package com.ibmmqproducer.controller;


import com.ibmmqproducer.adapter.SenderService;
import grpc.TransactionRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api")
@RequiredArgsConstructor
public class ApiController {
    final JmsTemplate jmsTemplate;
    final ApplicationContext applicationContext;
    final SenderService senderService;

    @Value("${queues.queueDev1}")
    private String queueDev1;

    @Value("${queues.queueDev2}")
    private String queueDev2;

    @GetMapping(value = "/test")
    public <T> T test(@RequestParam String transactionId) {
//        String[] allBeanNames = applicationContext.getBeanDefinitionNames();
//        for(String beanName : allBeanNames) {
//            System.out.println(beanName);
//        }
        TransactionRequest request = TransactionRequest.newBuilder()
                .setTransactionId(transactionId)
                .build();
        var response = senderService.deduct(request);
        return (T) response.getResult();
    }

    @GetMapping("send")
    String send(@RequestParam String adapter) {
        String message = "Hello World!";
        jmsTemplate.convertAndSend(queueDev1, message, messagePostProcessor -> {
            messagePostProcessor.setStringProperty("ADAPTER", adapter);
            return messagePostProcessor;
        });
//            jmsTemplate.convertAndSend(queueDev, "Hello World!");
        return "OK";
    }

    @GetMapping("send2")
    String send2(@RequestParam String adapter) {
        String message = "Hello World!";
        jmsTemplate.convertAndSend(queueDev2, message, messagePostProcessor -> {
            messagePostProcessor.setStringProperty("ADAPTER", adapter);
            return messagePostProcessor;
        });
        return "OK";
    }

}
