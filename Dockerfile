# syntax = docker/dockerfile:experimental

FROM eclipse-temurin:20-jdk-jammy AS builder
WORKDIR /app
COPY .mvn/ .mvn
COPY mvnw pom.xml settings.json ./
RUN ./mvnw dependency:resolve
COPY src ./src
RUN ./mvnw package
 # Stage 2: Final stage
FROM eclipse-temurin:20-jdk-alpine
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
EXPOSE 8443
CMD ["java", "-jar", "app.jar"]