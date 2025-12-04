FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
RUN ./mvnw dependency:resolve

COPY src src
RUN ./mvnw package -DskipTests

EXPOSE 8081

CMD ["java", "-jar", "target/hello_api-0.0.1-SNAPSHOT.jar"]
