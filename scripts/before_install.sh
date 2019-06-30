#!/bin/bash
sudo mkdir -p /home/ec2-user/web/data
cd /home/ec2-user/web/ && sudo chmod 777 start_server.sh
cd /home/ec2-user/web/ && sudo chmod 777 url-shortener-0.0.1-SNAPSHOT.jar