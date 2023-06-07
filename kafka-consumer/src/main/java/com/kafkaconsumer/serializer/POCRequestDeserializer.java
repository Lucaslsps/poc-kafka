package com.kafkaconsumer.serializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kafkaconsumer.requests.POCRequestDTO;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

public class POCRequestDeserializer implements Deserializer<POCRequestDTO> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        // No configuration needed
    }

    @Override
    public POCRequestDTO deserialize(String topic, byte[] data) {
        try {
            return objectMapper.readValue(data, POCRequestDTO.class);
        } catch (Exception e) {
            throw new RuntimeException("Error deserializing POCRequestDTO", e);
        }
    }

    @Override
    public void close() {
        // No resources to close
    }
}