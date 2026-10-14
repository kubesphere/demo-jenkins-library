package org.kubesphere

import spock.lang.Specification

class VersionTest extends Specification {

    def 'parses a semantic version string'() {
        when:
        def v = new Version('1.2.3')

        then:
        v.major == 1
        v.minor == 2
        v.patch == 3
        v.toString() == '1.2.3'
    }

    def 'bumps the requested part'() {
        given:
        def v = new Version('1.2.3')

        expect:
        v.bump('patch').toString() == '1.2.4'
        v.bump('minor').toString() == '1.3.0'
        v.bump('major').toString() == '2.0.0'
    }

    def 'rejects malformed versions'() {
        when:
        new Version('1.2')

        then:
        thrown(IllegalArgumentException)
    }
}
