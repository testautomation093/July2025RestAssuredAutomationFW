# Use a base image with Maven and Java 17
FROM maven:3.8.3-openjdk-17

# Set the working directory inside the container
WORKDIR /app

# Copy the Maven project files into the container
COPY pom.xml .
COPY src ./src

# Switch to the directory containing the pom.xml
WORKDIR /app

# Run Maven tests
CMD ["mvn", "test"]
