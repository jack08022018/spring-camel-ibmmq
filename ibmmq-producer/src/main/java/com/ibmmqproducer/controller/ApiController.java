package com.ibmmqproducer.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibmmqproducer.adapter.SenderService;
import com.ibmmqproducer.config.properties.TemporalProperties;
import com.ibmmqproducer.dto.ConsumeFilterDto;
import com.ibmmqproducer.dto.RequestDto;
import com.ibmmqproducer.dto.User;
import grpc.TransactionRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
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
    final TemporalProperties temporalProperties;
    final ObjectMapper customObjectMapper;

    @Value("${queues.queueDev1}")
    private String queueDev1;

    @Value("${queues.queueDev2}")
    private String queueDev2;

    @GetMapping(value = "/modify")
    public <T> T modify(@RequestBody RequestDto<User> req) throws JsonProcessingException {
//        String[] allBeanNames = applicationContext.getBeanDefinitionNames();
//        for(String beanName : allBeanNames) {
//            System.out.println(beanName);
//        }
//        return (T) customObjectMapper.writeValueAsString(req);
        return (T) req;
    }

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
            String random = RandomStringUtils.random(32);
//            messagePostProcessor.setJMSCorrelationID(adapter + "_getInfo_" + random);
            var correlationId = ConsumeFilterDto.builder()
                    .adapterName(adapter)
                    .lmid(random)
                    .build();
//            try {
//                messagePostProcessor.setJMSCorrelationID(customObjectMapper.writeValueAsString(correlationId));
////                int a = 1/0;
//            } catch (JsonProcessingException e) {
//                throw new RuntimeException(e);
//            }
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
