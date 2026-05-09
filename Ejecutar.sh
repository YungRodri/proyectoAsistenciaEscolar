#!/bin/bash
echo "Preparando Sistema de Asistencia Escolar..."
# Nos movemos a la carpeta Proyecto donde esta el pom.xml
cd "$(dirname "$0")/Proyecto"

# Verificar si Maven esta instalado
if ! command -v mvn &> /dev/null
then
    echo "=========================================================="
    echo " ERROR: Maven no esta instalado en este sistema."
    echo " Este proyecto requiere Maven para descargar y compilar"
    echo " las librerias graficas y de Excel (JFreeChart, Apache POI)."
    echo ""
    echo " Para instalarlo en Ubuntu/Linux, ejecuta:"
    echo " sudo apt-get update && sudo apt-get install maven"
    echo "=========================================================="
    read -p "Presiona ENTER para salir..."
    exit 1
fi

echo "Compilando proyecto con Maven..."
mvn clean compile

echo "Iniciando programa..."
mvn exec:java -Dexec.mainClass="AsistenciaCurso.Main"

echo ""
read -p "Presiona ENTER para salir..."
