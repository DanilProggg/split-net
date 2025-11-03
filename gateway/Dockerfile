# 1. Build stage
FROM maven:3.9.6-amazoncorretto-21 as build
WORKDIR /app

# Копируем pom.xml и качаем зависимости
COPY pom.xml .
RUN mvn dependency:go-offline

# Копируем исходники
COPY src ./src

# Собираем jar без тестов
RUN mvn clean package -DskipTests

# -----------------------
# 2. Runtime stage
FROM openjdk:17-jdk-slim
WORKDIR /app

# Install iproute2 and wg-tools
RUN apt-get update && \
    apt-get install -y iproute2 wireguard-tools && \
    rm -rf /var/lib/apt/lists/*

# Copy .jar file from target
COPY --from=build /app/target/*.jar app.jar

# Args for build
ARG WG_HOST
ARG API
ARG TOKEN


# Set .env
ENV WG_HOST=${WG_HOST}
ENV API=${API}
ENV TOKEN=${TOKEN}

EXPOSE ${WG_PORT}

ENTRYPOINT ["java", "-jar", "app.jar"]
