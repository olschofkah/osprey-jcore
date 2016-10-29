#!/bin/bash

if [ $# -eq 0 ]
  then
    echo "Must supply the current expected build version"
    exit -1
fi

mvn clean compile validate package -DskipTests
#mvn clean compile validate package

cp ../marketdata-batch/target/marketdata-batch-${1}.jar ./lib/
cp ../marketdata/target/marketdata-${1}.jar ./lib/
cp ../integration/target/integration-${1}.jar ./lib/
cp ../math/target/math-${1}.jar ./lib/
cp ../screen/target/screen-${1}.jar ./lib/
cp ../securitymaster/target/securitymaster-${1}.jar ./lib/
cp ../trade/target/trade-${1}.jar ./lib/

cat Dockerfile

DOCKER_AWS_LOGIN=`aws ecr get-login --region us-east-1`

echo "${DOCKER_AWS_LOGIN}"

${DOCKER_AWS_LOGIN}

docker build --no-cache --build-arg app_version=${1} -t ospreycapital/marketdata-batch .


# Tag the build
docker tag ospreycapital/marketdata-batch:${1} 620041067453.dkr.ecr.us-east-1.amazonaws.com/ospreycapital/marketdata-batch:${1}

# Push the build to AWS EC2 Container Manager
docker push 620041067453.dkr.ecr.us-east-1.amazonaws.com/ospreycapital/marketdata-batch:${1}

# Tag the build
docker tag ospreycapital/marketdata-batch:latest 620041067453.dkr.ecr.us-east-1.amazonaws.com/ospreycapital/marketdata-batch:latest

# Push the build to AWS EC2 Container Manager
docker push 620041067453.dkr.ecr.us-east-1.amazonaws.com/ospreycapital/marketdata-batch:latest
