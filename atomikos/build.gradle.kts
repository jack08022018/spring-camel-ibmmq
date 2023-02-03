plugins {
	java
	id("org.springframework.boot") version "2.7.8"
	id("io.spring.dependency-management") version "1.0.15.RELEASE"
}

group = "com"
version = "0.0.1"

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
	all {
		exclude(module = "spring-boot-starter-logging")
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("com.microsoft.sqlserver:mssql-jdbc")
	implementation("org.apache.commons:commons-dbcp2:2.9.0")
	implementation("com.atomikos:transactions:5.0.9")
	implementation("com.atomikos:transactions-jta:5.0.9")
	implementation("com.atomikos:transactions-jdbc:5.0.9")
	implementation("org.mariadb.jdbc:mariadb-java-client:3.1.2")

	compileOnly("org.projectlombok:lombok")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	implementation("org.springframework.boot:spring-boot-starter-logging:2.7.3")
	implementation("org.springframework.boot:spring-boot-starter-log4j2:2.7.3")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
