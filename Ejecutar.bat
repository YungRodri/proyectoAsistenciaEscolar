@echo off
echo Preparando Sistema de Asistencia Escolar...
if not exist "out" mkdir out
dir /s /B Proyecto\src\main\java\AsistenciaCurso\*.java > sources.txt
javac -d out @sources.txt
del sources.txt

echo Iniciando programa...
java -cp out AsistenciaCurso.Main
echo.
pause
