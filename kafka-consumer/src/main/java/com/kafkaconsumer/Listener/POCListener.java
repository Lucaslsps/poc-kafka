package com.kafkaconsumer.Listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kafkaconsumer.requests.POCRequestDTO;

public interface POCListener {
    void consumer(POCRequestDTO message) throws JsonProcessingException;
}
