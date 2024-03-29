import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.6.4"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("jvm") version "1.6.10"
    kotlin("plugin.spring") version "1.6.10"
    kotlin("kapt") version "1.6.10"
    id("org.springframework.cloud.contract") version "3.1.1"
    id("org.asciidoctor.jvm.convert") version "3.3.2"

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
    implementation("org.springframework.boot:spring-boot-starter-hateoas")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.atteo:evo-inflector:$evoInflector")

    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
    runtimeOnly("com.h2database:h2")

    //	This will also create index root api - http://docker/myapp/services/customer/browser/index.html#http://docker/myapp/services/customer/
    implementation("org.springframework.data:spring-data-rest-hal-browser:$halBrowser")
    // HAL Explorer http://docker/myapp/services/customer/explorer/index.html#uri=http://docker/myapp/services/customer/ -->
    implementation("org.springframework.data:spring-data-rest-hal-explorer")

    //	Used for WebClient
    implementation("org.springframework.boot:spring-boot-starter-webflux")

    // Client-Side Load-Balancing
    implementation("org.springframework.cloud:spring-cloud-starter-loadbalancer")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    testImplementation("io.github.serpro69", "kotlin-faker", "$kotlinFaker")


    // Spring Cloud Contracts
    testImplementation("org.springframework.cloud", "spring-cloud-contract-spec-kotlin")
    testImplementation("org.springframework.cloud", "spring-cloud-starter-contract-stub-runner")
    testImplementation("org.springframework.cloud", "spring-cloud-starter-contract-verifier")
    testImplementation("org.springframework.restdocs", "spring-restdocs-mockmvc")

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
    from("$buildDir/docs/asciidoc") {
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
    finalizedBy(tasks.asciidoctor)
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
