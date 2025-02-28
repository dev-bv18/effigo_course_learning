# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the jar file into the container
COPY target/app1-0.0.1-SNAPSHOT.jar app1.jar

# Expose the correct port (as per your properties file)
EXPOSE 8085

# Run the jar file
ENTRYPOINT ["java", "-jar", "/app/app1.jar"]
