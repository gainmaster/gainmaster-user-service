# Gainmaster User Service

[![Build Status](http://ci.hesjevik.im/buildStatus/icon?job=gainmaster-user-service)](http://ci.hesjevik.im/job/gainmaster-user-service/) [![Docker Hub](https://img.shields.io/badge/docker-ready-blue.svg?style=plastic)][docker_hub_repository]

This repository contains a **Dockerfile**, and a **Vagrantfile** for local development. This repository is a part of an automated build, published to the [Docker Hub][docker_hub_repository].

**Base image:** [gainmaster/gradle][docker_hub_base_image]

[docker_hub_repository]: https://registry.hub.docker.com/u/gainmaster/gainmaster-user-service/
[docker_hub_base_image]: https://registry.hub.docker.com/u/gainmaster/gradle/

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

These references have been helpful when creating this repository:

https://github.com/dsyer/sparklr-boot
http://docs.spring.io/spring-security/site/docs/3.0.x/reference/el-access.html
http://projects.spring.io/spring-security-oauth/docs/oauth2.html
https://github.com/jirutka/spring-rest-exception-handler

http://docs.spring.io/spring-amqp/docs/1.4.4.RELEASE/reference/html/
