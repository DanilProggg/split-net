# 1. Build stage
FROM eclipse-temurin:17-jdk AS build
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

RUN apt-get update && \
    apt-get install -y wireguard-tools && \
    rm -rf /var/lib/apt/lists/*

# Copy .jar file from target
COPY --from=build /app/target/*.jar app.jar

# Args for build
ARG DB_HOST
ARG DB_PORT
ARG DB_NAME
ARG DB_USER
ARG DB_PASS
ARG APP_PORT
ARG WG_NET


# Set .env
ENV DB_HOST=${DB_HOST}
ENV DB_PORT=${DB_PORT}
ENV DB_NAME=${DB_NAME}
ENV DB_USER=${DB_USER}
ENV DB_PASS=${DB_PASS}
ENV APP_PORT=${APP_PORT}
ENV WG_NET=${WG_NET}

EXPOSE ${APP_PORT}

ENTRYPOINT ["java", "-jar", "app.jar"]
