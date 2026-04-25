# --- Stage 1: Build Stage ---
FROM eclipse-temurin:17-jdk-alpine AS build
WORKDIR /app

# 1. Copy the wrapper and configuration first
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .

# 2. FIX: Grant execution permission to the gradlew script
RUN chmod +x gradlew

# 3. Copy the source code
COPY src src

# 4. Build the application (skipping tests speeds up deployment)
RUN ./gradlew bootJar --no-daemon -x test

# --- Stage 2: Runtime Stage ---
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# 5. Copy the jar from the build stage
# Note: Spring Boot's default output is in build/libs/
COPY --from=build /app/build/libs/*.jar app.jar

# 6. Expose the port Render uses (Render usually defaults to 10000)
EXPOSE 10000

# 7. Run the application with memory limits
ENTRYPOINT ["java", "-Xms256m", "-Xmx512m", "-jar", "app.jar"]