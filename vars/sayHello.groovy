#!/usr/bin/env groovy

/**
 * Print a greeting. The simplest possible shared-library step.
 *
 * Usage:
 *   sayHello()
 *   sayHello 'KubeSphere'
 *   sayHello name: 'KubeSphere', loud: true
 */
def call(Map config = [:]) {
    String name = config.get('name', 'World')
    boolean loud = config.get('loud', false)

    String greeting = "Hello, ${name}!"
    if (loud) {
        greeting = greeting.toUpperCase()
    }

    echo greeting
    return greeting
}
