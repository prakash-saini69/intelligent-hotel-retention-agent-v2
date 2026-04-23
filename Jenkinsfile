pipeline {
    agent any

    environment {
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
                echo "Creating virtual environment and installing dependencies..."
                uv venv
                uv pip install -r requirements.txt
                '''
            }
        }

        stage('Code Compile & Syntax Check') {
            steps {
                sh '''
                .venv/bin/python -m py_compile main.py app.py
                echo "Code syntax checked successfully!"
                '''
            }
        }
        
        stage('Deploy (CD)') {
            steps {
                echo 'Stopping old application versions...'
                sh '''
                pkill -f "python main.py" || true
                pkill -f "streamlit" || true
                '''

                echo 'Starting new application versions in the background...'
                sh '''
                JENKINS_NODE_COOKIE=dontKillMe nohup uv run python main.py > backend.log 2>&1 &
                JENKINS_NODE_COOKIE=dontKillMe nohup uv run streamlit run app.py > frontend.log 2>&1 &
                '''
            }
        }
    }
    
    post {
        success {
            echo '✅ Pipeline execution completed successfully! Apps are running.'
        }
        failure {
            echo '❌ Pipeline execution failed. Please check the logs.'
        }
    }
}