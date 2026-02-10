FROM amazoncorretto:17-alpine-jdk

COPY target/back-0.0.1-SNAPSHOT.jar /v1.jar

ENTRYPOINT ["java", "-jar", "/v1.jar"]