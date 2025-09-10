pipeline {
    agent any

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
                    // Run container (tests will auto-execute because of CMD mvn test in Dockerfile)
                    sh """
                        docker run --name ${CONTAINER_NAME} ${IMAGE_NAME}:latest || true
                        docker cp ${CONTAINER_NAME}:/app/target/surefire-reports ./surefire-reports || true
                        docker rm ${CONTAINER_NAME} || true
                    """
                }
            }
        }

        stage('Publish Reports') {
    			steps {
        			junit 'target/surefire-reports/*.xml'   // TestNG results
        			archiveArtifacts artifacts: 'test-output/ExtentReport.html', allowEmptyArchive: true

		        // If HTML Publisher Plugin is installed
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
