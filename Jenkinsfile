pipeline{
    agent any
    tools{
        maven 'maven-3.9.6'
    }
    stages{
        stage('Git checkout'){
            steps{
                git url: 'https://github.com/kol2104/jenkins-test.git', branch: 'master'
            }
        }
        stage('Build application'){
            steps{
                sh 'mvn clean package'
            }
        }
        stage('Test and JaCoCo analysis'){
            steps{
                junit 'target/surefire-reports/**/*.xml'
                jacoco()
            }
        }
        stage('Sonarqube scanner'){
            steps{
                withSonarQubeEnv(installationName: 'sonarqube', credentialsId: 'jenkins-sonar-token') {
                    sh 'mvn sonar:sonar'
                }
            }
        }
        stage('Deploy to tomcat') {
            steps{
                deploy adapters: [tomcat9(credentialsId: 'jenkins-tomcat-cred', path: '', url: 'http://localhost:8081')], \
                    contextPath: '/my-app', war: 'target/demo-0.0.1-SNAPSHOT.war'
            }
        }
    }
}