FROM openjdk:11-jdk-slim

WORKDIR /app

COPY . .

RUN chmod +x mvnw

RUN ./mvnw clean package

CMD ["java", "-jar", "target/warehouse_backend.jar"]
