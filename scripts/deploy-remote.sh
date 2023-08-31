#!/usr/bin/env bash

mvn clean package

echo 'Copy files...'
scp -i  ~/.ssh/id_rsa \
    target/user-USER-REMOTE.1.0.jar \
    t126@10.4.0.184:/home/t126/Projects/GAME

echo 'Restart server...'

scp -i  ~/.ssh/id_rsa.pub t126@10.4.0.184 <<EOF

pgrep java | xargs kill -9
nohup java -jar /home/t126/Projects/GAME/user-USER-REMOTE.1.0.jar > /home/t126/Projects/GAME/user-USER-REMOTE.1.0.Log.txt &

EOF

echo 'Done. Good day!'
