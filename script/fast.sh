#! /bin/bash

# sh fast.sh start 启动 stop 停止 restart 重启 backup 备份 status 状态
AppName=fast-start.jar

# JVM参数
JVM_OPTS="-Dname=$AppName -Duser.timezone=Asia/Shanghai -Xms512m -Xmx1024m"

# jar包目录
AppDir=/data/java
BUILD_ID=$AppName

function start()
{
    PID=`ps -ef |grep java|grep $AppName|grep -v grep|awk '{print $2}'`

	if [ x"$PID" != x"" ]; then
	    echo "$AppName is running..."
	else
	  cd $AppDir
		nohup java $JVM_OPTS -jar $AppName --spring.profiles.active=prod > log.log &
		echo "Start $AppName success..."
	fi
}

function stop()
{
  echo "Stop $AppName"

	PID=""
	query(){
		PID=`ps -ef |grep java|grep $AppName|grep -v grep|awk '{print $2}'`
	}

	query
	if [ x"$PID" != x"" ]; then
		kill -TERM $PID
		echo "$AppName (pid:$PID) exiting..."
		while [ x"$PID" != x"" ]
		do
			sleep 1
			query
		done
		echo "$AppName exited."
	else
		echo "$AppName already stopped."
	fi
}

function restart()
{
    stop
    sleep 1
    start
}

function status()
{
    PID=`ps -ef |grep java|grep $AppName|grep -v grep|wc -l`
    if [ $PID != 0 ];then
        echo "$AppName is running..."
    else
        echo "$AppName is not running..."
    fi
}

function backup()
{
    if [ -d "$AppDir/backup" ]; then
        echo "backup file directory is found"
    else
        mkdir "$AppDir/backup"
        echo "mkdir backup file directory"
    fi

    if [ -f "$AppDir/$AppName" ] && [ -x "$AppDir/$AppName" ]; then
        echo "$AppName file is not found"
    else
        cp "$AppDir/$AppName" "$AppDir/backup/$AppName" -f
        echo "$AppName file copy success"
    fi
}

case $1 in
    start)
    start;;
    stop)
    stop;;
    restart)
    restart;;
    status)
    status;;
    backup)
    backup;;
    *)

esac