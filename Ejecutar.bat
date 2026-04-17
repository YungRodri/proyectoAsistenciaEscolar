@echo off
echo Preparando Sistema de Asistencia Escolar...
cd Proyecto
call mvn clean package
echo Iniciando programa...
java -jar target/Proyecto-1.0-SNAPSHOT.jar
echo.
pause
