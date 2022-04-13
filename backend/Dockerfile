FROM openjdk:17.0-jdk

COPY . .

RUN ./gradlew build
RUN ./gradlew api:bootJar

CMD ["java", "-jar", "./api/build/libs/api.jar"]
