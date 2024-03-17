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
                powershell "docker build -t ${DOCKER_IMAGE} ."
            }
        }



        stage('Docker Login') {
            steps {
                script {
                    withCredentials([
                        string(credentialsId: 'docker-hub-username', variable: 'DOCKER_USERNAME'),
                        string(credentialsId: 'docker-hub-password', variable: 'DOCKER_PASSWORD')
                    ]) {
                        withEnv(["DOCKER_USERNAME=${DOCKER_USERNAME}", "DOCKER_PASSWORD=${DOCKER_PASSWORD}"]) {
                            powershell 'echo $env:DOCKER_PASSWORD | docker login --username $env:DOCKER_USERNAME --password-stdin'
                        }
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
