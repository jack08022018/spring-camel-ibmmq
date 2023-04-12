package com.learning.config.database.multi;

import com.learning.repository.mssql.entity.RentalNewEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
@RequiredArgsConstructor
@EnableJpaRepositories(basePackages = "com.demo.repository.mssql",
        entityManagerFactoryRef = "mssqlEntityManagerFactory",
        transactionManagerRef = "mssqlTransactionManager")
public class MssqlConfig {
    final DatasourceProperties datasourceProperties;

    @Primary
    @Bean(name = "mssqlDataSource")
    @ConfigurationProperties("datasource.mssql.configuration")
    public DataSource getDatasource() {
        var properties = datasourceProperties.getMssql();
        return DataSourceBuilder.create()
                .url(properties.getUrl())
                .username(properties.getUsername())
                .password(properties.getPassword())
                .build();
    }

    @Primary
    @Bean(name = "mssqlEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean getEntityManagerFactory(EntityManagerFactoryBuilder builder) {
        var properties = new HashMap<String, Object>();
        properties.put("hibernate.dialect", datasourceProperties.getMssql().getDialect());
        return builder
                .dataSource(getDatasource())
                .packages(RentalNewEntity.class)
                .properties(properties)
                .build();
    }

    @Primary
    @Bean(name = "mssqlTransactionManager")
    public PlatformTransactionManager getTransactionManager (
            final @Qualifier("mssqlEntityManagerFactory")
            LocalContainerEntityManagerFactoryBean entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory.getObject());
    }
}
