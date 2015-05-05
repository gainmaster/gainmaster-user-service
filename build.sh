#!/usr/bin/env bash

trap 'exit 1' ERR   # Exit script with error if command fails

if [[ -z $(which docker) ]]; then
    echo "Missing docker client which is required for building, testing and pushing."
    exit 2
fi

declare PROJECT_DIRECTORY=$(cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd)
declare DOCKER_IMAGE_NAME="gainmaster/gainmaster-user-service"

cd $PROJECT_DIRECTORY

function build {
        docker build -t "${DOCKER_IMAGE_NAME}" .
}


function test {
    docker history "${DOCKER_IMAGE_NAME}" 2> /dev/null

    if [ $? -eq 1 ]; then
        echo "Cant test ${DOCKER_IMAGE_NAME}, the image does not exist."
        exit 2
    fi

    docker run -t "${DOCKER_IMAGE_NAME}" ./gradlew test
}


function push {
    docker history "${DOCKER_IMAGE_NAME}" 2> /dev/null

    if [ $? -eq 1 ]; then
        echo "Cant push ${DOCKER_IMAGE_NAME}, the image does not exist."
        exit 2
    fi

    [ -z "$DOCKER_EMAIL" ]    && { echo "Need to set DOCKER_EMAIL";    exit 1; }
    [ -z "$DOCKER_USER" ]     && { echo "Need to set DOCKER_USER";     exit 1; }
    [ -z "$DOCKER_PASSWORD" ] && { echo "Need to set DOCKER_PASSWORD"; exit 1; }

    if [[ $EUID -ne 0 ]]; then
        if [[ -z $(which sudo) ]]; then
            echo "Missing sudo client which is required for pushing when not root"
            exit 2
        fi

        sudo docker login -e $DOCKER_EMAIL -u $DOCKER_USER -p $DOCKER_PASSWORD
        sudo docker push "${DOCKER_IMAGE_NAME}"

    else
        docker login -e $DOCKER_EMAIL -u $DOCKER_USER -p $DOCKER_PASSWORD
        docker push "${DOCKER_IMAGE_NAME}"
    fi

}


#
# Handle input
#

actions=("$@")

if [ ${#actions[@]} -eq 0 ]; then
    actions=(build test push)
fi

for action in "${actions[@]}"; do
    case "$action" in
        build)
            echo "Executing build action"
            build
            ;;

        test)
            echo "Executing test action"
            test
            ;;

        push)
            echo "Executing push action"
            push
            ;;
    esac
done
