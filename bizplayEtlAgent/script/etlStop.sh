#!/bin/sh

if [ `ps -ef | grep bizplay.etl.DatabaseConnectTest | grep -v grep | awk {'print $2'}` ] ; then
                kill -9  `ps -ef | grep bizplay.etl.DatabaseConnectTest | grep -v grep | awk {'print $2'}`
                echo "============================================"
                echo "DataBase Connect Test AGENT Stop.."
                echo "============================================"
fi

if [ `ps -ef | grep bizplay.etl.Run | grep -v grep | awk {'print $2'}` ] ; then
                kill -9  `ps -ef | grep bizplay.etl.Run | grep -v grep | awk {'print $2'}`
                echo "============================================"
                echo "SERVICE CHECK AGENT Stop.."
                echo "============================================"
fi


