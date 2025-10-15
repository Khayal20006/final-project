# Step 1: Build stage (using Maven)
FROM maven:3.9.9-eclipse-temurin-21 AS build

# Set working directory inside the container
WORKDIR /app

# Copy the Maven descriptor first (to cache dependencies)
COPY pom.xml .

# Download dependencies (this layer is cached if pom.xml doesn't change)
RUN mvn dependency:go-offline -B

# Copy the rest of the project
COPY src ./src

# Package the application (skip tests to speed up build)
RUN mvn clean package -DskipTests

# Step 2: Runtime stage (using lightweight JRE)
FROM eclipse-temurin:21-jre-alpine

# Set working directory for the app
WORKDIR /app

# Copy the jar file from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose the port your Spring Boot app runs on (usually 8080)
EXPOSE 8080

# Default command to run the Spring Boot app
ENTRYPOINT ["java", "-jar", "app.jar"]
