#!/usr/bin/env groovy

/**
 * Send a build notification to Slack.
 *
 * Usage:
 *   notifySlack()                                  // auto-detects status from currentBuild
 *   notifySlack(status: 'SUCCESS', message: 'Deployed to prod')
 */
def call(Map config = [:]) {
    String channel = config.get('channel', '#builds')
    String status = config.get('status', currentBuild.currentResult ?: 'UNKNOWN')
    String message = config.get('message', "${env.JOB_NAME} #${env.BUILD_NUMBER}")

    String emoji = [
        SUCCESS: ':white_check_mark:',
        FAILURE: ':x:',
        UNSTABLE: ':warning:',
        ABORTED: ':no_entry:'
    ].get(status, ':grey_question:')

    String text = "${emoji} ${status} - ${message}\n${env.BUILD_URL}"

    echo "Slack -> ${channel}: ${text}"

    // Real usage: uncomment and configure the Slack plugin.
    // slackSend(channel: channel, color: status == 'SUCCESS' ? 'good' : 'danger', message: text)

    return text
}
