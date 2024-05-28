pipeline {
    agent any
    tools {
        // Define las herramientas que necesitas, por ejemplo, JDK y Gradle
        jdk 'JDK' // Asegúrate de tener configurada una instalación de JDK en Jenkins
        gradle 'GRADLE' // Asegúrate de tener configurada una instalación de Gradle en Jenkins
    }

    stages {
        stage('Preparation') {
            steps {
                git branch: 'main',url: 'https://github.com/renzzoupia/app-la-panca'
                // Otorga permisos de ejecución al archivo gradlew
                sh 'chmod +x ./gradlew'
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
