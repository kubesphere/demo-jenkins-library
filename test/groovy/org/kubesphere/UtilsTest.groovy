package org.kubesphere

import spock.lang.Specification
import spock.lang.Unroll

class UtilsTest extends Specification {

    @Unroll
    def 'slugify "#input" -> "#expected"'() {
        expect:
        Utils.slugify(input) == expected

        where:
        input                || expected
        'feature/My Thing'   || 'feature-my-thing'
        'release-1.2'        || 'release-1-2'
        '  --weird--  '      || 'weird'
        null                 || ''
    }

    def 'detects release branches'() {
        expect:
        Utils.isReleaseBranch('release-1.2')
        !Utils.isReleaseBranch('release-1.2.3')
        !Utils.isReleaseBranch('main')
    }
}
