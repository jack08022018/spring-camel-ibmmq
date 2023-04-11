package com.demo.config.dbconfig;

import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryOptions;
import org.springframework.boot.r2dbc.ConnectionFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.data.relational.core.dialect.MariaDbDialect;
import org.springframework.r2dbc.connection.R2dbcTransactionManager;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.time.Duration;

@Configuration
@EnableTransactionManagement
@EnableR2dbcRepositories(basePackages = "com.demo.repository.mariadb")
public class MariadbConfig {

    @Bean
    @Primary
    public TransactionManager mariadbTransactionManager(ConnectionFactory mariadbConnectionFactory) {
        return new R2dbcTransactionManager(mariadbConnectionFactory);
    }

//    @Bean
//    public DatabaseClient databaseClient1(ConnectionFactory connectionFactory1) {
//        return DatabaseClient.create(connectionFactory1);
//    }

    @Bean
    @Primary
    public ConnectionFactory mariadbConnectionFactory() {
//        url: r2dbc:mariadb://localhost:3308/sakila
//        username: root
//        password: 123456
        ConnectionFactoryOptions options = ConnectionFactoryOptions.builder()
                .option(ConnectionFactoryOptions.DRIVER, "mariadb")
                .option(ConnectionFactoryOptions.HOST, "localhost")
                .option(ConnectionFactoryOptions.PORT, 3308)
                .option(ConnectionFactoryOptions.USER, "root")
                .option(ConnectionFactoryOptions.PASSWORD, "123456")
                .option(ConnectionFactoryOptions.DATABASE, "sakila")
                .option(ConnectionFactoryOptions.CONNECT_TIMEOUT, Duration.ofSeconds(10))
//                .option(PostgresConnectionFactoryProvider.POSTGRES_CONNECTION_FACTORY, "r2dbc.postgresql")
                .build();
        return ConnectionFactories.get(options);

//        return ConnectionFactoryBuilder.withUrl("")
//                .hostname("mariadb://localhost")
//                .port(3308)
//                .database("sakila")
//                .username("root")
//                .password("123456")
//                .build();
//                .builder()
//                .driver("org.postgresql.Driver")
//                .url("jdbc:postgresql://localhost/mydatabase")
//                .username("myusername")
//                .password("mypassword");

//        return ConnectionFactories.b;
    }

//    @Bean
//    public R2dbcEntityTemplate r2dbcEntityTemplate(ConnectionFactory mariadbConnectionFactory) {
//        return new R2dbcEntityTemplate(mariadbConnectionFactory, new MariaDbDialect(QuotedIdentifiers.INSTANCE));
//    }

//    @Bean
//    public ConnectionFactory connectionFactory2() {
//        return ConnectionFactories.get("r2dbc:h2:mem:///testdb2?options=DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE");
//    }

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
