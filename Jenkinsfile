pipeline {
    agent any
    stages {
        stage('checkout') {
            steps {
                echo 'checking out...'
                git credentialsId: 'github', url: 'https://github.com/shadabbahadara/apigateway.git'
            }
        }
        stage('build') {
            steps {
                echo 'building...'
                mvn package
            }
        }
    post {
        success {
            echo 'success message...'
        }
        failure {
            echo 'failure message...'
        }
    }
}