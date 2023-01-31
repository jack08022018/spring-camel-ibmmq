// An example of using the Spring Boot JmsTemplate in a request/reply pattern.
// The main application thread sends a message and then waits for a reply.
// Meanwhile, a JMS Listener is waiting for an input message to which it replies as
// part of the same transaction.
//
// The jmsTemplate.sendAndReceive method creates a temporary queue to which the reply will be
// sent. It can make use of the 'ibm.mq.tempModel' configuration property to select which
// Model queue to use to underpin that TDQ. If you are going to do many request/reply operations,
// it will be more efficient to create the reply queue once and use a separate receive() call.

package com.ibmmqproducer.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

@RestController
@RequestMapping(value = "/api")
@RequiredArgsConstructor
public class Requester {
    final JmsTemplate jmsTemplate;

    @Value("${queues.queueDev1}")
    private String queueDev1;

    @GetMapping("sendAndWaitForResponse")
    String sendAndWaitForResponse() {
        try{
//            jmsTemplate.convertAndSend(queueDev, "Hello World!");
            jmsTemplate.setReceiveTimeout(5 * 1000);
            String payload = "Hello from IBM MQ at ";
            Message replyMsg = jmsTemplate.sendAndReceive(queueDev1, new MessageCreator() {
                @Override
                public Message createMessage(Session session) throws JMSException {
                    TextMessage message = session.createTextMessage(payload);
                    System.out.println("Sending message: " + message.getText());
                    return message;
                }
            });
            if (replyMsg != null) {
                if (replyMsg instanceof TextMessage) {
                    System.out.println("Reply message is: " + ((TextMessage) replyMsg).getText());
                }
                else {
                    System.out.println("Reply message is: " + replyMsg.toString());
                }
            }
            else {
                System.out.println("No reply received");
            }
            return "OK";
        }catch(Exception ex){
            ex.printStackTrace();
            return ex.getMessage();
        }
    }

}
