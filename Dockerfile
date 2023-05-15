# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory to /app
WORKDIR /app

# Copy the current directory contents into the container at /app
COPY . /app

# Make the Maven wrapper executable
RUN chmod +x mvnw

# Build the Spring Boot application using Maven
RUN ./mvnw clean package

# Expose port 8080 for the Spring Boot application
EXPOSE 8080

# Run the Spring Boot application when the container starts

CMD ["java", "-jar", "target/warehousebackend-0.0.1-SNAPSHOT.jar"]
