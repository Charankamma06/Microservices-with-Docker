FROM eclipse-temurin:17
LABEL authors="vishakavinod"
ARG JAR_FILE=target/*.jar
COPY ./target/DockerApp2-0.0.1-SNAPSHOT.jar DockerApp2.jar
EXPOSE 8000
CMD ["java","-jar","/DockerApp2.jar"]