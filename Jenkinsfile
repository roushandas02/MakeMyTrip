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

        stage('Check Docker Installation') {
            steps {
                bat '''
                docker --version >nul 2>&1
                if %errorlevel% neq 0 (
                    echo Docker is not installed or not running
                    exit /b 1
                ) else (
                    echo Docker is installed
                    docker --version
                )
                '''
            }
        }

        stage('Build Docker Image') {
            steps {
                bat '''
                echo Building Docker image: %IMAGE_NAME%
                docker build -t %IMAGE_NAME%:latest .
                '''
            }
        }
        

        stage('Run Selenium Tests') {
            steps {
                bat '''
                echo Running Selenium + TestNG tests in Docker container...
                docker run --name %CONTAINER_NAME% %IMAGE_NAME%:latest || exit /b 0
                docker cp %CONTAINER_NAME%:/app/target/surefire-reports .\\surefire-reports || exit /b 0
                docker cp %CONTAINER_NAME%:/app/test-output .\\test-output || exit /b 0
                docker rm %CONTAINER_NAME% || exit /b 0
                '''
            }
        }

        stage('Publish Reports') {
            steps {
                junit 'surefire-reports/*.xml'
                archiveArtifacts artifacts: 'test-output/ExtentReport.html', allowEmptyArchive: true

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
            cleanWs()
        }
    }
}
