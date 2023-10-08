FROM openjdk:11-jre-slim
ARG JAR_FILE=../build/libs/Gserver-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar

EXPOSE 8080

# wait-for-it.sh
RUN apk update && apk add bash
COPY wait-for-it.sh wait-for-it.sh
RUN chmod +x wait-for-it.sh


ENTRYPOINT ["java","-jar","/app.jar"]