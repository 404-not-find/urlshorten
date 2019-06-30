#!/bin/bash
ls -alth
echo `pwd`
cd /home/ec2-user/web/
sudo chmod 777 start_server.sh
sudo chmod 777 url-shortener-0.0.1-SNAPSHOT.jar