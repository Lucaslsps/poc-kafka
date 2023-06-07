package com.example.kafkaproducer.controller;

import com.example.kafkaproducer.dto.requests.POCRequestDTO;
import com.example.kafkaproducer.service.ProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/produce")
public class POCController {
    private final ProducerService produceMessage;


    @PostMapping("/")
    public ResponseEntity<String> produceMessage(
            @RequestBody final POCRequestDTO request
    ) {
        produceMessage.produce(request);
        return ResponseEntity.ok("ok");
    }

}
