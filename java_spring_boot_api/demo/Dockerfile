#FROM adoptopenjdk/openjdk18:alpine-jre
FROM openjdk:18
ADD target/spring-postgres-docker-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]