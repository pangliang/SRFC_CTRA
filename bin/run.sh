#!/bin/sh

export LANG="en_US"
export SUPPORTED="zh_CN.GB18030:zh_CN:zh:zh_TW.Big5:zh_TW:zh:en_US.UTF-8:en_US:en"
export SYSFONT="latarcyrheb-sun16"

GATE_HOME=/home/smsend/SRFC_CTRA

export CLASSPATH=$GATE_HOME:$GATE_HOME/lib/*:$CLASSPATH

/usr/java/jdk1.6.0/bin/java -Dfile.encoding="GBK" SRFC_CTRA >/dev/null &
