FROM openjdk:17-jdk-slim

ARG JAR_FILE

COPY target/${JAR_FILE} /app/${JAR_FILE}

ENTRYPOINT ["java", "-jar", "/app/${JAR_FILE}"]
