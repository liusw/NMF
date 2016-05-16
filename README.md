mvn install:install-file -DgroupId=com.boyaa -DartifactId=hb-base -Dversion=1.0 -Dfile=/data/wwwroot/mf/java/NMF/lib/hb-base.jar -Dpackaging=jar
mvn install:install-file -DgroupId=com.boyaa -DartifactId=hb-base -Dversion=1.2 -Dfile=/data/wwwroot/mf/java/NMF/lib/hb-base.jar -Dpackaging=jar


项目说明:
1.建一个目录
mkdir -p /data/mf/tomcat/webapps/mf/WEB-INF/classes/
ln -s /home/hadoop/workspace/NMF/src/main/dev-resources/app.config  app.config
ln -s /home/hadoop/workspace/NMF/src/main/dev-resources/system.config system.config

2.项目打包与运行
打包
mvn clean compile package -Pproduct -DskipTests

运行
-Pdev jetty:run -DskipTests

3.开发新功能在新包下开发
com.boyaa.mf.xxx


