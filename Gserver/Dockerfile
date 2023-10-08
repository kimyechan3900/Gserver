FROM openjdk:11-jre-slim
# OpenJDK 11 slim 버전을 베이스 이미지로 사용
ARG JAR_FILE=build/libs/Gserver-0.0.1-SNAPSHOT.jar
# 빌드 시 JAR 파일의 경로를 지정할 변수 설정
COPY ${JAR_FILE} app.jar
# JAR 파일을 이미지에 'app.jar'로 복사

EXPOSE 8080
# 8080 포트 열기

# wait-for-it.sh 스크립트
#RUN apk update && apk add bash

COPY wait-for-it.sh wait-for-it.sh
RUN chmod +x wait-for-it.sh

# 컨테이너 실행 시 JAR 파일 실행
ENTRYPOINT ["java","-jar","/app.jar"]