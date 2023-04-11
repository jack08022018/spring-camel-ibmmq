package com.demo.config;

import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.r2dbc.connection.R2dbcTransactionManager;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

//@Configuration
//@EnableTransactionManagement
public class DatabaseConfig {

    @Bean
    public TransactionManager transactionManager1(ConnectionFactory connectionFactory1) {
        return new R2dbcTransactionManager(connectionFactory1);
    }

    @Bean
    public TransactionManager transactionManager2(ConnectionFactory connectionFactory2) {
        return new R2dbcTransactionManager(connectionFactory2);
    }

    @Bean
    public DatabaseClient databaseClient1(ConnectionFactory connectionFactory1) {
        return DatabaseClient.create(connectionFactory1);
    }

    @Bean
    public DatabaseClient databaseClient2(ConnectionFactory connectionFactory2) {
        return DatabaseClient.create(connectionFactory2);
    }

    @Bean
    public ConnectionFactory connectionFactory1() {
        return ConnectionFactories.get("r2dbc:h2:mem:///testdb1?options=DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE");
    }

    @Bean
    public ConnectionFactory connectionFactory2() {
        return ConnectionFactories.get("r2dbc:h2:mem:///testdb2?options=DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE");
    }

//    @Bean
//    public MyRepository myRepository1(DatabaseClient databaseClient1) {
//        return new MyRepository(databaseClient1);
//    }
//
//    @Bean
//    public MyRepository myRepository2(DatabaseClient databaseClient2) {
//        return new MyRepository(databaseClient2);
//    }
}
