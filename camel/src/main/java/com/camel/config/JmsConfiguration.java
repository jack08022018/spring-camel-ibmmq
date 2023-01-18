//package com.camel.config;
//
//import lombok.extern.slf4j.Slf4j;
//import org.apache.camel.component.jms.JmsComponent;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.jms.connection.CachingConnectionFactory;
//import org.springframework.jms.connection.JmsTransactionManager;
//import org.springframework.jms.connection.UserCredentialsConnectionFactoryAdapter;
//import org.springframework.transaction.PlatformTransactionManager;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//import com.ibm.mq.jms.MQQueueConnectionFactory;
//import com.ibm.msg.client.wmq.WMQConstants;
//
//@Slf4j
//@Configuration
//@EnableTransactionManagement
//public class JmsConfiguration {
//    private String host = "localhost";
//    private int port = 1414;
//    private String queueManager = "QM1";
//    private String channel = "DEV.APP.SVRCONN";
//    private String username = "admin";
//    private String password = "passw0rd";
//    private long receiveTimeout = 2000;
//
//    @Bean
//    public JmsComponent mq() {
//        JmsComponent jmsComponent = new JmsComponent();
//        jmsComponent.setConnectionFactory(mqQueueConnectionFactory());
//        return jmsComponent;
//    }
//    @Bean
//    public MQQueueConnectionFactory mqQueueConnectionFactory() {
//        MQQueueConnectionFactory mqQueueConnectionFactory = new MQQueueConnectionFactory();
//        mqQueueConnectionFactory.setHostName(host);
//        try {
//            mqQueueConnectionFactory.setTransportType(WMQConstants.WMQ_CM_CLIENT);
//            mqQueueConnectionFactory.setChannel(channel);
//            mqQueueConnectionFactory.setPort(port);
//            mqQueueConnectionFactory.setQueueManager(queueManager);
//        } catch (Exception e) {
//            log.error(e.getMessage(), e);
//        }
//        return mqQueueConnectionFactory;
//    }
//    @Bean
//    public UserCredentialsConnectionFactoryAdapter userCredentialsConnectionFactoryAdapter(
//            MQQueueConnectionFactory mqQueueConnectionFactory) {
//        UserCredentialsConnectionFactoryAdapter userCredentialsConnectionFactoryAdapter =
//                new UserCredentialsConnectionFactoryAdapter();
//        userCredentialsConnectionFactoryAdapter.setUsername(username);
//        userCredentialsConnectionFactoryAdapter.setPassword(password);
//        userCredentialsConnectionFactoryAdapter.setTargetConnectionFactory(mqQueueConnectionFactory);
//        return userCredentialsConnectionFactoryAdapter;
//    }
//    @Bean
//    @Primary
//    public CachingConnectionFactory cachingConnectionFactory(
//            UserCredentialsConnectionFactoryAdapter userCredentialsConnectionFactoryAdapter) {
//        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory();
//        cachingConnectionFactory.setTargetConnectionFactory(userCredentialsConnectionFactoryAdapter);
//        cachingConnectionFactory.setSessionCacheSize(500);
//        cachingConnectionFactory.setReconnectOnException(true);
//        return cachingConnectionFactory;
//    }
//    @Bean
//    public PlatformTransactionManager jmsTransactionManager(
//            CachingConnectionFactory cachingConnectionFactory) {
//        JmsTransactionManager jmsTransactionManager = new JmsTransactionManager();
//        jmsTransactionManager.setConnectionFactory(cachingConnectionFactory);
//        return jmsTransactionManager;
//    }
//}
