pipeline {
    agent any

    environment {
        DOCKER_IMAGE = 'glad2os/lab2-maven'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build Maven Project') {
            steps {
                powershell './mvnw.cmd clean package -DskipTests'
            }
        }

        stage('Code Coverage') {
            steps {
                powershell './mvnw.cmd test jacoco:report'
            }
        }

        stage('Docker Build') {
            steps {
                script {
                    // Building Docker image with tag
                    docker.build("${DOCKER_IMAGE}")
                }
            }
        }

        stage('Docker Push') {
            steps {
                script {
                    // Using Docker withRegistry for login and push
                    docker.withRegistry('https://registry.hub.docker.com', 'your-docker-credentials-id') {
                        // Pushing the built Docker image within the registry login context
                        docker.image("${DOCKER_IMAGE}").push()
                    }
                }
            }
        }
    }

    post {
        always {
            echo 'Build completed'
        }
    }
}
