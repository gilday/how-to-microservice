#!/bin/bash -eu
# env-cred-helper.sh
# Docker credential store that echoes credentials from the environment
# https://docs.docker.com/engine/reference/commandline/login/

echo "{\"Username\": \"$DOCKER_USERNAME\", \"Secret\": \"$DOCKER_PASSWORD\"}"
