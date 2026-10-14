#!/usr/bin/env groovy

/**
 * Wrap a block with common build environment setup: checkout, tool versions,
 * and a timestamped log header. Demonstrates a block-taking step.
 *
 * Usage:
 *   withBuildEnv {
 *       sh 'make build'
 *   }
 */
def call(Map config = [:], Closure body) {
    String goVersion = config.get('goVersion', '1.22')
    String nodeVersion = config.get('nodeVersion', '20')

    timestamps {
        wrap([$class: 'BuildUser']) {
            echo "=== Build started: ${env.JOB_NAME} #${env.BUILD_NUMBER} ==="
            echo "Go ${goVersion} / Node ${nodeVersion}"

            try {
                body()
            } finally {
                echo "=== Build finished: ${currentBuild.currentResult} ==="
            }
        }
    }
}
