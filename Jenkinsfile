pipeline {
    agent any

    environment {
        // Specify that uv should not prompt
        UV_NO_SYNC = '1'
    }

    stages {
        stage('Checkout Code') {
            steps {
                checkout scm
                echo 'Source code fully checked out!'
            }
        }
        
        stage('Setup Dependencies') {
            steps {
                sh '''
                echo "Python version:"
                python3 --version
                
                echo "Installing uv..."
                uv --version || echo "uv is already available"

                echo "Creating virtual environment and installing dependencies..."
                uv venv
                uv pip install -r requirements.txt
                '''
            }
        }

        stage('Code Compile & Syntax Check') {
            steps {
                sh '''
                # Run python compile to check for syntax errors
                .venv/bin/python -m py_compile main.py app.py
                echo "Code syntax checked successfully!"
                '''
            }
        }
        
        // This is a placeholder for actual deployment
        stage('Deploy (Mock)') {
            steps {
                echo 'Starting deployment to mock server...'
                echo 'Deployment successful! 🎉'
            }
        }
    }
    
    post {
        success {
            echo '✅ Pipeline execution completed successfully!'
        }
        failure {
            echo '❌ Pipeline execution failed. Please check the logs.'
        }
    }
}
