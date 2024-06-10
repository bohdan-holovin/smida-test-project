FROM openjdk:17-jdk
COPY target/smida-test-project-1.0.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]