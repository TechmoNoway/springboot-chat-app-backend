FROM rabbitmq:3-management AS rabbitmq
RUN rabbitmq-plugins enable --offline rabbitmq_stomp rabbitmq_web_stomp
RUN rabbitmq-plugins enable rabbitmq_management
EXPOSE 5672 15672 15674 61613
ENV RABBITMQ_DEFAULT_USER=guest
ENV RABBITMQ_DEFAULT_PASS=guest

FROM eclipse-temurin:21-jdk-alpine AS springapp
COPY build/libs/*.jar /app/app.jar
WORKDIR /app
ENTRYPOINT ["java", "-jar", "app.jar"]

FROM mysql:8.0 AS mysql
ENV MYSQL_ROOT_PASSWORD=root_password
ENV MYSQL_DATABASE=example_db
ENV MYSQL_USER=user
ENV MYSQL_PASSWORD=user_password
EXPOSE 3306
CMD ["mysqld"]

FROM redis:latest AS redis
EXPOSE 6379
CMD ["redis-server"]








