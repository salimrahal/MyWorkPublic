#!/bin/bash
#
#	/etc/rc.d/init.d/sipEchoserver
#
#	sipEchoserver
#	<any general comments about this init script>
#
# chkconfig: 345 70 30
# description: sipEchoserver
# processname: sipEchoserver


#source function library.
. /etc/init.d/functions

RETVAL=0
prog="sipEchoserver"
LOCKFILE=/var/lock/subsys/$prog

start() {
	echo -n "Starting sipEchoserver: "
	#screen -dmLS sipserver java -jar siptoolserver/SipToolServer.jar 5069
        #sudo screen -dmLS sipserver java -jar siptoolserver/SipToolServer.jar $1
        java -jar /home/srahal/sipworkspace/siptoolserver/SipToolServer.jar $1 > /home/srahal/sipworkspace/siptoolserver/log.$(date +"%Y-%m-%d-
%T") &
	RETVAL=$?
        [ $RETVAL -eq 0 ] && touch $LOCKFILE
        echo
        return $RETVAL
}	

stop() {
	echo -n "Shutting down sipEchoserver: "
	rm -f /var/lock/subsys/sipEchoserver
        RETVAL=$?
        [ $RETVAL -eq 0 ] && rm -f $LOCKFILE
        echo
        return $RETVAL
}

status() {
        echo -n "Checking $prog status: "
        RETVAL=$?
        return $RETVAL
}

case "$1" in
    start)
	start
	;;
    stop)
	stop
	;;
    status)
	status
	;;
    restart)
    	stop
	start
	;;
    *)
	echo "Usage: $prog {start|stop|status]"
	exit 1
	;;
esac
exit $?
