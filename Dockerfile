# Step 1: Build stage
FROM gradle:8.10.2-jdk21 AS build

WORKDIR /app

# Copy Gradle wrapper and build files first (for caching)
COPY gradle gradle
COPY gradlew .
COPY build.gradle .
COPY settings.gradle .

# ✅ Give execute permission to gradlew
RUN chmod +x gradlew

# Download dependencies (cached layer)
RUN ./gradlew dependencies --no-daemon || return 0

# Copy the rest of the source code
COPY src ./src

# Build the Spring Boot JAR (skip tests for faster build)
RUN ./gradlew bootJar --no-daemon -x test

# Step 2: Runtime stage
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Copy the JAR file from the build stage
COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
