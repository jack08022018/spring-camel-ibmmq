package com.atomikos.config.database;

import com.atomikos.icatch.jta.UserTransactionImp;
import com.atomikos.icatch.jta.UserTransactionManager;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.jta.JtaTransactionManager;

import javax.transaction.TransactionManager;
import javax.transaction.UserTransaction;

@Configuration
@ComponentScan
@EnableTransactionManagement
public class DatabaseConfig {

	@Bean
	public PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}
	
	@Bean
	@Primary
	public JpaVendorAdapter mssqlVendorAdapter() {
		var vendorAdapter = new HibernateJpaVendorAdapter();
		vendorAdapter.setShowSql(true);
		vendorAdapter.setGenerateDdl(false);
		vendorAdapter.setDatabase(Database.SQL_SERVER);
		return vendorAdapter;
	}

	@Bean
	public JpaVendorAdapter mariadbVendorAdapter() {
		var vendorAdapter = new HibernateJpaVendorAdapter();
		vendorAdapter.setShowSql(true);
		vendorAdapter.setGenerateDdl(false);
		vendorAdapter.setDatabase(Database.MYSQL);
		return vendorAdapter;
	}


	@Bean(name = "userTransaction")
	public UserTransaction userTransaction() throws Throwable {
		var userTransactionImp = new UserTransactionImp();
		userTransactionImp.setTransactionTimeout(10000);
		return userTransactionImp;
	}

	@Bean(name = "atomikosTransactionManager", initMethod = "init", destroyMethod = "close")
	public TransactionManager atomikosTransactionManager() throws Throwable {
		var userTxManager = new UserTransactionManager();
		userTxManager.setForceShutdown(false);
		AtomikosJtaPlatform.transactionManager = userTxManager;
		return userTxManager;
	}

	@Bean(name = "transactionManager")
	@DependsOn({ "userTransaction", "atomikosTransactionManager" })
	public PlatformTransactionManager transactionManager() throws Throwable {
		var userTransaction = userTransaction();
		AtomikosJtaPlatform.transaction = userTransaction;
		TransactionManager atomikosTransactionManager = atomikosTransactionManager();
		return new JtaTransactionManager(userTransaction, atomikosTransactionManager);
	}

}
