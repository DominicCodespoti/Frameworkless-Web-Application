#Stage 1
FROM gradle:5.2.1-jdk11-slim AS BUILD
WORKDIR /bin
COPY --chown=gradle:gradle . /bin
RUN gradle build --no-daemon

#Stage 2
FROM openjdk:14-alpine
COPY /persons.txt /bin/persons.txt
COPY /Index.html /bin/Index.html
COPY --from=BUILD /bin/build/libs/*.jar /bin/server.jar
EXPOSE 8080
WORKDIR /bin
CMD ["java", "-jar", "server.jar", "0.0.0.0"]
