import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.6.4"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("jvm") version "1.6.10"
    kotlin("plugin.spring") version "1.6.10"
    kotlin("kapt") version "1.6.10"
    id("org.springframework.cloud.contract") version "3.1.1"
    id("org.asciidoctor.convert") version "2.4.0"

    java
    jacoco
    base
    idea
    `java-library`
    `maven-publish`
}

fun Project.envConfig() = object : kotlin.properties.ReadOnlyProperty<Any?, String?> {
    override fun getValue(thisRef: Any?, property: kotlin.reflect.KProperty<*>): String? =
        if (extensions.extraProperties.has(property.name)) {
            extensions.extraProperties[property.name] as? String
        } else {
            System.getenv(property.name)
        }
}

val version: String by extra
val group: String by extra
val springCloudVersion: String by extra
val kotlinFaker: String by extra
val halBrowser: String by extra
val evoInflector: String by extra

// import Spring Cloud  BOM
dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:$springCloudVersion")
    }
}

contracts {
    testFramework.set(org.springframework.cloud.contract.verifier.config.TestFramework.JUNIT5)
    testMode.set(org.springframework.cloud.contract.verifier.config.TestMode.EXPLICIT)
    basePackageForTests.set("ch.keepcalm.demo.base")
    baseClassForTests.set("ch.keepcalm.demo.base.BaseClass")
}

buildscript {
    repositories {
        maven {
            url = uri("https://plugins.gradle.org/m2/")
            url = uri("https://repo.spring.io/snapshot")
            url = uri("https://repo.spring.io/milestone")
            url = uri("https://repo.spring.io/release")
        }
    }
    dependencies {
        classpath("org.springframework.cloud", "spring-cloud-contract-gradle-plugin", "${property("springCloudContractGradlePlugin")}")
        classpath("org.springframework.cloud", "spring-cloud-contract-spec-kotlin", "${property("springCloudContractSpecKotlin")}")
    }
}


repositories {
    mavenCentral()
    maven { url = uri("https://repo.spring.io/milestone") }
}

springBoot {
    buildInfo()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-actuator")

	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

	implementation("org.springframework.cloud:spring-cloud-starter-gateway")
	implementation("org.springframework.cloud:spring-cloud-starter-circuitbreaker-reactor-resilience4j")

	implementation("org.springframework.hateoas:spring-hateoas")
	implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")

	implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")

	// Spring Cloud Contracts
	testImplementation("org.springframework.cloud", "spring-cloud-contract-spec-kotlin")
	testImplementation("org.springframework.cloud", "spring-cloud-starter-contract-verifier")
	testImplementation("io.rest-assured", "spring-web-test-client")
	testImplementation("org.springframework.restdocs", "spring-restdocs-webtestclient")

	testImplementation("org.springframework.boot:spring-boot-starter-test")

	asciidoctor("org.springframework.restdocs:spring-restdocs-asciidoctor")
}

publishing {
    publications {
        create<MavenPublication>("contracts") {
            artifact(tasks.named("verifierStubsJar"))
            // https://github.com/spring-gradle-plugins/dependency-management-plugin/issues/273
            versionMapping {
                usage("java-api") {
                    fromResolutionOf("runtimeClasspath")
                }
                usage("java-runtime") {
                    fromResolutionResult()
                }
            }
        }
    }
}

tasks.bootJar {
    this.dependsOn(tasks.test)
    from("$buildDir/asciidoc/html5") {
        into("static/docs")
    }
}

tasks.jacocoTestReport {
    reports {
        xml.isEnabled = true
        csv.isEnabled = false
        html.destination = file("$buildDir/jacocoHtml")
    }
}
tasks.jacocoTestCoverageVerification {
    violationRules {
        rule {
            enabled = true
            element = "CLASS"
            excludes = listOf("com.jacoco.dto.*")
            limit {
                counter = "BRANCH"
                minimum = 0.0.toBigDecimal()
            }
        }
    }
}
tasks.test {
    finalizedBy(tasks.contractTest)
//    finalizedBy(tasks.asciidoctor)
    // report is always generated after tests run
    finalizedBy(tasks.jacocoTestReport)
    finalizedBy(tasks.jacocoTestCoverageVerification)
}

tasks.withType<Test> {
    useJUnitPlatform()
}
java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}
tasks.withType<KotlinCompile> {
    kotlinOptions {
        languageVersion = "1.6"
        apiVersion = "1.6"
        jvmTarget = "11"
        freeCompilerArgs = listOf("-Xjsr305=strict")
    }
}
tasks.withType<Test> {
    useJUnitPlatform()
}
