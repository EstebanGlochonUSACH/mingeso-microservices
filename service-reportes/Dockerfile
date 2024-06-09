FROM openjdk:17
ARG JAR_FILE=./target/*.jar
COPY ${JAR_FILE} service-reportes.jar
ENTRYPOINT ["java","-jar","/service-reportes.jar"]