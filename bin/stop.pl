#!/usr/bin/perl

$PRO_NAME="java -Dfile.encoding=GBK SRFC_CTRA";

my $pid=`ps -aux |grep -v grep|grep "$PRO_NAME"|awk '{print \$2}'`;
print "kill $PRO_NAME sender_pid : $pid";
my $result=`kill -9 $pid`;
print "result : $result\n";

