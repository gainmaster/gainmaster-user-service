# Gainmaster User Service

[![Build Status](http://jenkins.hesjevik.im/buildStatus/icon?job=gainmaster-user-service)](http://jenkins.hesjevik.im/job/gainmaster-user-service/) [![Docker Hub](https://img.shields.io/badge/docker-ready-blue.svg?style=plastic)][docker_hub_repository]

This repository contains a **Dockerfile**, and a **Vagrantfile** for local development. This repository is a part of an automated build, published to the [Docker Hub][docker_hub_repository].

**Base image:** [bachelorthesis/java:latest][docker_hub_base_image]

[docker_hub_repository]: https://registry.hub.docker.com/u/bachelorthesis/gainmaster-user-service/
[docker_hub_base_image]: https://registry.hub.docker.com/u/bachelorthesis/java/

## API

### Resources

```
GET /users/3
Content-Type: application/hal+json

{
    "_links": {
        "self": {
            "href": "http://domain.com/users/3"
        }
    },
    "name": "Lisa Smith",
    "username": "lsmith",
    "email": "lisa@smith.com"
}
```

```
GET /users
Content-Type: application/hal+json

{
    "_links": {
        "self": {
            "href": "http://domain.com/users{?size,page}",
            "templated": true
        },
        "first": {
            "href": "http://domain.com/users?size=1&page=1",
        },
        "previous": {
            "href": "http://domain.com/users?size=1&page=2",
        },
        "current": {
            "href": "http://domain.com/users?size=1&page=3",
        },
        "next": {
            "href": "http://domain.com/users?size=1&page=4",
        },
        "last": {
            "href": "http://domain.com/users?size=1&page=4",
        }
    },
    "_embedded": {
        "users": [
            {
                "_links": {
                    "self": {
                        "href": "http://domain.com/users/3"
                    }
                },
                "name": "Lisa Smith",
                "username": "lsmith",
                "email": "lisa@smith.com"
            }
        ]
    },
    "numberOfElementsOnPage": 1,
    "PageNumber": 3,
    "PageSize": 1,
    "totalNumberOfElements": 4,
    "totalNumberOfPages": 4
}
```

#### POST /users


## References

These referances have been helpful when creating this repository:

