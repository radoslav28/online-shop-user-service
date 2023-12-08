package com.onlineshop.user.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onlineshop.user.api.exceptions.ParsingToJsonException;
import com.onlineshop.user.api.operations.cartitem.sellcart.SellCartResult;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaGateway {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public void sendMessage (SellCartResult result) {
        String json = "";
        try {
            json = objectMapper.writeValueAsString(result);
        } catch (JsonProcessingException e) {
            throw new ParsingToJsonException();
        }
        kafkaTemplate.send("sendEmail", json);
    }
}
