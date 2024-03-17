pipeline {
    agent any

    environment {
        DOCKER_IMAGE = 'glad2os/lab2-maven'
        DOCKER_CREDENTIALS_ID = 'glad2os-docker-credentials-id'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build Maven Project') {
            steps {
                powershell 'mvn clean package -DskipTests'
            }
        }

        stage('Code Coverage') {
            steps {
                powershell 'mvn test jacoco:report'
            }
        }

        stage('Docker Build') {
            steps {
                powershell "docker build -t ${DOCKER_IMAGE} ."
            }
        }

        stage('Docker Login') {
            steps {
                script {
                    withCredentials([usernamePassword(credentialsId: DOCKER_CREDENTIALS_ID, passwordVariable: 'DOCKERHUB_PASSWORD', usernameVariable: 'DOCKERHUB_USERNAME')]) {
                        powershell "echo $DOCKERHUB_PASSWORD | docker login --username $DOCKERHUB_USERNAME --password-stdin"
                    }
                }
            }
        }

        stage('Docker Push') {
            steps {
                powershell "docker push ${DOCKER_IMAGE}"
            }
        }
    }

    post {
        always {
            echo 'Build completed'
        }
    }
}
