FROM eclipse-temurin:17-jdk

RUN apt-get -y install findutils

COPY . .

RUN chmod +x gradlew
RUN ./gradlew build -x test
RUN ./gradlew api:bootJar

CMD ["./gradlew", "api:BootRun"]
