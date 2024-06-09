FROM openjdk:17
ARG JAR_FILE=./target/*.jar
COPY ${JAR_FILE} service-autos.jar
ENTRYPOINT ["java","-jar","/service-autos.jar"]