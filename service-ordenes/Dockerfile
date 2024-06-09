FROM openjdk:17
ARG JAR_FILE=./target/*.jar
COPY ${JAR_FILE} service-ordenes.jar
ENTRYPOINT ["java","-jar","/service-ordenes.jar"]