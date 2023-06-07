package com.kafkaconsumer.producer;

import com.kafkaconsumer.requests.POCRequestDTO;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;

@Component
public class KafkaProducer {

    private final KafkaTemplate<String, POCRequestDTO> kafkaTemplate;

    @Autowired
    public KafkaProducer(KafkaTemplate<String, POCRequestDTO> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(ProducerRecord<String, POCRequestDTO> req) throws ExecutionException, InterruptedException {
        kafkaTemplate.send(req).get();
    }
}
