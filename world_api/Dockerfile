FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
RUN ./mvnw dependency:resolve

COPY src src
RUN ./mvnw package -DskipTests

RUN apt-get update && apt-get install -y wget

EXPOSE 8082
ENV SPRING_PROFILES_ACTIVE=docker
CMD ["java", "-jar", "target/world_api-0.0.1-SNAPSHOT.jar"]
