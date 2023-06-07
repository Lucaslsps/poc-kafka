package com.kafkaconsumer.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kafkaconsumer.requests.POCRequestDTO;
import org.springframework.kafka.support.Acknowledgment;

public interface POCListener {
    void consume(POCRequestDTO message, long ts, Acknowledgment ack) throws JsonProcessingException;
}
