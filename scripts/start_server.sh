#!/bin/bash
jarname='url-shortener-0.0.1-SNAPSHOT.jar'
pid=`ps aux |grep $jarname |grep -v grep |awk '{print $2}'`
echo "find pid $pid,kill it"
if [ $pid ];then
  kill -9 $pid
fi
cd /home/ec2-user/web/ && nohup java -jar $jarname  -Xms 512m -Xmx512m > server.log 2>&1 &
echo "current directory is `pwd`,server started successfully..."