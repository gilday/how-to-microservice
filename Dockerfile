FROM java:8
MAINTAINER Johnathan Gilday

COPY ./build/libs/how-to-microservice-0.0.4-SNAPSHOT-all.jar /opt/how-to-microservice/
EXPOSE 8000
WORKDIR /opt/how-to-microservice

CMD ["java", "-jar", "how-to-microservice-0.0.4-SNAPSHOT-all.jar"]

