#!/bin/bash -v

# Boot Projects
cp ./main/* ../marketdata-batch/src/main/resources/
cp ./main/* ../marketdata-rest/src/main/resources/

# Libs
cp ./main/* ../marketdata/src/main/resources/
cp ./main/* ../securitymaster/src/main/resources/
cp ./main/* ../integration/src/main/resources/
cp ./main/* ../screen/src/main/resources/

echo "Configuration Pushed to local projects. "