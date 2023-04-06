package com.ibmmqconsumer.controller;


import com.ibmmqconsumer.dto.UserData;
import com.ibmmqconsumer.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@RequestMapping(value = "/api")
@RequiredArgsConstructor
public class ApiController {
    final JmsTemplate jmsTemplate;

    @Value("${queues.queueDev1}")
    private String queueDev1;

    @GetMapping("recv")
    String recv(){
        try{
            return jmsTemplate.receiveAndConvert(queueDev1).toString();
        }catch(JmsException ex){
            ex.printStackTrace();
            return "FAIL";
        }
    }

    @PostMapping("/test")
    public <T> T test(@RequestBody UserDto dto) throws InterruptedException {
        return (T) UserData.builder()
                .name(dto.getName())
                .address("HCM")
                .build();
    }

}
