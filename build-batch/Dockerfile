# FROM ubuntu
FROM java:8

MAINTAINER osprey

ARG app_version

# Access to a local /tmp drive?
VOLUME /tmp


# Pick up the jars to push out to the image
ADD ./lib/marketdata-batch-${app_version}.jar marketdata-batch-${app_version}.jar
ADD ./lib/marketdata-${app_version}.jar marketdata-${app_version}.jar
ADD ./lib/integration-${app_version}.jar integration-${app_version}.jar
ADD ./lib/math-${app_version}.jar math-${app_version}.jar
ADD ./lib/screen-${app_version}.jar screen-${app_version}.jar
ADD ./lib/securitymaster-${app_version}.jar securitymaster-${app_version}.jar
ADD ./lib/trade-${app_version}.jar trade-${app_version}.jar

ENV CLASSPATH /*.jar
ENV APP_VERSION ${app_version}

RUN sh -c 'touch /marketdata-batch-${app_version}.jar'

ENTRYPOINT ["java","-cp","./*.jar","-jar","marketdata-batch-0.0.1-SNAPSHOT.jar"]

# Expose the port for the Batch Server
EXPOSE 9001
