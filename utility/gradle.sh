#!/usr/bin/env bash

WORKING_DIRECTORY=$(pwd)
PROJECT_DIRECTORY=$(cd "$( dirname "${BASH_SOURCE[0]}" )/../" && pwd)

if [[ ${WORKING_DIRECTORY} != ${PROJECT_DIRECTORY}* ]]; then
    echo "Gradle must be executed from within the gainmaster-user-service project folder"
    exit 1
fi

DOCKER_WORKING_DIRECTORY=${WORKING_DIRECTORY#"$PROJECT_DIRECTORY"}

if [ "$TERM" == "dumb" ]; then
    DOCKER_RUN_OPTIONS=""
else
    DOCKER_RUN_OPTIONS="-it"
fi

if [ $(docker ps -a | grep gainmaster-user-service-data-container | wc -l) -ne 1 ] ; then
    docker run $DOCKER_RUN_OPTIONS \
        --name gainmaster-user-service-data-container \
        --volume /root \
        gainmaster/gradle echo "Data container started"
fi

docker run $DOCKER_RUN_OPTIONS --rm \
    --volumes-from "gainmaster-user-service-data-container" \
    --volume ${PROJECT_DIRECTORY}:/project \
    --workdir "/project${DOCKER_WORKING_DIRECTORY}/" \
    --env GRADLE_USER_HOME=/root/.gradle/ \
    --entrypoint gradle \
    gainmaster/gradle $@
