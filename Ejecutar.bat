@echo off
echo Preparando Sistema de Asistencia Escolar...
cd Proyecto

REM Verificar si Maven esta instalado
where mvn >nul 2>nul
if %errorlevel% neq 0 (
    echo ==========================================================
    echo  ERROR: Maven no esta instalado o no esta en el PATH.
    echo  Este proyecto requiere Maven para compilar las librerias
    echo  graficas y de Excel (JFreeChart, Apache POI).
    echo.
    echo  Por favor, instala Maven o ejecuta el proyecto
    echo  directamente desde tu IDE (NetBeans, IntelliJ, Eclipse).
    echo ==========================================================
    pause
    exit /b
)

echo Compilando proyecto con Maven...
call mvn clean compile

echo Iniciando programa...
call mvn exec:java -Dexec.mainClass="AsistenciaCurso.Main"

echo.
pause
