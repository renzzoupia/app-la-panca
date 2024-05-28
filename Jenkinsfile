pipeline {
    agent any
    tools {
        jdk 'JDK' // Asegúrate de tener configurada una instalación de JDK en Jenkins
        gradle 'GRADLE' // Asegúrate de tener configurada una instalación de Gradle en Jenkins
    }

    environment {
        ANDROID_SDK_ROOT = 'C:\\Users\\Renzo\\AppData\\Local\\Android\\Sdk'
    }

    stages {
        stage('Preparation') {
            steps {
                git branch: 'main',url: 'https://github.com/renzzoupia/app-la-panca'
                // Otorga permisos de ejecución al archivo gradlew
                sh 'chmod +x ./gradlew'
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
    }
}
