package com.camel.process;

import lombok.AllArgsConstructor;
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
public class ToQueueProcess implements Processor {
    final JmsTemplate jmsTemplate;

    @Value("${queues.queueDev2}")
    private String queueDev2;

    @Override
    public void process(Exchange exchange) throws Exception {
        log.info("ToQueueProcess");
        String body = (String) exchange.getIn().getBody();
        jmsTemplate.convertAndSend(queueDev2, body, messagePostProcessor -> {
            messagePostProcessor.setStringProperty("ADAPTER", "CONSUMER");
            return messagePostProcessor;
        });
    }

}
