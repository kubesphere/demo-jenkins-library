#!/usr/bin/env groovy

/**
 * Run the project's test suite and publish the JUnit report.
 *
 * Usage:
 *   runTests()
 *   runTests(command: 'go test ./...', report: 'reports/junit.xml')
 */
def call(Map config = [:]) {
    String command = config.get('command', 'make test')
    String report = config.get('report', 'reports/junit.xml')
    boolean allowEmpty = config.get('allowEmpty', true)

    echo "Running tests: ${command}"

    try {
        sh command
    } finally {
        junit testResults: report, allowEmptyResults: allowEmpty
    }
}
