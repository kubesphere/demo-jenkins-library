#!/usr/bin/env groovy

/**
 * Deploy a manifest to Kubernetes and wait for the rollout to finish.
 *
 * Usage:
 *   deployK8s(namespace: 'demo', manifest: 'deploy/app.yaml')
 *   deployK8s(namespace: 'demo', manifest: 'deploy/app.yaml', deployment: 'myapp', timeout: '5m')
 */
def call(Map config = [:]) {
    String namespace = config.get('namespace', 'default')
    String manifest = config.get('manifest', 'deploy/app.yaml')
    String deployment = config.get('deployment', '')
    String timeout = config.get('timeout', '2m')
    String kubeconfig = config.get('kubeconfig', 'kubeconfig-prod')

    withCredentials([file(credentialsId: kubeconfig, variable: 'KUBECONFIG')]) {
        echo "Applying ${manifest} to namespace ${namespace}"
        sh "kubectl apply -n ${namespace} -f ${manifest}"

        if (deployment) {
            echo "Waiting for rollout of ${deployment} (timeout ${timeout})"
            sh "kubectl rollout status deployment/${deployment} -n ${namespace} --timeout=${timeout}"
        }
    }
}
