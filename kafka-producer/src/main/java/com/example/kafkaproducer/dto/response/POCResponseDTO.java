package com.example.kafkaproducer.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class POCResponseDTO {
    private Integer id;
    private String message;
}
