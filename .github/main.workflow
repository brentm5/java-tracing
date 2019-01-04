workflow "New workflow" {
  on = "push"
  resolves = ["maven:3.6-jdk-8"]
}

action "maven:3.6-jdk-8" {
  uses = "docker://maven:3.6-jdk-8"
  runs = "mvn install"
}
