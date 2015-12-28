#!/bin/sh

source etlConfig.sh


#echo "????"
#echo ${ETL_JAVA_HOME}
#echo $ETL_HOME
#echo $JAVA_OPTS
#echo $CLASS_PATH


./etlStop.sh


    echo "-------------------------------------------------------------------"
    echo "         StartUp ETL"
    echo ""
    echo "                DataBase Connect Test Start !!!!"
    echo "-------------------------------------------------------------------"

nohup $ETL_JAVA_HOME/bin/java $JAVA_OPTS $CLASS_PATH bizplay.etl.DatabaseConnectTest > ${ETL_HOME}/logs/etlDbcon_`/bin/date +'%y-%m-%d'`.out &
#nohup $ETL_JAVA_HOME/bin/java $JAVA_OPTS $CLASS_PATH bizplay.etl.DatabaseConnectTest & 

