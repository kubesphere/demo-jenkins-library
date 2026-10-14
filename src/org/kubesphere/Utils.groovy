package org.kubesphere

/**
 * Small stateless helpers shared across pipelines.
 */
class Utils implements Serializable {

    /** Slugify a string for use in image names / branch names. */
    static String slugify(String input) {
        if (!input) {
            return ''
        }
        return input.toLowerCase()
            .replaceAll(/[^a-z0-9]+/, '-')
            .replaceAll(/^-+|-+$/, '')
    }

    /** Derive a docker-safe tag from a git branch name. */
    static String branchToTag(String branch) {
        return slugify(branch ?: 'unknown')
    }

    /** True when the branch is a release branch such as release-1.2. */
    static boolean isReleaseBranch(String branch) {
        return branch ==~ /release-\d+\.\d+/
    }
}
