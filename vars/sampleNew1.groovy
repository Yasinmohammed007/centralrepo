def call(String name = 'User') {
		echo "Welcome, ${name}."
}
pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                echo 'Building..'
                echo "Welcome, ${name}."
            }
        }
        stage('Test') {
            steps {
                echo 'Testing..'
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploying....'
            }
        }
    }
}
