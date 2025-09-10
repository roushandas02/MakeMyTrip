FROM maven:3.9.9-eclipse-temurin-11

# Install dependencies + add Mozilla PPA
RUN apt-get update && \
    apt-get install -y wget gnupg curl unzip software-properties-common && \
    add-apt-repository ppa:mozillateam/ppa -y && \
    apt-get update && \
    apt-get install -y firefox && \
    rm -rf /var/lib/apt/lists/*

WORKDIR /app

COPY pom.xml testng.xml .

RUN mvn dependency:go-offline -B

COPY src ./src
COPY config ./config

RUN mvn clean install -DskipTests

CMD ["mvn", "test"]
