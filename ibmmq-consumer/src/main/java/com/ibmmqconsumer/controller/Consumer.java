package com.ibmmqconsumer.controller;


import com.ibm.jms.JMSMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.TextMessage;

@Component
@RequiredArgsConstructor
public class Consumer {
    final JmsTemplate jmsTemplate;

    @JmsListener(destination = "${queues.queueDev}")
    public void receive(Message message) throws JMSException {
        System.out.println("aaa: " + message.getPayload());
        if (message instanceof JMSMessage) {
            TextMessage textMessage = (TextMessage) message;
            System.out.println("aaa: " + textMessage.getText());
        }
    }

}
