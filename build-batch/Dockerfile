# FROM ubuntu
FROM java:8

MAINTAINER osprey

ARG app_version

# Access to a local /tmp drive?
VOLUME /tmp


# Pick up the jars to push out to the image
ADD ./lib/marketdata-batch-${app_version}.jar ./lib/marketdata-batch-${app_version}.jar
ADD ./lib/marketdata-${app_version}.jar ./lib/marketdata-${app_version}.jar
ADD ./lib/integration-${app_version}.jar ./lib/integration-${app_version}.jar
ADD ./lib/math-${app_version}.jar ./lib/math-${app_version}.jar
ADD ./lib/screen-${app_version}.jar ./lib/screen-${app_version}.jar
ADD ./lib/securitymaster-${app_version}.jar ./lib/securitymaster-${app_version}.jar
ADD ./lib/trade-${app_version}.jar ./lib/trade-${app_version}.jar

ENV CLASSPATH ~/lib/marketdata-batch-${app_version}.jar:\
	~/lib/marketdata-${app_version}.jar:\
	~/lib/integration-${app_version}.jar:\
	~/lib/math-${app_version}.jar:\
	~/lib/screen-${app_version}.jar:\
	~/lib/securitymaster-${app_version}.jar:\
	~/lib/trade-${app_version}.jar

ENV APP_VERSION ${app_version}
ENV EDITOR vim

RUN sh -c 'touch /marketdata-batch-${APP_VERSION}.jar'
RUN echo ${CLASSPATH}

CMD java -jar ./lib/marketdata-batch-${APP_VERSION}.jar 
#CMD sh

# Expose the port for the Batch Server
EXPOSE 9001
