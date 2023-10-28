FROM openjdk:17-jdk

WORKDIR /app

COPY target/electroshop.jar .
EXPOSE 8080
ENTRYPOINT ["java","-jar","electroshop.jar"]