FROM gainmaster/java
MAINTAINER Tony Hesjevik <tony@hesjevik.no>

COPY build/libs/gainmaster-user-service-0.1.0.war /src/http/gainmaster-user-service-0.1.0.war

CMD ["java", "-jar", "/src/http/gainmaster-user-service-0.1.0.war"]
