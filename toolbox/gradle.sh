#!/usr/bin/env bash

EXEC_DIR=$(pwd)
PROJ_DIR=$(cd "$( dirname "${BASH_SOURCE[0]}" )/../" && pwd)

if [[ ${EXEC_DIR} != ${PROJ_DIR}* ]]; then
    echo "Gradle must be executed from within gainmaster-user-service project folder."
    exit 1
fi

WORK_DIR=${EXEC_DIR#"$PROJ_DIR"}

docker run -it --rm \
  -v /projects/gainmaster-user-service:/project \
  -w="/project${WORK_DIR}/" \
  -e GRADLE_USER_HOME=/project/.gradle/ \
  --entrypoint gradle \
  bachelorthesis/gradle $@
