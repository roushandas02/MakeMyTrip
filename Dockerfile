FROM maven:3.9.9-eclipse-temurin-11

# Install Chrome only
RUN apt-get update && \
    apt-get install -y wget gnupg curl unzip && \
    wget -q -O - https://dl.google.com/linux/linux_signing_key.pub | apt-key add - && \
    sh -c 'echo "deb [arch=amd64] http://dl.google.com/linux/chrome/deb/ stable main" >> /etc/apt/sources.list.d/google-chrome.list' && \
    apt-get update && \
    apt-get install -y google-chrome-stable && \
    rm -rf /var/lib/apt/lists/*

WORKDIR /app

COPY pom.xml testng.xml .

RUN mvn dependency:go-offline -B

COPY src ./src
COPY config ./config

RUN mvn clean install -DskipTests

CMD ["mvn", "test"]
