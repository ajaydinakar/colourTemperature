FROM openjdk:8-jdk-alpine
MAINTAINER ajaydinakar.com
COPY build/libs/ColourTemperature-0.0.1-SNAPSHOT.jar ajaysapp.jar
ENTRYPOINT ["java","-jar","/app.jar"]