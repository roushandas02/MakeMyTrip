FROM maven:3.9.9-eclipse-temurin-11

# Install xvfb and other dependencies required for a headless browser environment
RUN apt-get update && apt-get install -y \
    wget \
    bzip2 \
    dbus \
    libxtst6 \
    libnss3 \
    xvfb \
    apt-utils

# Set up a working directory inside the container
WORKDIR /app

# Copy the Maven project files first to leverage Docker's layer caching.
COPY pom.xml .
COPY testng.xml .

# Use the 'go-offline' command to download all dependencies
RUN mvn dependency:go-offline -B

# Copy the rest of the project source code
COPY src ./src
COPY config ./config

# Build the project without running tests
RUN mvn clean install -DskipTests

# Run tests in headless mode using xvfb-run.
CMD ["xvfb-run", "-a", "mvn", "test", "-B", "-e", "-X"]