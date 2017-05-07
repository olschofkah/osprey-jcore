#!/bin/bash -v

# TODO ... instead of copying blindly ... construct properties in property files on an as needed basis or use additional dev ops tools.  

# Boot Projects
cp ./main/* ../marketdata-batch/src/main/resources/
cp ./main/* ../marketdata-events-batch/src/main/resources/
cp ./main/* ../marketdata-rest/src/main/resources/

# Libs
cp ./main/* ../marketdata/src/main/resources/
cp ./main/* ../securitymaster/src/main/resources/
cp ./main/* ../integration/src/main/resources/
cp ./main/* ../screen/src/main/resources/

echo "Configuration Pushed to local projects. "