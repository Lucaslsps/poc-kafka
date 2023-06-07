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

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaListenerImpl implements POCListener {
    private final KafkaProducer kafkaProducer;
    @KafkaListener(topics = "poc-topic", groupId = "poc-group")
    public void consume(final POCRequestDTO message,
                        @Header(KafkaHeaders.RECEIVED_TIMESTAMP) long ts,
                        Acknowledgment ack) throws JsonProcessingException {
        log.info(message.toString());

        if (message.getId() == 1) {
           log.info("Sending to DLQ Queue");
           sendToRetry(message, ts);
        }

        ack.acknowledge();
    }

    private void sendToRetry(POCRequestDTO message, long date) {
        final var record = new ProducerRecord<String, POCRequestDTO>("poc-topic-dlq", message);
        record.headers().add("kafka-retry-timestamp-original", String.valueOf(date).getBytes());
        try {
            kafkaProducer.sendMessage(record);
        } catch (Exception e) {
            log.error("Exception: ", e);
        }
    }
}
