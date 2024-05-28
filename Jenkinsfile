pipeline {
    agent any

    tools {
        maven 'MAVEN'
        nodejs 'NodeJS'
    }

    stages {
        stage('Clone') {
            dir('.') {
                git branch: 'main', credentialsId: 'github_com', url: 'git@github.com:renzzoupia/app-la-panca.git'
            }
        }

        stage('Build') {
            withGradle {
                sh './gradlew clean build --stacktrace -i'
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
        //Revisa la calidad de código con SonarQube
        stage('Analysis SonarQube') {
           steps {
                 sh 'mvn sonar:sonar -Dsonar.login=squ_b2f1d63b7b4fbb6179a37c3fb1ccbd457316d343 -Dsonar.projectKey=app-la-panca -Dsonar.projectName="LaPancaProject"'
                 echo 'SonarQube Code review done'
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
