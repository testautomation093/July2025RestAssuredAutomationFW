pipeline {
    agent any

    tools {
        maven 'maven'
    }

    stages {

        stage('Build') {
            steps {
                git 'https://github.com/jglick/simple-maven-project-with-tests.git'
                bat "mvn -Dmaven.test.failure.ignore=true clean package"
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
                echo "Deploying the project to QA Env"
            }
        }

        stage('Regression API Automation Tests') {
            steps {
                catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                    git 'https://github.com/testautomation093/July2025RestAssuredAutomationFW.git'
                    // Store results in allure-results
                    bat "mvn clean test -Dsurefire.suiteXmlFiles=src/test/resources/testrunners/testng_regression.xml -Dallure.results.directory=allure-results"
                }
            }
        }

        stage('Publish Allure Reports') {
            steps {
                script {
                    // Clean old report directory before generating new one
                    bat 'if exist allure-report (rmdir /s /q allure-report)'

                    // Publish Allure report for Regression
                    allure([
                        includeProperties: false,
                        jdk: '',
                        reportBuildPolicy: 'ALWAYS',
                        results: [[path: 'allure-results']]
                    ])
                }
            }
        }

        stage("Deploy to Stage") {
            steps {
                echo "Deploying the Project to Stage"
            }
        }

        stage('Sanity API Automation Test') {
            steps {
                catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                    git 'https://github.com/testautomation093/July2025RestAssuredAutomationFW.git'
                    // Store sanity results separately
                    bat "mvn clean test -Dsurefire.suiteXmlFiles=src/test/resources/testrunners/testng_sanity.xml -Dallure.results.directory=allure-results-sanity"
                }
            }
        }

        stage('Publish Allure Reports for Sanity Env') {
            steps {
                script {
                    // Clean any existing sanity report directory
                    bat 'if exist allure-report-sanity (rmdir /s /q allure-report-sanity)'

                    // Generate unique report folder for sanity results
                    bat '"%ALLURE_HOME%\\bin\\allure.bat" generate allure-results-sanity -c -o allure-report-sanity'

                    // Publish Allure report for Sanity (so it appears in Jenkins UI)
                    allure([
                        includeProperties: false,
                        jdk: '',
                        reportBuildPolicy: 'ALWAYS',
                        results: [[path: 'allure-results-sanity']]
                    ])
                }
            }
        }

        stage("Deploy to PROD") {
            steps {
                echo "Deploying the project to PROD"
            }
        }
    }
}
