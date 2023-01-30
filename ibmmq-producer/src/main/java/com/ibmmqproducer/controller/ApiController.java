package com.ibmmqproducer.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api")
@RequiredArgsConstructor
public class ApiController {
    final JmsTemplate jmsTemplate;
    final ApplicationContext applicationContext;

    @Value("${queues.queueDev}")
    private String queueDev;

    @GetMapping(value = "/test")
    public <T> T test() {
        String[] allBeanNames = applicationContext.getBeanDefinitionNames();
        for(String beanName : allBeanNames) {
            System.out.println(beanName);
        }
        return (T) "success";
    }

    @GetMapping("send")
    String send(@RequestParam String adapter) {
        String message = "Hello World!";
        jmsTemplate.convertAndSend(queueDev, message, messagePostProcessor -> {
            messagePostProcessor.setStringProperty("ADAPTER", adapter);
            return messagePostProcessor;
        });
//            jmsTemplate.convertAndSend(queueDev, "Hello World!");
        return "OK";
    }

}
