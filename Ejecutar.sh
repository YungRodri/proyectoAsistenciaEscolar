#!/bin/bash
echo "Preparando Sistema de Asistencia Escolar..."
cd "$(dirname "$0")"

mkdir -p out
javac -d out $(find Proyecto/src/main/java/AsistenciaCurso -name "*.java")

echo "Iniciando programa..."
java -cp out AsistenciaCurso.Main

echo ""
read -p "Presiona ENTER para salir..."
