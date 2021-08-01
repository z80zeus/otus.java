#!/bin/bash

mkdir /tmp/java.logs/ > /dev/null 2>&1

HEAPSIZE="256"
HEAPSIZE="384"
HEAPSTEP="128"
MEMLIMIT="4100"
TESTS=10

while [[ $HEAPSIZE -lt $MEMLIMIT ]]; do

echo -n "${HEAPSIZE}m: "
PARAM="\
-Xms${HEAPSIZE}m \
-Xmx${HEAPSIZE}m \
-XX:+HeapDumpOnOutOfMemoryError \
-XX:HeapDumpPath=/tmp/java.logs/heapdump.hprof \
-XX:+UseG1GC \
-Xlog:gc=debug:file=/tmp/java.logs/gc-%p-%t.log:tags,uptime,time,level:filecount=5,filesize=10m \
"

#-XX:NewRatio=1
#-XX:NewSize=$HEAPSIZE
#-XX:MaxNewSize=$HEAPSIZE
#-Xmn:256m
#-XX:MaxGCPauseMillis=1

ACCUME=0
for ((i=0; i<$TESTS; i++)); do
    CUR=$(java -classpath ../build/classes/java/main/ $PARAM ru.calculator.CalcDemo | grep spend | sed "s/.*msec:\([0-9]*\).*/\1/")
    echo -n "$CUR "
    ACCUME=$(($ACCUME + $CUR))
done
echo "-> $(($ACCUME / $TESTS))"

HEAPSIZE=$(($HEAPSIZE + $HEAPSTEP))

done
