FROM openjdk:17.0-jdk

COPY . .

RUN ./gradlew build -x test
RUN ./gradlew api:bootJar

CMD ["./gradlew", "api:BootRun"]
