# Use OpenJDK as the base image
FROM openjdk:17-jdk-slim as build

# Set the working directory
WORKDIR /app

# Copy the jar file from target directory to the container
COPY target/K8Student-0.0.1-SNAPSHOT.jar studentapp.jar

# Expose the application port (default is 8080)
EXPOSE 8080

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "/app/studentapp.jar"]
