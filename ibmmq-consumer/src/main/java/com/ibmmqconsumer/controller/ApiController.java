package com.ibmmqconsumer.controller;


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

    @GetMapping("recv")
    String recv(){
        try{
            return jmsTemplate.receiveAndConvert(queueDev).toString();
        }catch(JmsException ex){
            ex.printStackTrace();
            return "FAIL";
        }
    }

}
