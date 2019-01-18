@ECHO ON

SET ROOT_PATH=C:\workspace\spring\assignment2

SET BIN_HOME=%ROOT_PATH%/target/classes

SET LIB_HOME=%ROOT_PATH%/lib

SET CLASSPATH=%BIN_HOME%;%LIB_HOME%/spring-context-3.2.3.RELEASE.jar;%LIB_HOME%/spring-core-3.2.3.RELEASE.jar;%LIB_HOME%/spring-beans-3.2.3.RELEASE.jar;%LIB_HOME%/commons-logging-1.1.1.jar;%LIB_HOME%/spring-expression-3.2.3.RELEASE.jar;%LIB_HOME%/spring-aop-3.2.3.RELEASE.jar;%LIB_HOME%/aopalliance-1.0.jar;


ECHO java com.meta.main.MainClass %1
java com.meta.main.MainClass %1

pause