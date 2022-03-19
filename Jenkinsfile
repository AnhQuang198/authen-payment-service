#!/usr/bin/env groovy

node {
    properties([disableConcurrentBuilds()])

    try {
        project = "authen-payment-service"
        dockerRepo = "leequang198"
        dockerFile = "Dockerfile"
        imageName = "${dockerRepo}/${project}"
        buildNumber = "${env.BUILD_NUMBER}"

        stage('checkout code') {
            checkout scm
            sh "git checkout ${env.BRANCH_NAME} && git reset --hard origin/${env.BRANCH_NAME}"
        }

        stage('build') {
            sh "docker build -t ${imageName}:${env.BRANCH_NAME}-build -f ./Dockerfile ."
        }

        switch (env.BRANCH_NAME) {
            case 'develop':
                stage('run-dev') {
                    sh """
                        cd /home/server-dev-end/
                        pwd
                    """
                }
                break
            case 'master':
                stage('run-prod') {
                    sh """
                        cd /home/server-prod-end/
                        pwd
                    """
                }
                break
        }

    } catch (e) {
        currentBuild.result = "FAILED"
        throw e
    }
}