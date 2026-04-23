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
                // Changed from 'sh' to 'bat' for Windows
                bat '''
                echo Creating virtual environment and installing dependencies...
                uv venv
                uv pip install -r requirements.txt
                '''
            }
        }

        stage('Code Compile & Syntax Check') {
            steps {
                // Windows uses .venv\\Scripts\\python.exe instead of .venv/bin/python
                bat '''
                .venv\\Scripts\\python.exe -m py_compile main.py app.py
                echo Code syntax checked successfully!
                '''
            }
        }
        
        stage('Deploy (CD)') {
            steps {
                echo 'Stopping old application versions...'
                // taskkill is the Windows version of pkill. 
                // || exit 0 ensures the pipeline doesn't fail if the apps aren't running yet.
                bat '''
                taskkill /F /IM python.exe /T >nul 2>&1 || exit 0
                '''

                echo 'Starting new application versions in the background...'
                // 'start /B' is the Windows equivalent of 'nohup ... &' for background tasks
                // BUILD_ID=dontKillMe tells Windows Jenkins not to close your app when the pipeline finishes
                bat '''
                set BUILD_ID=dontKillMe
                set JENKINS_NODE_COOKIE=dontKillMe
                start /B uv run python main.py > backend.log 2>&1
                start /B uv run streamlit run app.py > frontend.log 2>&1
                '''
            }
        }
    }
    
    post {
        success {
            echo '✅ Pipeline execution completed successfully! Apps are running natively on Windows.'
        }
        failure {
            echo '❌ Pipeline execution failed. Please check the logs.'
        }
    }
}