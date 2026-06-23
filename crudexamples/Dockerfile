# === Etapa 1: Compilación (Build) ===
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Copiar el archivo de configuración de dependencias
COPY pom.xml .

# Descargar las dependencias en caché (optimiza futuras compilaciones)
RUN mvn dependency:go-offline -B

# Copiar el código fuente y compilar el proyecto omitiendo los tests para acelerar el proceso
COPY src ./src
RUN mvn clean package -DskipTests

# === Etapa 2: Imagen Final de Ejecución ===
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

# Copiamos el .jar generado en la etapa anterior y lo renombramos a app.jar
COPY --from=build /app/target/*.jar app.jar

# Exponemos el puerto en el que corre tu Tomcat
EXPOSE 8081

# Comando para arrancar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]