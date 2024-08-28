plugins {
	kotlin("jvm") version "1.9.25"
	kotlin("plugin.spring") version "1.9.25"
	id("org.springframework.boot") version "3.3.3"
	id("io.spring.dependency-management") version "1.1.6"
}

group = "com.ennaru"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}

repositories {
	mavenCentral()
}

val resilience4jVersion = "2.1.0"

dependencies {
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.springframework.boot:spring-boot-configuration-processor")
	implementation("org.jetbrains.kotlin:kotlin-reflect")

	// actuator
	implementation("org.springframework.boot:spring-boot-starter-actuator")

	// web-mvc
	implementation("org.springframework.boot:spring-boot-starter-web")

	// jpa
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	runtimeOnly("com.h2database:h2")

	// aop
	implementation("org.springframework.boot:spring-boot-starter-aop")

	// circuit-breaker
	// 1. CircuitBreaker : 장애 전파 방지 기능 제공
	implementation("io.github.resilience4j:resilience4j-circuitbreaker:${resilience4jVersion}")
	// 2. Retry : 요청 실패 시 재시도 처리 기능 제공
	implementation("io.github.resilience4j:resilience4j-retry:${resilience4jVersion}")
	// 3. RateLimiter : 제한치를 넘어서 요청을 거부하거나 Queue 생성하여 처리하는 기능 제공
	implementation("io.github.resilience4j:resilience4j-ratelimiter:${resilience4jVersion}")
	// 4. TimeLimiter : 실행 시간제한 설정 기능 제공
	implementation("io.github.resilience4j:resilience4j-timelimiter:${resilience4jVersion}")
	// 5. Bulkhead : 동시 실행 횟수 제한 기능 제공
	implementation("io.github.resilience4j:resilience4j-bulkhead:${resilience4jVersion}")
	// 6. Cache : 결과 캐싱 기능 제공
	implementation("io.github.resilience4j:resilience4j-cache:${resilience4jVersion}")
	// 7. spring boot annotation
	implementation("io.github.resilience4j:resilience4j-spring-boot3:${resilience4jVersion}")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
