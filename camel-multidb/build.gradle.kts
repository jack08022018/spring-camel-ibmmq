import org.springframework.boot.gradle.tasks.bundling.BootBuildImage

plugins {
	java
	id("org.springframework.boot") version "2.7.7"
	id("io.spring.dependency-management") version "1.0.15.RELEASE"
//	id("org.springframework.experimental.aot") version "0.12.2"
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
	maven { url = uri("https://repo.spring.io/release") }
	mavenCentral()
}

dependencies {
	implementation("org.apache.camel.springboot:camel-spring-boot-starter:3.20.1")
	implementation("org.apache.camel.springboot:camel-netty-http-starter:3.20.1")
	implementation("org.apache.camel.springboot:camel-jackson-starter:3.20.1")
	implementation("org.springframework:spring-web")
	implementation("com.ibm.mq:mq-jms-spring-boot-starter:2.6.5")
	implementation("org.apache.camel:camel-jms:3.20.1")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	runtimeOnly("org.mariadb.jdbc:mariadb-java-client")
	implementation("com.microsoft.sqlserver:mssql-jdbc")
	implementation("org.apache.commons:commons-dbcp2:2.9.0")
	implementation("com.github.ulisesbocchio:jasypt-spring-boot-starter:3.0.5")
	implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
	implementation("org.apache.poi:poi:5.2.2")
	implementation("org.apache.poi:poi-ooxml:5.2.2")
	implementation("org.apache.commons:commons-lang3:3.12.0")
	implementation("org.javamoney:moneta:1.4.2")

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

tasks.withType<BootBuildImage> {
	builder = "paketobuildpacks/builder:tiny"
	environment = mapOf("BP_NATIVE_IMAGE" to "true")
}
