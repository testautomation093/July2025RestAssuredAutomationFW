pipeline {
    agent any

    tools {
        maven 'maven'
    }

    environment {
        BUILD_NUMBER = "${BUILD_NUMBER}"
    }

    stages {

        stage('Build') {
            steps {
                git 'https://github.com/jglick/simple-maven-project-with-tests.git'
                sh "mvn -Dmaven.test.failure.ignore=true clean package"
            }
            post {
                success {
                    junit '**/target/surefire-reports/TEST-*.xml'
                    archiveArtifacts 'target/*.jar'
                }
            }
        }

        stage("Deploy to QA") {
            steps {
                echo("Deploying the project to QA Env")
            }
        }

        stage('Run Docker Image with Regression Tests') {
            steps {
                script {
                    echo "Starting Regression Tests in Docker container..."
                    
                    def exitCode = sh(
                        script: """
                            docker run --name apitesting${BUILD_NUMBER} \
                            -e MAVEN_OPTS='-Dsurefire.suiteXmlFiles=src/test/resources/testrunners/testng_regression.xml' \
                            testautomation093/apifwpractice2:latest
                        """,
                        returnStatus: true
                    )

                    if (exitCode != 0) {
                        currentBuild.result = 'FAILURE'
                    }

                    // Copy allure-results from container (if available)
                    sh "docker start apitesting${BUILD_NUMBER} || true"
                    sh "docker cp apitesting${BUILD_NUMBER}:/app/allure-results ${WORKSPACE}/allure-results || true"
                    sh "docker rm -f apitesting${BUILD_NUMBER} || true"
                }
            }
        }

        stage('Publish Allure Reports - Regression') {
            steps {
                script {
                    echo "Publishing Allure Reports..."
                    allure([
                        includeProperties: false,
                        jdk: '',
                        properties: [],
                        reportBuildPolicy: 'ALWAYS',
                        results: [[path: 'allure-results']]
                    ])
                }
            }
        }

        stage("Deploy to Stage") {
            steps {
                echo("Deploying the Project to Stage")
            }
        }

        stage('Run Docker Image with Sanity Tests') {
            steps {
                script {
                    echo "Starting Sanity Tests in Docker container..."
                    
                    def exitCode = sh(
                        script: """
                            docker run --name apitesting_sanity${BUILD_NUMBER} \
                            -e MAVEN_OPTS='-Dsurefire.suiteXmlFiles=src/test/resources/testrunners/testng_sanity.xml' \
                            testautomation093/apifwpractice2:latest
                        """,
                        returnStatus: true
                    )

                    if (exitCode != 0) {
                        currentBuild.result = 'FAILURE'
                    }

                    // Clean up container, but no report publishing
                    sh "docker rm -f apitesting_sanity${BUILD_NUMBER} || true"
                }
            }
        }

        stage("Deploy to PROD") {
            steps {
                echo("Deploying the project to PROD")
            }
        }
    }
}
