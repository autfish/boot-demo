#!/bin/bash

#do not edit in windows

ID=`ps -ef | grep "java -jar oms-1.0.jar" | grep -v "grep" | awk '{print $2}'`
for id in $ID
do
kill $id
echo "killed $id"
done
