# Use the Gradle image with JDK 17
FROM gradle:7.6.0-jdk17 AS build

# Set the working directory
WORKDIR /app

# Copy the Gradle files
COPY gradle /app/gradle
COPY build.gradle settings.gradle gradle.properties /app/

# Copy the source code
COPY src /app/src

# Build the application
RUN gradle build --no-daemon --stacktrace

# Use a smaller base image for the runtime
FROM eclipse-temurin:17-jdk-alpine

# Set the working directory
WORKDIR /app

# Copy the built JAR file from the previous stage
COPY --from=build /app/build/libs/ftp-app-0.0.1-SNAPSHOT.jar /app/ftp-app.jar

# Expose port 8080
EXPOSE 80

# Run the application
ENTRYPOINT ["java", "-jar", "/app/ftp-app.jar","--server.port=80"]
