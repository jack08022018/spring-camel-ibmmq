package com.atomikos.controller;


import com.atomikos.service.ApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api")
public class ApiController {
    final ApiService apiService;

    @GetMapping(value = "/getDataTransaction")
    public <T> T getDataTransaction() {
        return apiService.getDataTransaction();
    }

    @PostMapping(value = "/handleTransactional")
    public void handleTransactional() {
        apiService.handleTransactional();
    }
}
