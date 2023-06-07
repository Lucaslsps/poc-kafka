package com.kafkaconsumer.Listener;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface POCListener {
    void consumer(String message) throws JsonProcessingException;
}
