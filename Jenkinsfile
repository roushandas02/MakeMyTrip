pipeline {
    agent {
        docker {
            image 'docker:24.0'
            args '--privileged -v /var/run/docker.sock:/var/run/docker.sock'
        }
    }

    environment {
        IMAGE_NAME = "selenium-testng-makemytrip"
        CONTAINER_NAME = "selenium-testng-run"
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'master', url: 'https://github.com/roushandas02/MakeMyTrip.git'
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    echo "Building Docker image: ${IMAGE_NAME}"
                    sh "docker build -t ${IMAGE_NAME}:latest ."
                }
            }
        }

        stage('Run Selenium Tests') {
            steps {
                script {
                    echo "Running Selenium + TestNG tests in Docker container..."
                    sh """
                        docker run --name ${CONTAINER_NAME} ${IMAGE_NAME}:latest || true
                        docker cp ${CONTAINER_NAME}:/app/target/surefire-reports ./surefire-reports || true
                        docker cp ${CONTAINER_NAME}:/app/test-output ./test-output || true
                        docker rm ${CONTAINER_NAME} || true
                    """
                }
            }
        }

        stage('Publish Reports') {
            steps {
                // Corrected path for JUnit
                junit 'surefire-reports/*.xml'

                // Archive Extent Report HTML
                archiveArtifacts artifacts: 'test-output/ExtentReport.html', allowEmptyArchive: true

                // Publish Extent Report in Jenkins UI (requires HTML Publisher Plugin)
                publishHTML(target: [
                    reportDir: 'test-output',
                    reportFiles: 'ExtentReport.html',
                    reportName: 'Extent Report'
                ])
            }
        }
    }

    post {
        always {
            echo 'Cleaning workspace...'
            cleanWs()
        }
    }
}
