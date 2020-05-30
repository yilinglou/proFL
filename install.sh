#!/bin/bash

echo -n "Installing ProFL Maven Plugin to local mvn repo..."

mvn install:install-file -Dfile=tool/proFL-plugin-2.0.3-SNAPSHOT.jar -DgroupId=org.mudebug  -DartifactId=prapr-plugin -Dversion=2.0.3-SNAPSHOT -Dpackaging=jar > /dev/null 2>&1

echo "DONE"
