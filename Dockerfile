FROM bachelorthesis/java:latest
MAINTAINER Tony Hesjevik <tony@hesjevik.no>

COPY ./ /srv/http/gainmaster-account-service

WORKDIR /srv/http/gainmaster-account-service

CMD ["./gradlew", "bootRun"]
