package com.example.kafkaproducer.producer;

import com.example.kafkaproducer.dto.requests.POCRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaProducer {

    private final KafkaTemplate<String, POCRequestDTO> kafkaTemplate;

    @Autowired
    public KafkaProducer(KafkaTemplate<String, POCRequestDTO> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(POCRequestDTO req) {
        kafkaTemplate.send("poc-topic", req);
    }
}
