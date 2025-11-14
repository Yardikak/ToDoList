# 1. Tahap Build (Membuat file .jar)
FROM maven:3.8.5-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests

# 2. Tahap Run (Menjalankan file .jar tadi)
FROM openjdk:17.0.1-jdk-slim
COPY --from=build /target/bahanajar-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]