FROM eclipse-temurin:21-jdk AS builder
WORKDIR /workspace
COPY . .
ARG MODULE
RUN chmod +x gradlew \
    && ./gradlew :${MODULE}:bootJar --no-daemon

FROM eclipse-temurin:21-jre
WORKDIR /app
RUN groupadd --system spring \
    && useradd --system --gid spring spring
ARG MODULE
COPY --from=builder --chown=spring:spring \
    /workspace/${MODULE}/build/libs/*.jar app.jar
USER spring
ENTRYPOINT ["java", "-jar", "app.jar"]
