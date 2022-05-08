FROM openjdk:8-jdk-alpine
MAINTAINER atik
COPY target/tweet-app-0.0.1-SNAPSHOT.jar tweet-app-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/tweet-app-0.0.1-SNAPSHOT.jar"]