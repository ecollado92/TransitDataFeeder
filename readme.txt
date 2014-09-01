To run the application in a mode capable of Eclipse debugging
(see debug-jboss-transitdatafeeder.launch), before running
$JBOSS_HOME/bin/run.sh, you'll need to set the following Java
options (bash example provided)

export JAVA_OPTS="$JAVA_OPTS -Xdebug -Xrunjdwp:server=y,transport=dt_socket,address=8787,suspend=n"

One problem encountered is that in this mode, it appears that
memory consumption is greater and that running in this mode can
cause OutOfMemoryErrors upon launch.  Therefore, assuming you have
the memory available, set the memory options as follows:

export JAVA_OPTS="-Xms128m -Xmx512m -XX:MaxPermSize=256m -Dorg.jboss.resolver.warning=true -Dsun.rmi.dgc.client.gcInterval=3600000 -Dsun.rmi.dgc.server.gcInterval=3600000 $JAVA_OPTS"