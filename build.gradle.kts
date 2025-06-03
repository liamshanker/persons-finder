import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.7.0"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	kotlin("jvm") version "1.6.21"
	kotlin("plugin.spring") version "1.6.21"
}

group = "com.persons.finder"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	//Todo: restore when complete
//	mavenCentral()
	maven { url = uri("https://artifactory.gcp.anz/artifactory/gradle-plugins") }
	maven { url = uri("https://artifactory.gcp.anz/artifactory/maven-central") }
	maven { url = uri("https://artifactory.gcp.anz/artifactory/google-maven") }
	maven { url = uri("https://artifactory.gcp.anz/artifactory/plugins-m2-gradle") }
	maven { url = uri("https://artifactory.gcp.anz/artifactory/mavencentralrepo") }
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.h2database:h2:2.1.212")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
