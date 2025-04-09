FROM openjdk:21-jdk

COPY . /app

WORKDIR /app

RUN chmod +x mvnw

RUN ./mvnw -s .mvn/settings.xml package

EXPOSE 8081

CMD ["java", "-Xmx1024m", "-jar", "/app/MainApplication/target/MainApplication-1.0.0.jar"]