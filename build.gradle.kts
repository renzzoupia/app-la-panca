buildscript {
    val agp_version by extra("8.1.2")
}
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.1.2" apply false
   // id("org.sonarqube") version "5.0.0.4638"
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