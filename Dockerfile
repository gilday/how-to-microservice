FROM openjdk:11-jre
MAINTAINER Johnathan Gilday

COPY ./build/libs/how-to-microservice-0.0.6-all.jar /opt/how-to-microservice/
EXPOSE 8000
WORKDIR /opt/how-to-microservice

CMD ["java", "--add-modules", "java.xml.bind", "-jar", "how-to-microservice-0.0.6-all.jar"]
