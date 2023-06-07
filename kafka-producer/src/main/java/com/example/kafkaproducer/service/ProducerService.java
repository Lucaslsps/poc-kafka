package com.example.kafkaproducer.service;

import com.example.kafkaproducer.dto.requests.POCRequestDTO;
import com.example.kafkaproducer.dto.response.POCResponseDTO;

public interface ProducerService {
    POCResponseDTO produce(POCRequestDTO request);
}
