package org.kubesphere

/**
 * Semantic version helper used by the demo pipelines.
 *
 *   def v = new org.kubesphere.Version('1.2.3')
 *   v.major          // 1
 *   v.bump('minor')  // 1.3.0
 */
class Version implements Serializable {

    int major
    int minor
    int patch

    Version(String version) {
        def parts = version.tokenize('.')
        if (parts.size() < 3) {
            throw new IllegalArgumentException("Not a semantic version: ${version}")
        }
        this.major = parts[0] as int
        this.minor = parts[1] as int
        this.patch = parts[2] as int
    }

    Version(int major, int minor, int patch) {
        this.major = major
        this.minor = minor
        this.patch = patch
    }

    /** Return a new Version with the given part incremented. */
    Version bump(String part) {
        switch (part) {
            case 'major':
                return new Version(major + 1, 0, 0)
            case 'minor':
                return new Version(major, minor + 1, 0)
            case 'patch':
                return new Version(major, minor, patch + 1)
            default:
                throw new IllegalArgumentException("Unknown part: ${part}")
        }
    }

    @Override
    String toString() {
        return "${major}.${minor}.${patch}"
    }
}
