#!/usr/bin/env bash

DAEMON_NAME=slackyoutrack
JAVA_HOME=/opt/java/jdk/1.8

# go to root dir
BINARY=$(readlink -f $0)
BASEDIR=$(dirname ${BINARY})

DAEMON_OPTIONS="--name=${DAEMON_NAME} --noconfig --output=/var/log/slackyoutrack/daemon.out"

#
# Compose class path
#
CLASSPATH="${BASEDIR}/etc"
for j in $(find ${BASEDIR} -name \*.jar); do
  CLASSPATH="${CLASSPATH}:${j}"
done

stop () {
    daemon ${DAEMON_OPTIONS} --stop
}

start() {
    daemon ${DAEMON_OPTIONS} -- \
        ${JAVA_HOME}/bin/java @jvm_flags@ \
        -cp ${CLASSPATH} \
        com.ontometrics.integrations.SlackYoutrack \
        @flags@ \
        $@
}

OP=$1
shift

case "$OP" in
    start)
        start
        ;;
    stop)
        stop
        ;;
    restart)
        stop
        sleep 1
        start
        ;;
    *)
        echo "*** Unknown command '${OP}'"
        ;;
esac
