FROM maven:3.9.8-eclipse-temurin-21-jammy
LABEL authors="joao victor"

WORKDIR /app

COPY . .

RUN mvn clean package -DskipTests

WORKDIR /app

COPY --from=builder /app/target/cineticket_pro-0.0.1-SNAPSHOT.jar .

EXPOSE 8080

ENTRYPOINT ["java", "-jar","cineticket_pro-0.0.1-SNAPSHOT.jar"]