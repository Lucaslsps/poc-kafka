package com.example.kafkaproducer.serializer;

import com.example.kafkaproducer.dto.requests.POCRequestDTO;
import org.apache.kafka.common.serialization.Serializer;
        import com.fasterxml.jackson.databind.ObjectMapper;

public class POCRequestSerializer implements Serializer<POCRequestDTO> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public byte[] serialize(String topic, POCRequestDTO data) {
        try {
            return objectMapper.writeValueAsBytes(data);
        } catch (Exception e) {
            throw new RuntimeException("Error serializing POCRequestDTO", e);
        }
    }
}