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
		        echo Removing old container if exists...
		        docker rm -f %CONTAINER_NAME% || exit /b 0
		
		        echo Running Selenium + TestNG tests in Docker container (detached)...
		        docker run -d --name %CONTAINER_NAME% %IMAGE_NAME%:latest
		
		        echo Streaming logs from container...
		        docker logs -f %CONTAINER_NAME%
		
		        echo Copying test reports from container...
		        docker cp %CONTAINER_NAME%:/app/target/surefire-reports .\\surefire-reports || exit /b 0
		        docker cp %CONTAINER_NAME%:/app/test-output .\\test-output || exit /b 0
		
		        echo Removing container...
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
