package com.multithread.service;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class RestServiceImpl implements RestService {
    final RestTemplate customRestTemplate;
    final static String URL = "http://localhost:9199/ibmmq-consumer";

    @Override
    public JsonNode getUser() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(null, headers);

        return customRestTemplate.postForObject(URL + "/api/getUser", request, JsonNode.class);
    }

    @Override
    public JsonNode getClient() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(null, headers);

        return customRestTemplate.postForObject(URL + "/api/getClient", request, JsonNode.class);
    }

    @Async
    @Override
    public <T> CompletableFuture<T> getUserAsync() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(null, headers);

        try {
            JsonNode response = customRestTemplate.postForObject(URL + "/api/getUser", request, JsonNode.class);
            return (CompletableFuture<T>) CompletableFuture.completedFuture(response);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return (CompletableFuture<T>) CompletableFuture.completedFuture(e.getMessage());
        }
    }

    @Async
    @Override
    public CompletableFuture<JsonNode> getClientAsync() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(null, headers);

        JsonNode response = customRestTemplate.postForObject(URL + "/api/getClient", request, JsonNode.class);
        return CompletableFuture.completedFuture(response);
    }

    @Async
    @Override
    public CompletableFuture<Void> saveUserAsync() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(null, headers);

        customRestTemplate.postForObject(URL + "/api/saveUser", request, JsonNode.class);
        return null;
    }
}
