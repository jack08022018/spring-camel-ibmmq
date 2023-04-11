package com.demo.config.dbconfig;

import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.r2dbc.connection.R2dbcTransactionManager;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableR2dbcRepositories(basePackages = "com.demo.repository.mssql")
public class MssqlConfig {

    @Bean
    public TransactionManager mssqlTransactionManager(ConnectionFactory mariadbConnectionFactory) {
        return new R2dbcTransactionManager(mariadbConnectionFactory);
    }

    @Bean
    public ConnectionFactory mssqlConnectionFactory() {
        ConnectionFactoryOptions options = ConnectionFactoryOptions.builder()
                .option(ConnectionFactoryOptions.DRIVER, "mssql")
                .option(ConnectionFactoryOptions.HOST, "localhost")
                .option(ConnectionFactoryOptions.PORT, 1433)
                .option(ConnectionFactoryOptions.USER, "sa")
                .option(ConnectionFactoryOptions.PASSWORD, "eJ8piivMCOYamnWNU87aIExyAbRXD1iW")
                .option(ConnectionFactoryOptions.DATABASE, "sakila")
                .build();
        return ConnectionFactories.get(options);
    }

}
