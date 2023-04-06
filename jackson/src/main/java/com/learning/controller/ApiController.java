package com.learning.controller;


import com.learning.service.ApiService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api")
public class ApiController {
    final ApiService apiService;

    @GetMapping(value = "/jsonToString")
    public <T> T jsonToString() throws JsonProcessingException {
        return apiService.jsonToString();
    }

    @GetMapping(value = "/stringToJson")
    public <T> T stringToJson() throws JsonProcessingException {
        return apiService.stringToJson();
    }

}
