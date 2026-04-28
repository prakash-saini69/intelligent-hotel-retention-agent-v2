pipeline {
    agent any

    environment {
        PYTHON_ENV = 'venv'
    }

    stages {
        stage('Checkout Code') {
            steps {
                git branch: 'main', url: 'https://github.com/your-username/hotel-retention-system.git'
            }
        }

        stage('Set Up Python Environment') {
            steps {
                sh '''
                    python3 -m venv $PYTHON_ENV
                    . $PYTHON_ENV/bin/activate
                    pip install --upgrade pip
                    pip install -r requirements.txt
                '''
            }
        }

        stage('Run Tests') {
            steps {
                sh '''
                    . $PYTHON_ENV/bin/activate
                    pytest tests/
                '''
            }
        }

        stage('Run Application Check') {
            steps {
                sh '''
                    . $PYTHON_ENV/bin/activate
                    python main.py
                '''
            }
        }

        stage('Deploy') {
            steps {
                echo 'Deploying Hotel Retention System...'
                sh '''
                    echo "Deployment completed"
                '''
            }
        }
    }

    post {
        always {
            echo 'Pipeline execution finished.'
        }
        success {
            echo 'Build succeeded!'
        }
        failure {
            echo 'Build failed!'
        }
    }
}