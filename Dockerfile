FROM openjdk:8-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ${JAR_FLIE} app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
