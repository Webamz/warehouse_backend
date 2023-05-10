##
## Build stage
##
#FROM maven:3.8.2-jdk-11 AS build
#WORKDIR /app
#COPY . /app
#RUN mvn -f /app/pom.xml clean package -DskipTests
#
##
## Package stage
##
#FROM openjdk:11-jdk-slim
#COPY --from=build /app/target/demo-0.0.1-SNAPSHOT.jar /demo.jar
#EXPOSE 8080/tcp
#ENTRYPOINT ["java","-jar","/demo.jar"]



## Build stage
#FROM maven:3.8.2-jdk-11 AS build
#WORKDIR /app
#COPY pom.xml .
#RUN mvn -B -f pom.xml -s /usr/share/maven/ref/settings-docker.xml dependency:resolve
#COPY src ./src
#RUN mvn -B -s /usr/share/maven/ref/settings-docker.xml package -DskipTests
#
## Package stage
#FROM openjdk:11-jdk-slim
#COPY --from=build /app/target/demo-0.0.1-SNAPSHOT.jar /demo.jar
#EXPOSE 8080
#CMD ["java","-Dserver.port=8080","-jar","/demo.jar"]


# Build stage
FROM maven:3.8.2-jdk-11 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn -B -f pom.xml -s /usr/share/maven/ref/settings-docker.xml dependency:resolve
COPY src ./src
RUN mvn -f /app/pom.xml clean package -DskipTests

# Package stage
FROM openjdk:11-jdk-slim
COPY --from=build /app/target/demo-0.0.1-SNAPSHOT.jar /demo.jar
EXPOSE 8080
CMD ["java","-jar","/demo.jar"]


