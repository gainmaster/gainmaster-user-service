FROM gainmaster/gradle
MAINTAINER Tony Hesjevik <tony@hesjevik.no>

COPY ./ /srv/http/gainmaster-user-service

WORKDIR /srv/http/gainmaster-user-service

RUN ["gradle", "build"]

CMD ["java", "-jar", "./build/libs/gainmaster-user-service-0.1.0.war"]
