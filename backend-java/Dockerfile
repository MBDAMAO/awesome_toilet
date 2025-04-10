FROM openjdk:21-jdk

# 复制构建文件和源代码
COPY . /app
WORKDIR /app

RUN chmod +x mvnw && \
    ./mvnw -s .mvn/settings.xml clean package

# 暴露端口
EXPOSE 8080

CMD ["java", "-Xmx1024m", "-jar", "/app/target/backend-1.0.0.jar"]