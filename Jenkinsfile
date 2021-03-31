pipeline {
  agent any
  stages {
    stage("build") {
      steps {
          sh "Build application..."
      }
    }
    stage("test") {
      steps {
          sh "Test application"
      }
    }
    
    stage("deploy") {
      steps {
          sh "Deploy application"
      }
    }
  }

}
