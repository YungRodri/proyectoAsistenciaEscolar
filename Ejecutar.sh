#!/bin/bash
echo "Preparando Sistema de Asistencia Escolar..."
cd "$(dirname "$0")/Proyecto"
mvn clean package
echo "Iniciando programa..."
java -jar target/Proyecto-1.0-SNAPSHOT.jar
echo ""
read -p "Presiona ENTER para salir..."
