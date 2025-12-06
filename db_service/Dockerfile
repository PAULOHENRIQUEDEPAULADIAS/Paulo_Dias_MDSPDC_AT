# Etapa 1: build
FROM eclipse-temurin:21-jdk AS builder
WORKDIR /app
COPY . .
RUN ./mvnw clean package -DskipTests

# Etapa 2: imagem final
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=builder /app/target/db_service-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8083
ENV SPRING_PROFILES_ACTIVE=docker

ENTRYPOINT ["java", "-jar", "app.jar"]
