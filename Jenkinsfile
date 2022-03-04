#!/usr/bin/env groovy

node {
    properties([disableConcurrentBuilds()])

    try {
        project = "food-delivery"
        dockerRepo = "leequang198"
        dockerFile = "Dockerfile"
        imageName = "${dockerRepo}/${project}"
        buildNumber = "${env.BUILD_NUMBER}"

        stage('checkout code') {
            checkout scm
            sh "git checkout ${env.BRANCH_NAME} && git reset --hard origin/${env.BRANCH_NAME}"
        }

        stage('build') {
            sh "docker build -t ${imageName}:${env.BRANCH_NAME}-build-${buildNumber} -f ./Dockerfile ."
        }

        stage('push') {
              sh "docker push ${imageName}:${env.BRANCH_NAME}-build-${buildNumber}"
        }

    } catch (e) {
        currentBuild.result = "FAILED"
        throw e
    }
}