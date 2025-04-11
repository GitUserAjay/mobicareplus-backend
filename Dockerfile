# Use OpenJDK as base image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file from the target folder into the container
COPY target/*.jar app.jar

# Expose the port your app runs on (8080 by default)
EXPOSE 8081

# Run the JAR file when the container starts
ENTRYPOINT ["java", "-jar", "app.jar"]
