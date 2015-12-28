#!/bin/sh

source etlConfig.sh

./etlStop.sh

    echo "-------------------------------------------------------------------"
    echo "         StartUp Service B "
    echo ""
    echo "                Agent Service Start !!!!"
    echo "-------------------------------------------------------------------"

    nohup $ETL_JAVA_HOME/bin/java $JAVA_OPTS $CLASS_PATH bizplay.etl.Run B > ${ETL_HOME}/logs/etlSvcB_`/bin/date +'%y-%m-%d'`.out &
    #nohup $ETL_JAVA_HOME/bin/java $JAVA_OPTS $CLASS_PATH bizplay.etl.Run B >/dev/null 2>&1
