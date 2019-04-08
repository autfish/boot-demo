#!/bin/bash

#do not edit in windows

# startup Server
cd /usr/local/oms
java -jar oms-1.0.jar -Xms4096m -Xmx4096m -XX:+PrintGCDetails -Xloggc:./logs/gc.log  --server.port=8091 --logging.config=./log4j2-spring.xml --spring.profiles.active=prod>spring.log &


