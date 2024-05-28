pipeline {
    agent any
    tools {
        // Define las herramientas que necesitas, por ejemplo, JDK y Gradle
        jdk 'JDK' // Asegúrate de tener configurada una instalación de JDK en Jenkins
        gradle 'GRADLE' // Asegúrate de tener configurada una instalación de Gradle en Jenkins
    }

    environment {
            ANDROID_HOME = "/opt/android-sdk" // Reemplaza esto con la ruta real del SDK de Android en tu servidor
            PATH = "${env.PATH}:${env.ANDROID_HOME}/cmdline-tools/latest/bin:${env.ANDROID_HOME}/platform-tools"
    }

    stages {
        stage('Preparation') {
            steps {
                git branch: 'main',url: 'https://github.com/renzzoupia/app-la-panca'
                // Otorga permisos de ejecución al archivo gradlew
                sh 'chmod +x ./gradlew'
            }
        }
        stage('Unit & Integration Tests') {
            steps {
                script {
                    try {
                        sh './gradlew clean test --no-daemon' //run a gradle task
                    } finally {
                        junit '**/build/test-results/test/*.xml' //make the junit test results available in any case (success & failure)
                    }
                }
            }
        }

    }
}
