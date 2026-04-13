FROM eclipse-temurin:21-jre-alpine

LABEL authors="daniil"

WORKDIR /app

COPY target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]