# ============================================================================
# Etapa 1: compilación con Maven + OpenJDK 17 (Eclipse Temurin)
# ============================================================================
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

# Primero solo el pom: aprovecha la caché de capas de Docker para las dependencias
COPY pom.xml .
RUN mvn -B -q dependency:go-offline

COPY src ./src
RUN mvn -B -q clean package -DskipTests

# ============================================================================
# Etapa 2: imagen final liviana, solo con el JRE y el .jar
# ============================================================================
FROM eclipse-temurin:17-jre
WORKDIR /app

# Ejecutar sin privilegios de root
RUN addgroup --system spring && adduser --system --ingroup spring spring

COPY --from=build /app/target/*.jar app.jar
USER spring

# Puerto por defecto; en Render/Heroku la plataforma inyecta PORT en tiempo de ejecución
ENV PORT=8080
ENV JAVA_OPTS="-XX:MaxRAMPercentage=75"
EXPOSE 8080

# Puerto dinámico: se toma de la variable PORT al arrancar
ENTRYPOINT ["sh", "-c", "exec java $JAVA_OPTS -Dserver.port=${PORT} -jar app.jar"]
