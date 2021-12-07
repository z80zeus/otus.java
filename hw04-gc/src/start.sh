#!/bin/bash

LOGDIR=/tmp/java.logs
mkdir $LOGDIR > /dev/null 2>&1

HEAPSIZE="256"
HEAPSTEP="128"
MEMLIMIT="2100"
TESTS=10

while [[ $HEAPSIZE -lt $MEMLIMIT ]]; do

echo -n "${HEAPSIZE}m: "
PARAM="\
-Xms${HEAPSIZE}m \
-Xmx${HEAPSIZE}m \
-XX:+HeapDumpOnOutOfMemoryError \
-XX:HeapDumpPath=$LOGDIR/heapdump.hprof \
-Xlog:gc=debug:file=$LOGDIR/gc-%p-%t.log:tags,uptime,time,level:filecount=5,filesize=10m \
-XX:+UseParallelGC -XX:-UseParallelOldGC -XX:ParallelGCThreads=2
"

#-XX:+UseG1GC

ACCUM=0
for ((i=0; i<$TESTS; i++)); do
    CUR=$(java -classpath ../build/classes/java/main/ $PARAM ru.calculator.CalcDemo | grep spend | sed "s/.*msec:\([0-9]*\).*/\1/")
    echo -n "$CUR "
    ACCUM=$(($ACCUM + $CUR))
done
echo "-> $(($ACCUM / $TESTS))"

HEAPSIZE=$(($HEAPSIZE + $HEAPSTEP))

done
