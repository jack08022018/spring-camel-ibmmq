import org.springframework.boot.gradle.tasks.bundling.BootBuildImage

plugins {
	java
	id("org.springframework.boot") version "2.7.7"
	id("io.spring.dependency-management") version "1.0.15.RELEASE"
	id("org.springframework.experimental.aot") version "0.12.2"
}

group = "com"
version = "0.0.1"

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
//	all*.exclude module : 'spring-boot-starter-logging'
}

repositories {
	maven { url = uri("https://repo.spring.io/release") }
	mavenCentral()
}

dependencies {
//	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.apache.camel.springboot:camel-spring-boot-starter:3.20.1")
	implementation("org.apache.camel.springboot:camel-rest-starter:3.20.1")
	implementation("org.apache.camel.springboot:camel-netty-http-starter:3.20.1")
	implementation("org.apache.camel.springboot:camel-jackson-starter:3.20.1")
//	implementation("org.apache.camel.springboot:camel-activemq-starter:3.20.1")
	implementation("org.apache.camel:camel-jms:3.20.1")
	implementation("com.ibm.mq:com.ibm.mq.allclient:9.3.1.0")
	compileOnly("org.projectlombok:lombok")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	implementation("org.springframework.boot:spring-boot-starter-logging:2.7.3"){
		exclude("spring-boot-starter-logging")
	}
	implementation("org.springframework.boot:spring-boot-starter-log4j2:2.7.3") {
		exclude("spring-boot-starter-logging")
	}
//	implementation ("com.ibm.mq:mq-jms-spring-boot-starter:2.7.4")

}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.withType<BootBuildImage> {
	builder = "paketobuildpacks/builder:tiny"
	environment = mapOf("BP_NATIVE_IMAGE" to "true")
}
