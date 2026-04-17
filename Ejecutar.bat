@echo off
echo Preparando Sistema de Asistencia Escolar...
if not exist "out" mkdir out
javac -d out Proyecto\src\main\java\AsistenciaCurso\*.java

echo Iniciando programa...
java -cp out AsistenciaCurso.Main
echo.
pause
