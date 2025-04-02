# Build stage: Use OpenJDK with Maven for the build
FROM maven:3.8.4-openjdk-17-slim AS build

# Set the working directory for the build
WORKDIR /app

# Copy pom.xml and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy source code and build the application
COPY src ./src
RUN mvn clean package -DskipTests

# Runtime stage: Use OpenJDK runtime image for running the application
FROM openjdk:17-jdk-slim

# Set the working directory for runtime
WORKDIR /app

# Copy the built JAR file from the build stage
COPY --from=build /app/target/cache2-0.0.1-SNAPSHOT.jar /app/cache2.jar

# Copy the JMX Exporter JAR and configuration file into the image
# Make sure the path to jmx_prometheus_javaagent-1.2.0.jar and config.yml is correct
# Copy JMX Exporter JAR and config
COPY libs/jmx_prometheus_javaagent-1.2.0.jar /app/jmx_exporter.jar
COPY src/main/resources/config.yml /app/config.yml

# Expose application and JMX exporter ports
EXPOSE 8080 8081

# Run the application with the JMX exporter agent
ENTRYPOINT ["java", "-javaagent:/app/jmx_exporter.jar=8081:/app/config.yml", "-jar", "/app/cache2.jar"]
