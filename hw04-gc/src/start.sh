#!/bin/bash

LOGDIR=/tmp/java.logs
mkdir $LOGDIR > /dev/null 2>&1

HEAPSIZE="2048"
HEAPSTEP="128"
#MEMLIMIT="600"
MEMLIMIT="4100"
TESTS=3

while [[ $HEAPSIZE -lt $MEMLIMIT ]]; do

echo -n "${HEAPSIZE}m: "
PARAM="\
-Xms${HEAPSIZE}m \
-Xmx${HEAPSIZE}m \
-XX:+HeapDumpOnOutOfMemoryError \
-XX:HeapDumpPath=$LOGDIR/heapdump.hprof \
-Xlog:gc=debug:file=$LOGDIR/gc-%p-%t.log:tags,uptime,time,level:filecount=5,filesize=10m \
-XX:+UseParallelGC -XX:+UseLargePages
"

#PARAM="-server -XX:+UseParallelGC -XX:+UseLargePages -Xmn4g  -Xms8g -Xmx8g"
#PARAM="-XX:+UseG1GC -Xmn4g  -Xms8g -Xmx8g"
#PARAM="-XX:+UseSerialGC -XX:+UseParallelOldGC -Xmn4g  -Xms8g -Xmx8g"
#PARAM="-XX:+UseConcMarkSweepGC -Xmn4g  -Xms8g -Xmx8g"

#-XX:+UseParallelGC
#-XX:+UseG1GC \
#-XX:+UseSerialGC
#-XX:-UseParallelOldGC
# -XX:+UnlockExperimentalVMOptions -XX:+UseZGC

#-XX:NewRatio=1
#-XX:NewSize=$HEAPSIZE
#-XX:MaxNewSize=$HEAPSIZE
#-Xmn:256m
#-XX:MaxGCPauseMillis=1

ACCUM=0
for ((i=0; i<$TESTS; i++)); do
    CUR=$(java -classpath ../build/classes/java/main/ $PARAM ru.calculator.CalcDemo | grep spend | sed "s/.*msec:\([0-9]*\).*/\1/")
    echo -n "$CUR "
    ACCUM=$(($ACCUM + $CUR))
done
echo "-> $(($ACCUM / $TESTS))"

HEAPSIZE=$(($HEAPSIZE + $HEAPSTEP))

done
