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
import java.sql.SQLException;
import java.util.HashMap;

@Configuration
@RequiredArgsConstructor
@DependsOn("transactionManager")
@EnableJpaRepositories(basePackages = "com.atomikos.repository.mymsdb",
		entityManagerFactoryRef = "mymsdbEntityManager",
		transactionManagerRef = "transactionManager")
public class MymsdbConfig {

	@Value("${mymsdb.datasource.url}")
	private String url;

	@Value("${mymsdb.datasource.username}")
	private String username;

	@Value("${mymsdb.datasource.password}")
	private String password;

	final JpaVendorAdapter mssqlVendorAdapter;

	@Bean(name = "mymsdbDataSource", initMethod = "init", destroyMethod = "close")
	@ConfigurationProperties("mymsdb.datasource.configuration")
	public DataSource getDataSource() throws SQLException {
		var dataSource = new SQLServerXADataSource();
		dataSource.setURL(url);
		dataSource.setUser(username);
		dataSource.setPassword(password);

		var xaDataSource = new AtomikosDataSourceBean();
		xaDataSource.setXaDataSource(dataSource);
		xaDataSource.setUniqueResourceName("xa-mymsdb");
		return xaDataSource;
	}

	@Bean(name = "mymsdbEntityManager")
	@DependsOn("transactionManager")
	public LocalContainerEntityManagerFactoryBean customerEntityManager() throws Throwable {
		HashMap<String, Object> properties = new HashMap<>();
		properties.put("hibernate.transaction.jta.platform", AtomikosJtaPlatform.class.getName());
		properties.put("javax.persistence.transactionType", "JTA");

		var entityManager = new LocalContainerEntityManagerFactoryBean();
		entityManager.setJtaDataSource(getDataSource());
		entityManager.setJpaVendorAdapter(mssqlVendorAdapter);
		entityManager.setPackagesToScan("com.atomikos.repository.mymsdb.entity");
		entityManager.setPersistenceUnitName("customerPersistenceUnit");
		entityManager.setJpaPropertyMap(properties);
		return entityManager;
	}

}
