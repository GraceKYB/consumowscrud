# Usamos una imagen oficial de OpenJDK
FROM openjdk:22-ea-jdk-slim

# Creamos un directorio para la app dentro del contenedor
WORKDIR /app

# Copiamos el jar de la app al contenedor (esto lo generará Gradle)
COPY build/libs/consumowscrud-0.0.1-SNAPSHOT.jar app.jar

# Puerto que expone tu aplicación (por defecto 8080)
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
