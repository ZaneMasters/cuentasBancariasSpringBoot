
FROM amazoncorretto:21

WORKDIR /app

COPY target/tickets-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]