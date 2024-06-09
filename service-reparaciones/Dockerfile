FROM openjdk:17
ARG JAR_FILE=./target/*.jar
COPY ${JAR_FILE} service-reparaciones.jar
ENTRYPOINT ["java","-jar","/service-reparaciones.jar"]