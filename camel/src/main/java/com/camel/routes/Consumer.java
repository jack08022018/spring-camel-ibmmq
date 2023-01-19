//package com.camel.routes;
//
//import org.apache.camel.Body;
//import org.apache.camel.Consume;
//import org.apache.camel.language.bean.Bean;
//
//public class Consumer {
//    @Consume("activemq:my.queue")
//    public void doSomething(@Bean("myCorrelationIdGenerator") String correlationID, @Body String body) {
//        // process the inbound message here
//    }
//}
