package com.camelmultidb.config.datasources;

import com.camelmultidb.repository.mssql.entity.RentalNewEntity;
import org.apache.camel.spring.spi.SpringTransactionPolicy;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
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
@EnableJpaRepositories(basePackages = "com.camelmultidb.repository.mssql",
            entityManagerFactoryRef = "mssqlEntityManagerFactory",
            transactionManagerRef= "mssqlTransactionManager")
public class MssqlConfig {

    @Value("${mariadb.datasource.dialect}")
    private String hibernateDialect;

    @Primary
    @Bean(name = "mssqlProperties")
    @ConfigurationProperties("mssql.datasource")
    public DataSourceProperties getProperties() {
        return new DataSourceProperties();
    }

    @Primary
    @Bean(name = "mssqlDataSource")
    @ConfigurationProperties("mssql.datasource.configuration")
    public DataSource getDataSource() {
        return getProperties()
                .initializeDataSourceBuilder()
                .type(BasicDataSource.class)
                .build();
    }

    @Primary
    @Bean(name = "mssqlEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean getEntityManagerFactory(EntityManagerFactoryBuilder builder) {
        HashMap<String, Object> props = new HashMap<>();
        props.put("hibernate.dialect", hibernateDialect);
        return builder
                .dataSource(getDataSource())
                .properties(props)
                .packages(RentalNewEntity.class)
                .build();
    }

    @Primary
    @Bean(name = "mssqlTransactionManager")
    public PlatformTransactionManager getTransactionManager(
            final @Qualifier("mssqlEntityManagerFactory")
            LocalContainerEntityManagerFactoryBean entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory.getObject());
    }

    @Bean(name = "txPolicyMssql")
    public SpringTransactionPolicy txPolicyMaria(
            final @Qualifier("mssqlTransactionManager") PlatformTransactionManager txManager) {
        var policy = new SpringTransactionPolicy();
        policy.setTransactionManager(txManager);
        return policy;
    }
}
