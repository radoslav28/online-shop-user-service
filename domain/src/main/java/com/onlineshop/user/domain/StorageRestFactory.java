package com.onlineshop.user.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onlineshop.storage.restexport.StorageServiceRestClient;
import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StorageRestFactory {

    @Value("${STORAGE_SERVICE_URL}")
    private String storeServiceUrl;

    @Bean
    public StorageServiceRestClient getRestExportClient() {
        final ObjectMapper objectMapper = new ObjectMapper();
        return Feign.builder()
                .encoder(new JacksonEncoder(objectMapper))
                .decoder(new JacksonDecoder(objectMapper))
                .target(StorageServiceRestClient.class, storeServiceUrl);

    }

}
