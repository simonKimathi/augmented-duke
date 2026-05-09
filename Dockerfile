# =========================
# BUILD STAGE
# =========================
FROM maven:3.9.9-eclipse-temurin-21 AS builder

WORKDIR /build

# Copy pom first for dependency caching
COPY pom.xml .

RUN mvn dependency:go-offline

# Copy source
COPY src ./src

# Build WAR
RUN mvn clean package -DskipTests

# =========================
# RUNTIME STAGE
# =========================
FROM quay.io/wildfly/wildfly:36.0.1.Final-jdk21

# Deploy WAR
COPY --from=builder /build/target/*.war \
    /opt/jboss/wildfly/standalone/deployments/augmented-duke.war

EXPOSE 8080 9990