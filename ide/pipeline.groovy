pipeline {
    agent any
    triggers { pollSCM('* * * * *')}
    stages {
        stage('Checkout') {
            steps {
                // Get some code from a GitHub repository
                git 'https://github.com/utahranger3/spring-petclinic.git'
            }
        }
        stage('Build') {
            steps {
                //sh './mvnw clean package'
                sh 'false' //true
            }

            post {
                always {
                    // junit '**/target/surefire-reports/TEST-*.xml'
                    // archiveArtifacts 'target/*.jar'
                // }
                // changed {
                    emailext attachLog: true, body: "Please go to ${BUILD_URL} and verify the build", compressLog: true, recipientProviders: [upstreamDevelopers(), requestor()], subject: "Job \'${JOB_NAME}\' (${BUILD_NUMBER}) ${currentBuild.result}", to:"test@jenkins"
                }
            }
        }
    }
}