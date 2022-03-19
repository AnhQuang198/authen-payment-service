#!/usr/bin/env groovy

node {
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
                    sh '''
                        #!/bin/bash
                        cd /
                        cd home
                        cd server
                        cd dev
                    '''
                }
                break
            case 'master':
                stage('run-prod') {
                    steps {
                        sh '''
                            #!/bin/bash
                            cd /
                            cd home
                            cd server
                            cd prod
                        '''
                    }
                }
                break
        }

    } catch (e) {
        currentBuild.result = "FAILED"
        throw e
    }
}