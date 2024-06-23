buildscript {
    val agp_version by extra("8.1.2")
}
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.1.2" apply false
    id("org.sonarqube") version "5.0.0.4638"
    id("jacoco")
}

/*
sonarqube {
    properties {
        property("sonar.projectName", "pancapp")
        property("sonar.projectKey", "app-la-panca")
        property("sonar.lenguage", "java")
        property("sonar.sources", "src/main/java")
        property("sonar.binaries", "build")
        property("sonar.sourceEncoding", "UTF-8")
        property("sonar.token", "squ_9a85a5d51eeb4dc04c69e18c75487d0fd823f407")
        property("sonar.login", "admin")
        property("sonar.password", "admin123")
    }
}
/*
sonar {
    properties {
        property("sonar.projectKey", "panca")
        property("sonar.host.url", "http://localhost:9000")
        property("sonar.token", "sqp_21c8a43207c6a73f1b292c416df4e4389d39419c")
        property("sonar.login", "admin")
        property("sonar.password", "admin1")
    }
}*/