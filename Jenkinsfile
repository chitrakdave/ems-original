pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                git branch: "${params.BRANCH}",
                    credentialsId: 'ems-github-credentials',
                    url: 'https://github.com/chitrakdave/ems.git'
            }
        }

        stage('Maven Build') {
            steps {
                sh './mvnw clean package'
            }
        }
    }
}
