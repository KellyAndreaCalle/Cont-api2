# Usamos Amazon Corretto 17 Alpine, que es estable y ligera
FROM amazoncorretto:17-alpine

WORKDIR /app

# Copiamos el JAR que Maven acaba de generar en la fase anterior de CodeBuild
# El contexto de build es la carpeta 'api-calendario', así que 'target/' es accesible directamente.
COPY target/*.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]