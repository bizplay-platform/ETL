#!/bin/sh

source etlConfig.sh

./etlStop.sh

    echo "-------------------------------------------------------------------"
    echo "         StartUp Service A "
    echo ""
    echo "                CollectorManager Start !!!!"
    echo "-------------------------------------------------------------------"

    nohup $ETL_JAVA_HOME/bin/java $JAVA_OPTS $CLASS_PATH bizplay.etl.Run A > ${ETL_HOME}/logs/etlSvcA_`/bin/date +'%y-%m-%d'`.out &
    #nohup $ETL_JAVA_HOME/bin/java $JAVA_OPTS $CLASS_PATH bizplay.etl.Run A >/dev/null 2>&1
