#!/usr/bin/env bash

set -x              # Print command traces before executing command
trap 'exit 1' ERR   # Exit script with error if command fails

cd $(dirname "${BASH_SOURCE[0]}")

if [[ -z $(which docker) ]]; then
    echo "Missing docker client which is required for building, testing and pushing."
    exit 2
fi


declare IMAGE_NAME="gainmaster/gainmaster-user-service"


function build {
        docker build -t "${IMAGE_NAME}" .
}


function test {
    docker history "${IMAGE_NAME}" 2> /dev/null

    if [ $? -eq 1 ]; then
        echo "Cant test ${IMAGE_NAME}, the image does not exist."
        exit 2
    fi

    docker run -t "${IMAGE_NAME}" ./gradlew test
}


function push {
    docker history "${IMAGE_NAME}" 2> /dev/null

    if [ $? -eq 1 ]; then
        echo "Cant push ${IMAGE_NAME}, the image does not exist."
        exit 2
    fi

    [ -z "$DOCKER_EMAIL" ]    && { echo "Need to set DOCKER_EMAIL";    exit 1; }
    [ -z "$DOCKER_USER" ]     && { echo "Need to set DOCKER_USER";     exit 1; }
    [ -z "$DOCKER_PASSWORD" ] && { echo "Need to set DOCKER_PASSWORD"; exit 1; }

    if [[ $EUID -ne 0 ]]; then
        sudo docker login -e $DOCKER_EMAIL -u $DOCKER_USER -p $DOCKER_PASSWORD
        sudo docker push "${IMAGE_NAME}"

    else
        docker login -e $DOCKER_EMAIL -u $DOCKER_USER -p $DOCKER_PASSWORD
        docker push "${IMAGE_NAME}"
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
            build
            ;;

        test)
            test
            ;;

        push)
            push
            ;;
    esac
done
