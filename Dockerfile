FROM bachelorthesis/java:latest
MAINTAINER Tony Hesjevik <tony@hesjevik.no>

COPY ./ /srv/http/gainmaster-user-service

WORKDIR /srv/http/gainmaster-user-service

CMD ["./gradlew", "bootRun"]
