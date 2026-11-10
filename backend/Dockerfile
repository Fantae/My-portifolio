# Stage 1: Build the application
FROM --platform=linux/arm64 eclipse-temurin:21-jdk-alpine AS build
WORKDIR /app
COPY .mvn/ .mvn
COPY mvnw pom.xml ./

# Fix for Windows line endings and file permissions
RUN sed -i 's/\r$//' mvnw && chmod +x mvnw

# Download dependencies
RUN ./mvnw dependency:go-offline

# Copy source code and build the jar
COPY src ./src
RUN ./mvnw clean package -DskipTests

# Stage 2: Run the application
FROM --platform=linux/arm64 eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Expose the port (cloud platforms override this with $PORT)
EXPOSE 8081

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]