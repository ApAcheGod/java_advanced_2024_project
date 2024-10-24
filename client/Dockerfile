FROM bellsoft/liberica-openjdk-alpine-musl:21
ARG JAR_FILE=target/client-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]