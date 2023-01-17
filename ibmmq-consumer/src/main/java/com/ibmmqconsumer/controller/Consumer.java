package com.ibmmqconsumer.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibm.jms.JMSMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.TextMessage;

@Slf4j
@Component
@RequiredArgsConstructor
public class Consumer {
    final JmsTemplate jmsTemplate;
    final ObjectMapper customObjectMapper;

    @JmsListener(destination = "${queues.queueDev}", selector = "adapter = 'T24'")
    public void receiveHight(Message message) throws JMSException, JsonProcessingException {
        log.info("T24: " + message.getPayload(), customObjectMapper.writeValueAsString(message));
//        if (message instanceof JMSMessage) {
//            TextMessage textMessage = (TextMessage) message;
//            System.out.println("aaa: " + textMessage.getText());
//        }
    }

    @JmsListener(destination = "${queues.queueDev}", selector = "adapter = 'EPAY'")
    public void receiveLow(Message message) throws JMSException {
        log.info("EPAY: "+ message.getPayload());
    }

}
