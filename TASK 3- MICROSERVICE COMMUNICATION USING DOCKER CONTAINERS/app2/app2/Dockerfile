# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the jar file into the container
COPY target/app2-0.0.1-SNAPSHOT.jar app2.jar

# Expose the correct port (match with application.properties)
EXPOSE 8088

# Run the jar file
ENTRYPOINT ["java", "-jar", "/app/app2.jar"]
