#!/usr/bin/perl

$GATE_NAME="SRFC_CTRA";
$HOME="/home/smsend/SRFC_CTRA";

$ps =`ps -aux |grep $GATE_NAME`;
$ps =~ s/grep $GATE_NAME//g;

if($ps !~ /java .* $GATE_NAME/)
{
	logs("start.......");
	system("$HOME/run.sh");	
}else{
#logs("running......");
}


sub logs{
        my ($msg) = @_;
        my $timeflag = localtime(time);
        open (FILE,">>$HOME/log/watchdog.log");
	print "$timeflag $msg\n";
        print FILE "$timeflag $msg\n";
        close(FILE);
        return; 
}   
