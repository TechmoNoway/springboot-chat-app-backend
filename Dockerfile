FROM rabbitmq:3-management AS rabbitmq
RUN rabbitmq-plugins enable --offline rabbitmq_stomp rabbitmq_web_stomp
RUN rabbitmq-plugins enable rabbitmq_management
EXPOSE 5672 15672 15674 61613
ENV RABBITMQ_DEFAULT_USER=guest
ENV RABBITMQ_DEFAULT_PASS=guest

FROM eclipse-temurin:21-jdk-alpine as springapp
COPY build/libs/*.jar /app/app.jar
WORKDIR /app
ENTRYPOINT ["java", "-jar", "app.jar"]




