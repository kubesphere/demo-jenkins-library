#!/usr/bin/env groovy

/**
 * Wrap a block with common build environment setup: a log header and a
 * guaranteed footer. Demonstrates a block-taking step.
 *
 * Usage:
 *   withBuildEnv {
 *       sh 'make build'
 *   }
 */
def call(Map config = [:], Closure body) {
    String goVersion = config.get('goVersion', '1.22')
    String nodeVersion = config.get('nodeVersion', '20')

    echo "=== Build started: ${env.JOB_NAME} #${env.BUILD_NUMBER} ==="
    echo "Go ${goVersion} / Node ${nodeVersion}"

    try {
        body()
    } finally {
        echo "=== Build finished: ${currentBuild.currentResult} ==="
    }
}
