plugins {
    id("java-library")
    id("jacoco")
    id("maven-publish")
    alias(libs.plugins.lombok)
    alias(libs.plugins.release)
    alias(libs.plugins.sonarcloud)
}

group = "net.internalerror"

repositories {
    mavenCentral()
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    implementation(libs.annotations)
    testImplementation(libs.junit)
}

tasks {
    test {
        useJUnitPlatform()
        finalizedBy(jacocoTestReport)
    }

    jacocoTestReport {
        dependsOn(test)

        reports {
            xml.required = true
        }
    }

    afterReleaseBuild {
        dependsOn(publish)
    }
}

publishing {

    val githubUsername: String by project
    val githubToken: String by project

    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/philipp-gatzka/csv-lib")
            credentials {
                username = githubUsername
                password = githubToken
            }
        }
    }
    publications {
        register<MavenPublication>("gpr") {
            from(components["java"])
        }
    }
}

sonar {
    properties {
        property("sonar.projectKey", "net.internalerror:csv-lib")
        property("sonar.organization", "philipp-gatzka")
        property("sonar.host.url", "https://sonarcloud.io")
    }
}

