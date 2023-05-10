#
# Build stage
#
FROM maven:3.8.2-jdk-11 AS build
WORKDIR /app
COPY . /app
RUN mvn -f /app/pom.xml clean package -DskipTests

#
# Package stage
#
FROM openjdk:11-jdk-slim
COPY --from=build /app/target/warehousebackend-0.0.1-SNAPSHOT.jar /warehousebackend.jar
EXPOSE 8080/tcp
ENTRYPOINT ["java","-jar","/warehousebackend.jar"]

#
#FROM maven:3.8.2-openjdk-17 AS build
#WORKDIR /app
#COPY . /app
#
#RUN mvn -f /app/pom.xml clean package -DskipTests
#
##
## Package stage
##
#FROM openjdk:17-jdk-slim
#COPY --from=build /app/target/demo-0.0.1-SNAPSHOT.jar /demo.jar
#EXPOSE 8080/tcp
#ENTRYPOINT ["java","-jar","/demo.jar"]





