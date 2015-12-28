#!/bin/sh

ETL_JAVA_HOME=/home/etl/jvm/jdk1.7.0_79

ETL_HOME=/home/etl
ETL_CONFIG=/conf/etl.config.xml
ETL_QUERY_STORE=/conf/etl.query.store.xml

JAVA_OPTS="${JAVA_OPTS} -DETL_HOME=$ETL_HOME"
JAVA_OPTS="${JAVA_OPTS} -DETL_CONFIG=$ETL_CONFIG"
JAVA_OPTS="${JAVA_OPTS} -DETL_QUERY_STORE=$ETL_QUERY_STORE"


JAVA_OPTS="${JAVA_OPTS} -Dlogback.configurationFile=$ETL_HOME/conf/logback.xml"

CLASS_PATH="-classpath ${CLASS_PATH}:$ETL_HOME/lib/bb-etl-1.0.0.jar"
CLASS_PATH="${CLASS_PATH}:$ETL_HOME/lib/commons-compiler.jar"
CLASS_PATH="${CLASS_PATH}:$ETL_HOME/lib/janino.jar"
CLASS_PATH="${CLASS_PATH}:$ETL_HOME/lib/json_simple-1.1.jar"
CLASS_PATH="${CLASS_PATH}:$ETL_HOME/lib/logback-classic-1.1.3.jar"
CLASS_PATH="${CLASS_PATH}:$ETL_HOME/lib/logback-core-1.1.3.jar"
CLASS_PATH="${CLASS_PATH}:$ETL_HOME/lib/httpcore-4.1.2.jar"
CLASS_PATH="${CLASS_PATH}:$ETL_HOME/lib/postgresql.jar"
CLASS_PATH="${CLASS_PATH}:$ETL_HOME/lib/slf4j-api-1.6.5.jar"



