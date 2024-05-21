FROM jelastic/maven:3.9.5-openjdk-21
WORKDIR /app

COPY . .

COPY --from=ghcr.io/ufoscout/docker-compose-wait:latest /wait /wait

RUN mvn clean package -DskipTests

CMD /wait && ./mvnw spring-boot:run
