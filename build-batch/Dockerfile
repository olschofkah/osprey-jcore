# FROM ubuntu
FROM java:8

MAINTAINER osprey

ARG app_version

# Access to a local /tmp drive?
VOLUME /tmp


# Pick up the jars to push out to the image
ADD ./lib/marketdata-batch-${app_version}.jar ./lib/marketdata-batch.jar
ADD ./lib/marketdata-${app_version}.jar ./lib/marketdata.jar
ADD ./lib/integration-${app_version}.jar ./lib/integration.jar
ADD ./lib/math-${app_version}.jar ./lib/math.jar
ADD ./lib/screen-${app_version}.jar ./lib/screen.jar
ADD ./lib/securitymaster-${app_version}.jar ./lib/securitymaster.jar
ADD ./lib/trade-${app_version}.jar ./lib/trade.jar

ENV CLASSPATH ~/lib/marketdata-batch.jar:\
	~/lib/marketdata.jar:\
	~/lib/integration.jar:\
	~/lib/math.jar:\
	~/lib/screen.jar:\
	~/lib/securitymaster.jar:\
	~/lib/trade.jar

ENV APP_VERSION ${app_version}
ENV EDITOR vim

RUN sh -c 'touch /marketdata-batch.jar'
RUN echo ${CLASSPATH}

CMD java -jar ./lib/marketdata-batch.jar 
#CMD sh

# Expose the port for the Batch Server
# EXPOSE 9001
