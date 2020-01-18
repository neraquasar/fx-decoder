plugins {
    java
    application
    id("org.openjfx.javafxplugin") version("0.0.8")
}

group = "ru.neraquasar"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

application {
    mainClassName = "example.HelloFX"
}

val startScripts by tasks.existing(CreateStartScripts::class) {}
startScripts {
    doLast {
        windowsScript.writeText(
                windowsScript.readText().replace("%JAVA_OPTS%", "--module-path \"%APP_HOME%\\lib\" --add-modules javafx.controls")
        )
    }
}

javafx {
    version = "13"
    modules("javafx.controls")
}

dependencies {
    testCompile("junit", "junit", "4.12")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_11
}

