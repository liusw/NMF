tomcatDir='/data/mf/tomcat'
backupDir='/data/mf'
td=`date +"%Y%m%d_%H%M%S"`
baseDir="/data/wwwroot/mf/java/NMF"
warFile="mf.war"

echo "backup file $tomcatDir/webapps/$warFile to $backupDir/$td-$warFile"
mv "$tomcatDir/webapps/$warFile" "$backupDir/$td-$warFile"


echo "copy file $baseDir/target/$warFile to $tomcatDir/webapps/"
cp "$baseDir/target/$warFile"  "$tomcatDir/webapps/"

echo "shutdown tomcat"
$tomcatDir/bin/shutdown.sh

sleep 5

echo "kill tomcat pid"
tomcatPID=`ps aux | grep tomcat | grep /data/mf/tomcat | grep -v grep | grep -v retomcat | awk '{print $2}'`
for element in $tomcatPID
do
    echo "kill " $element  
    kill -9 $element
done

echo "remove tomcat file"
rm -rf "$tomcatDir/work"
rm -rf "$tomcatDir/webapps/mf"

echo "startup tomcat"
$tomcatDir/bin/startup.sh
