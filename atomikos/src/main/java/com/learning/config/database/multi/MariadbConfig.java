package com.learning.config.database.multi;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
@RequiredArgsConstructor
@EnableJpaRepositories(basePackages = "com.demo.repository.mariadb",
        entityManagerFactoryRef = "mariadbEntityManagerFactory",
        transactionManagerRef = "mariadbTransactionManager")
public class MariadbConfig {
    final DatasourceProperties datasourceProperties;

    @Bean(name = "mariadbDataSource")
    @ConfigurationProperties("datasource.mariadb.configuration")
    public DataSource getDatasource() {
        var properties = datasourceProperties.getMariadb();
        return DataSourceBuilder.create()
                .url(properties.getUrl())
                .username(properties.getUsername())
                .password(properties.getPassword())
                .build();
    }

    @Bean(name = "mariadbEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean getEntityManagerFactory(EntityManagerFactoryBuilder builder) {
        var properties = new HashMap<String, Object>();
        properties.put("hibernate.dialect", datasourceProperties.getMariadb().getDialect());
        return builder
                .dataSource(getDatasource())
//                .packages(ActorEntity.class)
                .properties(properties)
                .build();
    }

    @Bean(name = "mariadbTransactionManager")
    public PlatformTransactionManager getTransactionManager (
            final @Qualifier("mariadbEntityManagerFactory")
            LocalContainerEntityManagerFactoryBean entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory.getObject());
    }
}
