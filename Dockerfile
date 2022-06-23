FROM openjdk:17

COPY target/user-server.jar app-user.jar

EXPOSE 9090

ENTRYPOINT ["java", "-jar", "/app-user.jar"]
