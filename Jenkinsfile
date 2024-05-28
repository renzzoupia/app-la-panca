node {
  
  stage('Clone') {
      dir('.') {
          git branch: 'main', credentialsId: 'github_com', url: 'git@github.com:renzzoupia/app-la-panca.git'
          // Otorga permisos de ejecución al archivo gradlew
          sh 'chmod +x ./gradlew'
      }    
  }       

  stage('Build') {
      withGradle {
        sh './gradlew clean build --stacktrace -i'
      }  
  }
  
}