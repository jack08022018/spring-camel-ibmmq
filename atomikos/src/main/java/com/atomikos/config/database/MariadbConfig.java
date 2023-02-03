package com.atomikos.config.database;

import com.atomikos.jdbc.AtomikosDataSourceBean;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
@RequiredArgsConstructor
@DependsOn("transactionManager")
@EnableJpaRepositories(basePackages = "com.atomikos.repository.mariaDB",
		entityManagerFactoryRef = "mariadbEntityManager",
		transactionManagerRef = "transactionManager")
public class MariadbConfig {

	@Value("${mariadb.datasource.dialect}")
	private String hibernateDialect;

	@Value("${mariadb.datasource.url}")
	private String url;

	@Value("${mariadb.datasource.username}")
	private String username;

	@Value("${mariadb.datasource.password}")
	private String password;

	final JpaVendorAdapter mariadbVendorAdapter;

	@Bean(name = "mariadbDataSource", initMethod = "init", destroyMethod = "close")
	@ConfigurationProperties("mariadb.datasource.configuration")
	public DataSource getDataSource() {
//		var dataSource = new MariaDbDataSource();
//		dataSource.setURL(url);
//		dataSource.setUser(username);
//		dataSource.setPassword(password);

		var xaDataSource = new AtomikosDataSourceBean();
//		xaDataSource.setXaDataSource(dataSource);
		xaDataSource.setUniqueResourceName("xa-mariadb");
		return xaDataSource;
	}

	@Bean(name = "mariadbEntityManager")
	@DependsOn("transactionManager")
	public LocalContainerEntityManagerFactoryBean customerEntityManager() throws Throwable {
		HashMap<String, Object> properties = new HashMap<String, Object>();
		properties.put("hibernate.transaction.jta.platform", AtomikosJtaPlatform.class.getName());
		properties.put("javax.persistence.transactionType", "JTA");

		var entityManager = new LocalContainerEntityManagerFactoryBean();
		entityManager.setJtaDataSource(getDataSource());
		entityManager.setJpaVendorAdapter(mariadbVendorAdapter);
		entityManager.setPackagesToScan("com.atomikos.repository.mariaDB.entity");
		entityManager.setPersistenceUnitName("customerPersistenceUnit");
		entityManager.setJpaPropertyMap(properties);
		return entityManager;
	}

}
