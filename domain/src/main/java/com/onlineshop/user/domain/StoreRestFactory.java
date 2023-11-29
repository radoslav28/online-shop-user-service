package com.onlineshop.user.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onlineshop.store.restexport.StoreServiceRestClient;
import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StoreRestFactory {

    @Value("${STORE_SERVICE_URL}")
    private String storeServiceUrl;

    @Bean
    public StoreServiceRestClient getRestExportClient() {
        final ObjectMapper objectMapper = new ObjectMapper();
        return Feign.builder()
                .encoder(new JacksonEncoder(objectMapper))
                .decoder(new JacksonDecoder(objectMapper))
                .target(StoreServiceRestClient.class, storeServiceUrl);

    }

}
