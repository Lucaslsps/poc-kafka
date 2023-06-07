package com.kafkaconsumer.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kafkaconsumer.producer.KafkaProducer;
import com.kafkaconsumer.requests.POCRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaDLQListenerImpl implements POCListener {

    private final KafkaProducer kafkaProducer;
    private final int retryDelayInSeconds = 2;
    private final int maxRetryAttempts = 5;

    @KafkaListener(topics = "poc-topic-dlq", groupId = "poc-group")
    public void consume(final POCRequestDTO message,
                        @Header(KafkaHeaders.RECEIVED_TIMESTAMP) long ts,
                        Acknowledgment ack) throws JsonProcessingException {

        if (isOverMaxRetry(ts)) {
            sendToParkingLot(message, ts, ack);
            return;
        }

        if (shouldDelayRetry(ack, ts)) {
            return;
        }

        log.info("DLQ message: {}", message.toString());

        if (message.getId() == 1) {
            log.info("Sending message to parking lot");
            ack.nack(retryDelayInSeconds);
            return;
        }

        ack.acknowledge();
    }

    private void sendToParkingLot(POCRequestDTO message, long date, Acknowledgment ack) {
        final var record = new ProducerRecord<String, POCRequestDTO>("poc-topic-pk", message);
        record.headers().add("kafka-retry-timestamp-original", String.valueOf(date).getBytes());
        try {
            kafkaProducer.sendMessage(record);
            ack.acknowledge();
        } catch (Exception e) {
            log.error("Exception: ", e);
        }
    }

    private boolean isOverMaxRetry (long date) {
        var now = Instant.now().toEpochMilli();
        var diffTs = now - date;
        var diffTsToSeconds = diffTs > 1000 ? diffTs / 1000 : 0;

        return diffTsToSeconds > maxRetryAttempts * retryDelayInSeconds;
    }

    private boolean shouldDelayRetry(Acknowledgment ack, long ts) {
        var tsNow = Instant.now().toEpochMilli();
        var diffTs = tsNow - ts;
        var diffSegundos = diffTs > 1000 ? diffTs / 1000 : 0;
        if (diffSegundos < retryDelayInSeconds){
            var nackTime = retryDelayInSeconds - diffSegundos;
            log.info("NACK {} seconds, retry topic: {}", nackTime, "poc-topic-dlq");
            ack.nack(nackTime * 1000);
            return true;
        }
        return false;
    }
}