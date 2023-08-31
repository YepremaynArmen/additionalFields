#!/usr/bin/env bash

mvn clean package

echo 'Copy files...'
scp -i  ~/.ssh/id_rsa \
    target/LessonsProj-1.0-SNAPSHOT.jar \
    t126@10.3.0.39:/home/t126/projects/ElJur

echo 'Restart server...'

scp -i  ~/.ssh/id_rsa.pub t126@10.3.0.39 <<EOF

pgrep java | xargs kill -9
nohup java -jar /home/t126/projects/ElJur/LessonsProj-1.0-SNAPSHOT.jar > ElJurLog.txt &

EOF

echo 'Done. Good day!'
