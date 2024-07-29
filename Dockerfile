FROM eclipse-temurin:21 AS application
WORKDIR /app
COPY build/libs/*.jar app.jar
CMD ["java", "-jar", "app.jar"]

#FROM rabbitmq:3-management AS rabbitmq
#RUN rabbitmq-plugins enable --offline rabbitmq_stomp rabbitmq_web_stomp
#EXPOSE 5672 15672 15674 61613
#ENV RABBITMQ_DEFAULT_USER=guest
#ENV RABBITMQ_DEFAULT_PASS=guest











