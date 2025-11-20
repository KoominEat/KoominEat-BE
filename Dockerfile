FROM eclipse-temurin:21

# jar 파일 copy
ARG JAR_FILE=build/libs/KoominEat-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]

