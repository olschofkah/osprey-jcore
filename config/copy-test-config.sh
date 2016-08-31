#!/bin/bash -v

# Boot Projects
cp ./test/* ../marketdata-batch/src/test/resources/
cp ./test/* ../marketdata-rest/src/test/resources/

# Libs
cp ./test/* ../marketdata/src/test/resources/
cp ./test/* ../securitymaster/src/test/resources/
cp ./test/* ../integration/src/test/resources/
cp ./test/* ../screen/src/test/resources/

echo "Configuration Pushed to local projects. "