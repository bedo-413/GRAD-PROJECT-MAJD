# ---------- Stage 1: Build ----------
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# Copy pom.xml and download dependencies first (cache-friendly)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Now copy the rest of the source code
COPY src ./src

# Build the JAR
RUN mvn clean package -DskipTests

# ---------- Stage 2: Run ----------
FROM openjdk:17
WORKDIR /app

# Copy JAR from previous stage
COPY --from=build /app/target/*.jar app.jar

# Run the JAR
ENTRYPOINT ["java", "-jar", "app.jar"]
