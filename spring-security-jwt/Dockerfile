# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the JAR file
COPY target/*.jar app.jar

# Expose the port the application runs on
EXPOSE 80

# Run the JAR file
ENTRYPOINT ["java", "-jar", "app.jar"]
