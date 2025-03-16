# Use a base image with both Maven and JDK 17
FROM maven:3.9.6-eclipse-temurin-17 AS builder

# Set the working directory inside the container
WORKDIR /app

# Copy the Maven build file and resolve dependencies
COPY pom.xml ./
RUN mvn dependency:go-offline -B

# Copy the entire project
COPY . ./

# Build the application
RUN mvn clean package -DskipTests

# Use a smaller JDK image for the final container
FROM eclipse-temurin:17-jdk

# Set the working directory inside the container
WORKDIR /app

# Copy the built JAR file from the builder stage
COPY --from=builder /app/target/kafkaProj-0.0.1-SNAPSHOT.jar app.jar

# Expose the port the application runs on
EXPOSE 8081

# Run the application
CMD ["java", "-jar", "app.jar"]
