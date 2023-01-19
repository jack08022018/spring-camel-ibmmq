package com.camel.process;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MyProcessor implements Processor {
//    final JmsTemplate jmsTemplate;

    @Value("${queues.queueDev}")
    private String queueDev;

    public void process(Exchange exchange) throws Exception {
        String message = "Hello World!";
        exchange.getIn().setBody(message);
//        jmsTemplate.convertAndSend(queueDev, message, messagePostProcessor -> {
//            messagePostProcessor.setStringProperty("adapter", "T24");
//            return messagePostProcessor;
//        });
    }
}
