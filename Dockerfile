FROM eclipse-temurin:21

# jar 파일 copy
ARG JAR_FILE=build/libs/KoominEat-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar

# static 폴더 생성 및 권한 설정.
RUN mkdir -p /static \
    && chown -R 1000:1000 /static \
    && chmod 755 /static

ENTRYPOINT ["java", "-jar", "/app.jar"]

