package com.orches.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(TemporalProperties.class)
public class BeanConfig {

    final Environment env;

    @Bean(name = "customObjectMapper")
    public ObjectMapper getObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper;
    }

//    @Bean(name = "customRestTemplate")
//    public RestTemplate getRestTemplate() {
//        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
//        requestFactory.setReadTimeout(30000);
//        requestFactory.setConnectTimeout(30000);
//        RestTemplate restTemplate = new RestTemplate(requestFactory);
//        restTemplate.setErrorHandler(new RestTemplateResponseErrorHandler());
//        return restTemplate;
//    }
}
