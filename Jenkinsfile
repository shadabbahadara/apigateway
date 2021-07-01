pipeline {
    agent any
    tools {
        maven 'mavenGTC'
    }
    environment {
        dockerImage = ''
        registry = 'localhost:5000/apigateway'
    }
    stages {
        stage('checkout') {
            steps {
                echo 'checking out...'
                git credentialsId: 'github', url: 'https://github.com/shadabbahadara/apigateway.git'
                echo 'checked out...'
            }
        }
        stage('build') {
            steps {
                echo 'building...'
                sh "mvn package"
            }
        }
        stage('build docker image') {
            steps {
                echo 'building docker image...'
                script {
                    dockerImage = docker.build registry
                }
            }
        }
        stage('push docker image') {
            steps {
                echo 'pushing docker image to docker registry...'
                script {
                    dockerImage.push()
                }
            }
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