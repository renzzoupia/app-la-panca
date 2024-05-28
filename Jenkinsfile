pipeline {
    agent any
    tools {
        // Define las herramientas que necesitas, por ejemplo, JDK y Gradle
        jdk 'JDK' // Asegúrate de tener configurada una instalación de JDK en Jenkins
        gradle 'GRADLE' // Asegúrate de tener configurada una instalación de Gradle en Jenkins
    }
    
    environment {
        // Define el SDK de Android
        ANDROID_HOME = "/opt/android-sdk" // Reemplaza esto con la ruta real del SDK de Android en tu servidor
        PATH = "${env.PATH}:${env.ANDROID_HOME}/cmdline-tools/latest/bin:${env.ANDROID_HOME}/platform-tools"
    }

    stages {
        stage('Preparation') {
            steps {
                git 'https://github.com/renzzoupia/app-la-panca.git'
                echo 'Pulled from GitHub successfully'
            }
        }
        // Compila el código en formato ejecutable
        stage('Compile the code to executable format') {
            steps {
                sh './gradlew assembleDebug'
                echo 'Converted human-readable code to machine-readable code'
            }
        }
        // Compila los test y los ejecuta
        stage('Testing the code') {
            steps {
                sh './gradlew test'
                echo 'Unit Test successfully'
            }
        }
        // Revisa la calidad de código con SonarQube
        stage('Analysis SonarQube') {
            steps {
                sh './gradlew sonarqube -Dsonar.login=squ_b2f1d63b7b4fbb6179a37c3fb1ccbd457316d343 -Dsonar.projectKey=app-la-panca -Dsonar.projectName="LaPancaProject" -Dsonar.host.url=http://192.168.18.224:9000'
                echo 'SonarQube Code review done'
            }
        }
        // Empaqueta el proyecto
        stage('Build') {
            steps {
                sh './gradlew assembleRelease -x test'
                echo 'Packaging project'
            }
        }
    }
}
