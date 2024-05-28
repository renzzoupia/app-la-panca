pipeline {
    agent any

    tools {
        maven 'MAVEN'
    }

    stages {
        stage('Checkout') {
            steps {
                // Clona el repositorio desde GitHub
                git 'https://github.com/renzzoupia/app-la-panca.git'
            }
        }

        stage('Build') {
            steps {
                // Construye el proyecto
                sh './gradlew assembleDebug'
            }
        }

        stage('Unit Tests') {
            steps {
                // Ejecuta las pruebas unitarias
                sh './gradlew test'
            }
            post {
                always {
                    // Publica los resultados de las pruebas unitarias
                    junit '**/build/test-results/testDebugUnitTest/*.xml'
                }
            }
        }
    }

    post {
        always {
            // Limpia el directorio de trabajo
            cleanWs()
        }
    }
}
