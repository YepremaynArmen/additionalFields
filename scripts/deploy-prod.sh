#!/usr/bin/env bash

mvn clean package

echo 'Copy files...'
scp -i  ~/.ssh/id_rsa \
    target/prod-1.0-SNAPSHOT.jar \
    t126@10.4.0.30:/home/t126/projects/BSP

echo 'Restart server...'

scp -i  ~/.ssh/id_rsa.pub t126@10.4.0.30 <<EOF

pgrep java | xargs kill -9
nohup java -jar /home/t126/projects/BSP/prod-1.0-SNAPSHOT.jar > BSPLog.txt &

EOF

echo 'Done. Good day!'
