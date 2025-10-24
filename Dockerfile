# --- Stage 1: build with Maven on JDK 25 ---
FROM eclipse-temurin:25-jdk AS build
WORKDIR /app

# instalar maven (y curl/zip si hiciera falta en otras variantes)
RUN apt-get update && apt-get install -y maven && rm -rf /var/lib/apt/lists/*

# copiar fuentes
COPY pom.xml .
COPY src ./src

# construir (skip tests para acelerar; quita -DskipTests si quieres ejecutar tests)
RUN mvn -B clean package -DskipTests

# --- Stage 2: runtime (ligero, con mismo JDK para compatibilidad) ---
FROM eclipse-temurin:25-jdk
WORKDIR /app

# copiar el JAR del stage build (usa wildcard por si cambia el nombre)
COPY --from=build /app/target/*.jar app.jar

# exposer puerto
EXPOSE 8080

# asegurarse de que Spring Boot acepte conexiones externas
# puedes fijar server.address en application.properties/yml a 0.0.0.0 o pasar aquí:
ENV JAVA_OPTS=""

ENTRYPOINT ["sh","-c","java $JAVA_OPTS -jar app.jar"]
