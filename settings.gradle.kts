rootProject.name = "csv-lib"

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            library("annotations", "org.jetbrains", "annotations").version("24.0.0")
            library("junit", "org.junit.jupiter", "junit-jupiter-engine").version("5.9.2")
            plugin("lombok", "io.freefair.lombok").version("8.6")
            plugin("release", "net.researchgate.release").version("3.0.2")
            plugin("sonarcloud", "org.sonarqube").version("4.4.1.3373")
        }
    }
}