# --- Stage 1: Build Stage ---
FROM eclipse-temurin:17-jdk-alpine AS build
WORKDIR /app

# Copy gradle executable and configuration files
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src

# Build the application - this creates the .jar inside the container
RUN ./gradlew bootJar --no-daemon

# --- Stage 2: Runtime Stage ---
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Copy the jar from the build stage to the runtime stage
COPY --from=build /app/build/libs/*.jar app.jar

# Optimize for memory-constrained environments like Render's free tier
ENTRYPOINT ["java", "-Xms256m", "-Xmx512m", "-jar", "app.jar"]