#######################################################################
#       DOCKERFILE : BACKEND
#######################################################################
#
# Build stage
#
FROM maven:3.6.0-jdk-11-slim AS build
RUN apt-get update
RUN apt-get -y install net-tools
RUN apt-get install -y iputils-ping
RUN apt-get install -y telnet

#RUN rm -rf /home/app/*
WORKDIR /home/app/
# Copies des binaires (*/src )de notre application
ADD ./Application/src Application/src
ADD ./Domain/src Domain/src
ADD ./Infrastructure/src Infrastructure/src
ADD ./Exposition/src Exposition/src
# Copies des binaires (pom.xml) de notre application
COPY ./pom.xml .
COPY ./Exposition/pom.xml Exposition/pom.xml
COPY ./Application/pom.xml Application/pom.xml
COPY ./Domain/pom.xml Domain/pom.xml
COPY ./Infrastructure/pom.xml Infrastructure/pom.xml

RUN mvn clean 
#RUN mvn compile
RUN mvn package -DskipTests

#
# Package stage
#
WORKDIR /home/app/Exposition/target
EXPOSE 8080
#ENTRYPOINT ["tail", "-f", "/dev/null"]
ENTRYPOINT ["java", "-jar", "Exposition-1.0-SNAPSHOT.jar"]