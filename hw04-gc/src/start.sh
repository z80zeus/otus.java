#!/bin/bash

HEAPSIZE=512m
PARAM="\
-Xms$HEAPSIZE \
-Xmx$HEAPSIZE \
-XX:+HeapDumpOnOutOfMemoryError \
-XX:HeapDumpPath=/tmp/java.logs/heapdump.hprof \
-XX:+UseG1GC \
-Xlog:gc=debug:file=/tmp/java.logs/gc-%p-%t.log:tags,uptime,time,level:filecount=5,filesize=10m
"

java -classpath ../build/classes/java/main/ $PARAM ru.calculator.CalcDemo
