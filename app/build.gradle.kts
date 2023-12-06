plugins {
    java
    application
    kotlin("jvm") version "1.9.0"
    id("org.openjfx.javafxplugin").version("0.0.13")

}

group = "edu.austral.dissis.chess"
version = "1.0.0"

repositories {
//    mavenLocal()
    mavenCentral()
    maven {
        url = uri("https://maven.pkg.github.com/austral-ingsis/chess-ui")
        credentials {
            username =  project.properties["gpr.user"]!!.toString()
            password = project.properties["gpr.token"]!!.toString()
        }
    }
//    maven {
//        url = uri("https://maven.pkg.github.com/austral-ingis/chess-simple-client-server")
//        credentials {
//            username =  project.properties["gpr.user"]!!.toString()
//            password = project.properties["gpr.token"]!!.toString()
//        }
//    }
}

dependencies {
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("edu.austral.dissis.chess:chess-ui:2.0.1")
    implementation("edu.austral.dissis.chess:simple-client-server:1.2.0")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.0")
    //testImplementation(kotlin("test"))
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}

javafx {
    version = "18"
    modules = listOf("javafx.graphics")
}

application {
    // Define the main class for the application.
    mainClass.set("edu.austral.dissis.chess.AppKt")
}

tasks.test {
    useJUnitPlatform()
}

