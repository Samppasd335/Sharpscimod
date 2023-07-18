FROM eclipse-temurin:20-jdk-jammy AS base
WORKDIR /app
COPY .mvn/ .mvn
COPY mvnw pom.xml settings.json ./
RUN ./mvnw dependency:resolve
COPY src ./src

FROM base AS test
RUN ["./mvnw", "test"]

FROM base AS development
CMD ["./mvnw", "spring-boot:run"]

FROM base AS build
RUN ./mvnw package

FROM eclipse-temurin:20-jdk-alpine AS production
EXPOSE 8443
COPY --from=build /app/target/datagraph-1.0.jar datagraph-1.0.jar
COPY --from=base /app/settings.json settings.json
ENTRYPOINT ["java", "-jar", "datagraph-1.0.jar"]