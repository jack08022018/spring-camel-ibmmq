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
	implementation("com.ibm.mq:mq-jms-spring-boot-starter:2.6.5")
	implementation("org.apache.camel:camel-jms:3.20.1")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	runtimeOnly("org.mariadb.jdbc:mariadb-java-client")
//	implementation("com.microsoft.sqlserver:mssql-jdbc")
	implementation("org.apache.poi:poi:5.2.2")
	implementation("org.apache.poi:poi-ooxml:5.2.2")
	implementation("org.apache.commons:commons-lang3:3.12.0")
	implementation("org.javamoney:moneta:1.4.2")
	implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:2.14.2")
	implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")

	compileOnly("org.projectlombok:lombok")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	implementation("org.springframework.boot:spring-boot-starter-logging:2.7.3")
	implementation("org.springframework.boot:spring-boot-starter-log4j2:2.7.3")

	implementation("net.devh:grpc-server-spring-boot-starter:2.14.0.RELEASE")
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
//	clean {
//		delete generatedFilesBaseDir
//	}
	generateProtoTasks {
		ofSourceSet("main").forEach {
//			it.builtins{
//				java { option "java" }
//			}
//			it.generateDescriptorSet = true
//			it.descriptorSetOptions.includeImports = true
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
