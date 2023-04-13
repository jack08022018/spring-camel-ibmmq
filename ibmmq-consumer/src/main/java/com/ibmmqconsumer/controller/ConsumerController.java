package com.ibmmqconsumer.controller;


import com.ibmmqconsumer.dto.UserData;
import com.ibmmqconsumer.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@RequestMapping(value = "/api")
@RequiredArgsConstructor
public class ConsumerController {
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
        System.out.println(LocalDateTime.now() + " START");
        TimeUnit.SECONDS.sleep(3);
        return (T) UserData.builder()
                .name(dto.getName())
                .address("HCM")
                .build();
    }

    @PostMapping("/getUser")
    public ModelMap getUser() throws InterruptedException {
        System.out.println(LocalDateTime.now() + " START");
        TimeUnit.SECONDS.sleep(2);
        var result = new ModelMap();
        result.put("id", 1);
        result.put("name", "King");
        System.out.println(LocalDateTime.now() + " END");
//        int a = 1/0;
        return result;
    }

    @PostMapping("/getClient")
    public ModelMap getClient() throws InterruptedException {
        System.out.println(LocalDateTime.now() + " START");
        TimeUnit.SECONDS.sleep(3);
        ModelMap result = new ModelMap();
        result.put("clientCode", 1001001);
        result.put("clientName", "PMH");
        System.out.println(LocalDateTime.now() + " END");
        return result;
    }

    @PostMapping("/saveUser")
    public void saveUser() throws InterruptedException {
        TimeUnit.SECONDS.sleep(2);
//        int a = 1/0;
    }

}
