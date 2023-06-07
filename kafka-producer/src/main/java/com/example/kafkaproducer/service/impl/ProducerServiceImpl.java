package com.example.kafkaproducer.service.impl;

import com.example.kafkaproducer.dto.requests.POCRequestDTO;
import com.example.kafkaproducer.dto.response.POCResponseDTO;
import com.example.kafkaproducer.producer.KafkaProducer;
import com.example.kafkaproducer.service.ProducerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProducerServiceImpl implements ProducerService {

    private final KafkaProducer kafkaProducer;

    @Override
    public POCResponseDTO produce(POCRequestDTO request) {
        kafkaProducer.sendMessage(request.getMessage());
        return null;
    }

}
