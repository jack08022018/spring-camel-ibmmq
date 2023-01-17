package com.ibmmqproducer.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/api")
@RequiredArgsConstructor
public class ApiController {
    final JmsTemplate jmsTemplate;

    @Value("${queues.queueDev}")
    private String queueDev;

    @GetMapping(value = "/test")
    public <T> T test() {
        return (T) "success";
    }

    @GetMapping("send")
    String send() {
        try{
            jmsTemplate.convertAndSend(queueDev, "Hello World!");
            return "OK";
        }catch(Exception e){
            log.error("send: ", e);
            return e.getMessage();
        }
    }

}
