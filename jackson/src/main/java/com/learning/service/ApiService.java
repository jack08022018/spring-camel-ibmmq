package com.learning.service;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface ApiService {
    <T> T jsonToString() throws JsonProcessingException;
    <T> T stringToJson() throws JsonProcessingException;
}
