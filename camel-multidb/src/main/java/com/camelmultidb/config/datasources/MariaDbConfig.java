package com.camelmultidb.config.datasources;

import com.camelmultidb.repository.mariaDB.entity.ActorEntity;
import org.apache.camel.spring.spi.SpringTransactionPolicy;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
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
@EnableJpaRepositories(basePackages = "com.camelmultidb.repository.mariadb",
        entityManagerFactoryRef = "mariadbEntityManagerFactory",
        transactionManagerRef= "mariadbTransactionManager")
public class MariaDbConfig {

    @Value("${mariadb.datasource.dialect}")
    private String hibernateDialect;

    @Bean(name = "mariadbProperties")
    @ConfigurationProperties("mariadb.datasource")
    public DataSourceProperties getProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = "mariadbDataSource")
    @ConfigurationProperties("mariadb.datasource.configuration")
    public DataSource getDataSource() {
        return getProperties()
                .initializeDataSourceBuilder()
                .type(BasicDataSource.class)
                .build();
    }

    @Bean(name = "mariadbEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean getEntityManagerFactory(EntityManagerFactoryBuilder builder) {
        HashMap<String, Object> props = new HashMap<>();
        props.put("hibernate.dialect", hibernateDialect);
        return builder
                .dataSource(getDataSource())
                .properties(props)
                .packages(ActorEntity.class)
                .build();
    }

    @Bean(name = "mariadbTransactionManager")
    public PlatformTransactionManager getTransactionManager(
            final @Qualifier("mariadbEntityManagerFactory")
            LocalContainerEntityManagerFactoryBean entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory.getObject());
    }

    @Bean(name = "txPolicyMariadb")
    public SpringTransactionPolicy txPolicyMaria(
            final @Qualifier("mariadbTransactionManager") PlatformTransactionManager txManager) {
        var policy = new SpringTransactionPolicy();
        policy.setTransactionManager(txManager);
        return policy;
    }

}
