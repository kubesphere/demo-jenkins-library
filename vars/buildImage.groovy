#!/usr/bin/env groovy

/**
 * Build a Docker image and push it to a registry.
 *
 * Usage:
 *   buildImage(image: 'myorg/myapp', tag: '1.0.0')
 *   buildImage(image: 'myorg/myapp', tag: '1.0.0', dockerfile: 'docker/Dockerfile', push: false)
 */
def call(Map config = [:]) {
    String image = config.get('image', env.JOB_NAME?.toLowerCase() ?: 'demo-app')
    String tag = config.get('tag', env.BUILD_NUMBER ?: 'latest')
    String dockerfile = config.get('dockerfile', 'Dockerfile')
    String context = config.get('context', '.')
    String registry = config.get('registry', '')
    boolean push = config.get('push', true)

    String fullImage = registry ? "${registry}/${image}:${tag}" : "${image}:${tag}"

    echo "Building image ${fullImage} from ${dockerfile} (context: ${context})"

    sh """
        docker build -f ${dockerfile} -t ${fullImage} ${context}
    """

    if (push) {
        withCredentials([usernamePassword(
            credentialsId: config.get('credentialsId', 'docker-registry-creds'),
            usernameVariable: 'DOCKER_USER',
            passwordVariable: 'DOCKER_PASS'
        )]) {
            sh """
                echo "\$DOCKER_PASS" | docker login ${registry ?: 'docker.io'} -u "\$DOCKER_USER" --password-stdin
                docker push ${fullImage}
            """
        }
    }

    return fullImage
}
