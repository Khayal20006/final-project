# Step 1: Build stage
FROM gradle:8.10.2-jdk21 AS build

# Set working directory
WORKDIR /app

# Copy Gradle wrapper and build files first (for caching dependencies)
COPY gradle gradle
COPY gradlew .
COPY build.gradle .
COPY settings.gradle .

# Download dependencies (this layer will be cached unless build files change)
RUN ./gradlew dependencies --no-daemon

# Copy the rest of your project
COPY src ./src

# Build the Spring Boot JAR (skip tests for faster build)
RUN ./gradlew bootJar --no-daemon -x test

# Step 2: Runtime stage (lightweight image)
FROM eclipse-temurin:21-jre-alpine

# Set working directory
WORKDIR /app

# Copy the built JAR file from the build stage
COPY --from=build /app/build/libs/*.jar app.jar

# Expose the port that Spring Boot runs on (default 8080)
EXPOSE 8080

# Start the application
ENTRYPOINT ["java", "-jar", "app.jar"]
