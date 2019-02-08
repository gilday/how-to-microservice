FROM openjdk:11-jre
MAINTAINER Johnathan Gilday

ADD ./build/distributions/how-to-microservice-0.0.7.tar /opt
EXPOSE 8000
WORKDIR /opt/how-to-microservice-0.0.7

CMD ["./bin/how-to-microservice"]
