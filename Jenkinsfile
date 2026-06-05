pipeline {
    agent any

    parameters {
        choice(
                name: 'PLATFORM',
                choices: ['android', 'ios'],
                description: 'Mobile platform tag to run'
        )
    }

    stages {
        stage('Upload Android app') {
            when {
                expression { params.PLATFORM == 'android' }
            }
            steps {
                withCredentials([
                        usernamePassword(
                                credentialsId: 'browserstack-credentials',
                                usernameVariable: 'BROWSERSTACK_USER',
                                passwordVariable: 'BROWSERSTACK_KEY'
                        )
                ]) {
                    script {
                        def uploadCommand = 'curl -s -u "$BROWSERSTACK_USER:$BROWSERSTACK_KEY" -X POST "https://api-cloud.browserstack.com/app-automate/upload" -F "url=https://www.browserstack.com/app-automate/sample-apps/android/WikipediaSample.apk"'
                        def response = isUnix()
                                ? sh(script: uploadCommand, returnStdout: true).trim()
                                : bat(script: '@curl.exe -s -u "%BROWSERSTACK_USER%:%BROWSERSTACK_KEY%" -X POST "https://api-cloud.browserstack.com/app-automate/upload" -F "url=https://www.browserstack.com/app-automate/sample-apps/android/WikipediaSample.apk"', returnStdout: true).trim()

                        env.BROWSERSTACK_APP_ANDROID = new groovy.json.JsonSlurperClassic()
                                .parseText(response)
                                .app_url
                    }
                }
            }
        }

        stage('Tests') {
            steps {
                withCredentials([
                        usernamePassword(
                                credentialsId: 'browserstack-credentials',
                                usernameVariable: 'BROWSERSTACK_USER',
                                passwordVariable: 'BROWSERSTACK_KEY'
                        )
                ]) {
                    script {
                        def command = "./gradlew clean test -Dplatform=${params.PLATFORM} -DdeviceHost=browserstack"

                        if (isUnix()) {
                            sh command
                        } else {
                            bat command.replace('./gradlew', 'gradlew.bat')
                        }
                    }
                }
            }
        }
    }

    post {
        always {
            allure(
                    includeProperties: false,
                    jdk: '',
                    results: [[path: 'build/allure-results']]
            )
        }
    }
}
