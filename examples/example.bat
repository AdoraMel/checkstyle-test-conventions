@echo off
java -classpath ..\build\libs\checkstyle-test-conventions.jar;checkstyle-10.14.2-all.jar ^
    com.puppycrawl.tools.checkstyle.Main -c config.xml ..\
pause