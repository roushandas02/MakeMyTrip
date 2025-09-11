FROM maven:3.9.9-eclipse-temurin-11

# Install dependencies + add Mozilla PPA
RUN apt-get update && apt-get install -y \
    wget \
    bzip2 \
    dbus \
    libxtst6 \
    libdbus-glib-1-2 \
    libnss3 \
    xvfb \
    firefox


WORKDIR /app

COPY pom.xml testng.xml .

RUN mvn dependency:go-offline -B

COPY src ./src
COPY config ./config

RUN mvn clean install -DskipTests

CMD ["xvfb-run", "mvn", "test"]
