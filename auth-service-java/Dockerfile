FROM openjdk:21-jdk-slim
RUN apt-get update && apt-get install -y curl
WORKDIR /app
COPY target/auth-service-java-0.0.1-SNAPSHOT.jar auth-service-java.jar
ENTRYPOINT ["java", "-jar", "auth-service-java.jar"]