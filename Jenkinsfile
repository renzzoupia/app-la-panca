pipeline {
    agent any
    tools {
        jdk 'JDK' // Asegúrate de tener configurada una instalación de JDK en Jenkins
        gradle 'GRADLE' // Asegúrate de tener configurada una instalación de Gradle en Jenkins
    }

    environment {
        ANDROID_HOME = "/opt/android-sdk" // Reemplaza con la ruta real del SDK de Android en tu servidor Jenkins
        PATH = "${env.PATH}:${env.ANDROID_HOME}/cmdline-tools/latest/bin:${env.ANDROID_HOME}/platform-tools"
    }

    stages {
        stage('Preparation') {
            steps {
                git 'https://github.com/joseht88/ms-producto.git'
                echo 'Pulled from GitHub successfully'
                // Otorga permisos de ejecución al archivo gradlew
                sh 'chmod +x ./gradlew'
                // Crear o actualizar el archivo local.properties
                sh "echo 'sdk.dir=${ANDROID_HOME}' > local.properties"
            }
        }
        stage('Compile the code to executable format') {
            steps {
                sh './gradlew assembleDebug'
                echo 'Converted human-readable code to machine-readable code'
            }
        }
        stage('Testing the code') {
            steps {
                sh './gradlew test'
                echo 'Unit Test successfully'
            }
        }
        stage('Analysis SonarQube') {
            steps {
                sh './gradlew sonarqube -Dsonar.login=squ_3efe3bdf584ad2a8ef47e1e0ca6d169f77dff6bf -Dsonar.projectKey=sqape -Dsonar.projectName="SQAPE BackEnd" -Dsonar.host.url=http://172.19.231.56:9000'
                echo 'SonarQube Code review done'
            }
        }
        stage('Build') {
            steps {
                sh './gradlew assembleRelease -x test'
                echo 'Packaging project'
            }
        }
    }
}
