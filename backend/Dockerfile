FROM openjdk:17.0-jdk

RUN microdnf install findutils

COPY . .

RUN chmod +x gradlew
RUN ./gradlew build -x test
RUN ./gradlew api:bootJar

CMD ["./gradlew", "api:BootRun"]
