import org.springframework.boot.gradle.tasks.bundling.BootBuildImage
import com.google.protobuf.gradle.*

plugins {
	java
	id("org.springframework.boot") version "2.7.7"
	id("io.spring.dependency-management") version "1.0.15.RELEASE"
//	id("org.springframework.experimental.aot") version "0.12.2"
	id("com.google.protobuf") version "0.9.1"
}

group = "com"
version = "0.0.1"

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	maven { url = uri("https://repo.spring.io/release") }
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	compileOnly("org.projectlombok:lombok")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
//	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
//	runtimeOnly("org.mariadb.jdbc:mariadb-java-client")
	annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	implementation("org.springframework.boot:spring-boot-starter-logging:3.0.1")
	implementation("org.springframework.boot:spring-boot-starter-log4j2:3.0.1")

	implementation ("com.ibm.mq:mq-jms-spring-boot-starter:2.7.4")
//	implementation ('javax.jms:javax.jms-api:2.0.1')
//	implementation ('commons-beanutils:commons-beanutils:1.9.4')

	implementation("net.devh:grpc-server-spring-boot-starter:2.14.0.RELEASE")
	implementation("net.devh:grpc-client-spring-boot-starter:2.14.0.RELEASE")
	implementation("io.grpc:grpc-protobuf:1.35.0")
	implementation("io.grpc:grpc-stub:1.35.0")
	implementation("io.grpc:grpc-netty:1.35.0")
	implementation("io.grpc:grpc-services:1.35.0")
}
sourceSets.main.configure {
	proto.srcDir("src/main/resources/proto")
}
protobuf {
	protoc {artifact = "com.google.protobuf:protoc:3.17.2"}
	plugins {
		id("grpc") {
			artifact = "io.grpc:protoc-gen-grpc-java:1.39.0"
		}
	}
	generatedFilesBaseDir = "generated-sources"
	generateProtoTasks {
		ofSourceSet("main").forEach {
			it.plugins {
				id("grpc")
			}
		}
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
tasks.withType<BootBuildImage> {
	builder = "paketobuildpacks/builder:tiny"
	environment = mapOf("BP_NATIVE_IMAGE" to "true")
}
