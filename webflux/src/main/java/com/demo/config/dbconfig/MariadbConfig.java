package com.demo.config.dbconfig;

import com.demo.config.properties.DatasourceProperties;
import io.r2dbc.mssql.MssqlConnectionConfiguration;
import io.r2dbc.mssql.MssqlConnectionFactory;
import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryOptions;
import lombok.RequiredArgsConstructor;
import org.mariadb.r2dbc.MariadbConnectionConfiguration;
import org.mariadb.r2dbc.MariadbConnectionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.r2dbc.ConnectionFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.r2dbc.core.DefaultReactiveDataAccessStrategy;
import org.springframework.data.r2dbc.core.R2dbcEntityOperations;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.r2dbc.dialect.MySqlDialect;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.data.relational.core.dialect.MariaDbDialect;
import org.springframework.r2dbc.connection.R2dbcTransactionManager;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.transaction.ReactiveTransactionManager;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.time.Duration;

@Configuration
@RequiredArgsConstructor
@EnableTransactionManagement
@EnableR2dbcRepositories(basePackages = "com.demo.repository.mariadb",
//        databaseClientRef = "mariadbDatabaseClient",
        entityOperationsRef = "mariadbEntityTemplate")
public class MariadbConfig {
    final DatasourceProperties datasourceProperties;

    @Primary
    @Bean("mariadbConnectionFactory")
    public MariadbConnectionFactory connectionFactory() {
        return new MariadbConnectionFactory(MariadbConnectionConfiguration.builder()
                .host("localhost")
                .port(3308)
                .database("sakila")
                .username("root")
                .password("123456")
                .build());
    }

    @Bean("mariadbTransactionManager")
    @Primary
    public ReactiveTransactionManager transactionManager(@Qualifier("mariadbConnectionFactory") ConnectionFactory connectionFactory) {
        return new R2dbcTransactionManager(connectionFactory);
    }

//    @Bean
//    @Primary
//    @Qualifier("mariadbDatabaseClient")
//    public DatabaseClient databaseClient(@Qualifier("mariadbConnectionFactory") ConnectionFactory connectionFactory) {
//        return DatabaseClient.create(connectionFactory);
//    }

    @Primary
    @Bean("mariadbEntityTemplate")
    public R2dbcEntityOperations entityTemplate(@Qualifier("mariadbConnectionFactory") ConnectionFactory connectionFactory) {
        var strategy = new DefaultReactiveDataAccessStrategy(MySqlDialect.INSTANCE);
        DatabaseClient databaseClient = DatabaseClient.builder()
                .connectionFactory(connectionFactory)
                .bindMarkers(MySqlDialect.INSTANCE.getBindMarkersFactory())
                .build();

        return new R2dbcEntityTemplate(databaseClient, strategy);
    }

}
