# demo-jenkins-library

A demo [Jenkins Shared Library](https://www.jenkins.io/doc/book/pipeline/shared-libraries/)
showing the three building blocks of a library plus a set of example
`Jenkinsfile`s that consume it.

## Layout

```
vars/                       Global steps, callable by name from any pipeline
  sayHello.groovy             simplest step, returns a value
  buildImage.groovy           docker build + push with credentials
  runTests.groovy             run tests, publish JUnit report
  deployK8s.groovy            kubectl apply + rollout status
  notifySlack.groovy          build notification
  withBuildEnv.groovy         block-taking step (wraps a closure)

src/org/kubesphere/         Classes, imported with `import`
  Version.groovy              semantic version helper
  Utils.groovy                slugify / branch helpers

jenkinsfiles/               Example pipelines
  Jenkinsfile.hello           minimal, one step
  Jenkinsfile.ci              checkout -> test -> build image -> notify
  Jenkinsfile.release         version bump -> build -> approve -> deploy
  Jenkinsfile.multibranch     branch-conditional stages
  Jenkinsfile.scripted        scripted (non-declarative) style

test/groovy/                Unit tests for the src/ classes (Spock)
```

## Using the library

In Jenkins: **Manage Jenkins → System → Global Pipeline Libraries**, add a
library named `demo-jenkins-library` pointing at this repo. Then in a
`Jenkinsfile`:

```groovy
@Library('demo-jenkins-library') _

sayHello name: 'KubeSphere', loud: true
```

`vars/` steps are available automatically; `src/` classes need an `import`:

```groovy
import org.kubesphere.Version

def next = new Version('1.2.3').bump('minor')   // 1.3.0
```

## Running the unit tests

The `src/` classes are plain Groovy and testable without a Jenkins instance:

```sh
# with the Groovy CLI and Spock on the classpath
groovy -cp "src:test/groovy:$(spock-classpath)" org.junit.platform.console.ConsoleLauncher \
  --scan-classpath test/groovy
```

## Notes

- The example pipelines reference credentials (`docker-registry-creds`,
  `kubeconfig-prod`) and a Slack channel that you must configure in your
  Jenkins instance before they will run end to end.
- `notifySlack` logs by default; uncomment the `slackSend` line to enable the
  real Slack plugin integration.
