FROM eclipse-temurin:17-jdk-alpine

WORKDIR /Abaya-0.0.1-SNAPSHOT

COPY build/libs/*.jar Abaya-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java", "-Xms256m", "-Xmx512m", "-jar", "Abaya-0.0.1-SNAPSHOT.jar"]