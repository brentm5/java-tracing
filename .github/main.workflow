workflow "Release Workflow" {
  on = "push"
  resolves = ["docker://maven:3.6-jdk-8"]
}

action "Maven Test" {
  uses = "docker://maven:3.6-jdk-8"
  runs = "mvn test"
}

action "Filter on master for release" {
  uses = "actions/bin/filter@b2bea07"
  needs = ["Maven Test"]
  args = "branch master"
}

action "docker://maven:3.6-jdk-8" {
  uses = "docker://maven:3.6-jdk-8"
  needs = ["Filter on master for release"]
  runs = "mvn package"
}
