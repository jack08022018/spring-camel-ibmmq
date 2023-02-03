package com.atomikos.config.database;

import com.atomikos.jdbc.AtomikosDataSourceBean;
import com.microsoft.sqlserver.jdbc.SQLServerXADataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
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
@EnableJpaRepositories(basePackages = "com.atomikos.repository",
		entityManagerFactoryRef = "mssqlEntityManager",
		transactionManagerRef = "transactionManager")
public class MssqlConfig {

	@Value("${mssql.datasource.dialect}")
	private String hibernateDialect;

	@Value("${mssql.datasource.url}")
	private String url;

	@Value("${mssql.datasource.username}")
	private String username;

	@Value("${mssql.datasource.password}")
	private String password;

	final JpaVendorAdapter mssqlVendorAdapter;

	@Bean(name = "mssqlDataSource", initMethod = "init", destroyMethod = "close")
	@ConfigurationProperties("mssql.datasource.configuration")
	public DataSource getDataSource() {
		var dataSource = new SQLServerXADataSource();
		dataSource.setURL(url);
		dataSource.setUser(username);
		dataSource.setPassword(password);

		var xaDataSource = new AtomikosDataSourceBean();
		xaDataSource.setXaDataSource(dataSource);
		xaDataSource.setUniqueResourceName("xa-mssql");
		return xaDataSource;
	}

	@Bean(name = "mssqlEntityManager")
	@DependsOn("transactionManager")
	public LocalContainerEntityManagerFactoryBean customerEntityManager() throws Throwable {
		HashMap<String, Object> properties = new HashMap<String, Object>();
		properties.put("hibernate.transaction.jta.platform", AtomikosJtaPlatform.class.getName());
		properties.put("javax.persistence.transactionType", "JTA");

		var entityManager = new LocalContainerEntityManagerFactoryBean();
		entityManager.setJtaDataSource(getDataSource());
		entityManager.setJpaVendorAdapter(mssqlVendorAdapter);
		entityManager.setPackagesToScan("com.atomikos.repository");
		entityManager.setPersistenceUnitName("customerPersistenceUnit");
		entityManager.setJpaPropertyMap(properties);
		return entityManager;
	}

}
