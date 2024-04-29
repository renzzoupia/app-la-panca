// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.1.2" apply false
    id("org.sonarqube") version "5.0.0.4638"
}
sonarqube {
    properties {
        property("sonar.projectKey", "prueba")
        property("sonar.host.url", "http://localhost:9000")
        property("sonar.token", "sqp_d6602c7c0a244ad76ce41d1398a9e280c2a2a8d6")
        property("sonar.login", "admin")
        property("sonar.password", "admin1")
    }
}