package com.kafkaconsumer.Listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KafkaListenerImpl implements POCListener {

    @KafkaListener(topics = "poc-topic", groupId = "poc-group")
    public void consumer(final String message) throws JsonProcessingException {
        log.info(message);
    }
}
