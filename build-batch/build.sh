#!/bin/bash

mvn package -DskipTests

cp ../marketdata-batch/target/marketdata-batch-${1}.jar ./lib/
cp ../marketdata/target/marketdata-${1}.jar ./lib/
cp ../integration/target/integration-${1}.jar ./lib/
cp ../math/target/math-${1}.jar ./lib/
cp ../screen/target/screen-${1}.jar ./lib/
cp ../securitymaster/target/securitymaster-${1}.jar ./lib/
cp ../trade/target/trade-${1}.jar ./lib/

cat Dockerfile

docker build --build-arg app_version=${1} -t osprey-batch .
