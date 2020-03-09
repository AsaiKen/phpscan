#!/bin/bash -e
rm -rf phpscan-1.0-SNAPSHOT/ && ./gradlew clean && ./gradlew build -x test && unzip build/distributions/phpscan-1.0-SNAPSHOT.zip
