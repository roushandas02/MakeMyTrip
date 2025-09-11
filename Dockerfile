FROM maven:3.9.9-eclipse-temurin-11

# Set up a working directory inside the container
WORKDIR /app

# Copy the Maven project files first to leverage Docker's layer caching.
# This ensures that dependencies are only downloaded when pom.xml changes.
COPY pom.xml .
COPY testng.xml .

# Use the 'go-offline' command to download all dependencies
# This is much faster than running a full 'clean install'
RUN mvn dependency:go-offline -B

# Copy the rest of the project source code
COPY src ./src
COPY config ./config

# Build the project without running tests
RUN mvn clean install -DskipTests

# Run tests in headless mode using xvfb-run.
# WebDriverManager will automatically download the necessary browser drivers.
CMD ["xvfb-run", "-a", "mvn", "test", "-B", "-e", "-X"]