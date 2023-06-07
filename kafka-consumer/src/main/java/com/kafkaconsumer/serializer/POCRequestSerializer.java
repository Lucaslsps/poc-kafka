package com.kafkaconsumer.serializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kafkaconsumer.requests.POCRequestDTO;
import org.apache.kafka.common.serialization.Serializer;

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