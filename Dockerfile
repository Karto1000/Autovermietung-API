FROM jelastic/maven:3.9.5-openjdk-21
WORKDIR /app

COPY . .

RUN mvn clean package

CMD ./mvnw spring-boot:run
