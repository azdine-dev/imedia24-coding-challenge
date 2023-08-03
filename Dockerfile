# Use the official OpenJDK 8 as the base image (since you specified Java 8 compatibility)
FROM adoptopenjdk/openjdk8:alpine-jre

# Set the working directory inside the container
WORKDIR /app

# Copy the compiled JAR file into the container
COPY build/libs/shop-0.0.1-SNAPSHOT.jar shop.jar

# Run the Spring Boot application when the container starts
CMD ["java", "-jar", "shop.jar"]