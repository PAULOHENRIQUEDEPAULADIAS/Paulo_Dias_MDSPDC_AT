FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
RUN ./mvnw dependency:resolve

COPY src src
RUN ./mvnw package -DskipTests

EXPOSE 8082

CMD ["java", "-jar", "target/world_api-0.0.1-SNAPSHOT.jar"]
