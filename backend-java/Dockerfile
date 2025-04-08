# 使用 Maven 3.8.5 和 OpenJDK 21 构建阶段
FROM maven:3.8.5-openjdk-21 AS builder
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# 使用 OpenJDK 21 运行阶段
FROM openjdk:21
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]