FROM openjdk:17-jdk

COPY . .

RUN chmod +x gradlew
RUN ./gradlew build -x test
RUN ./gradlew api:bootJar

CMD ["java", "-jar", "./api/build/libs/api.jar"]
