# Sistema de Asistencia Escolar

Este es un proyecto desarrollado en Java para gestionar el registro de alumnos y controlar sus asistencias de forma sencilla.

## Funcionalidades Principales

- **Gestión de Estudiantes:** Permite agregar, buscar, editar y eliminar alumnos de la base de datos local.
- **Control de Asistencia:** Registra asistencias normales, salidas anticipadas e inasistencias extraordinarias (con sus respectivos motivos o justificaciones).
- **Persistencia de Datos:** Todos los registros se guardan automáticamente para no perderse. Se utilizan archivos `.csv` (ubicados en la carpeta `resources/`) para guardar los estudiantes y el historial.
- **Doble Interfaz:** El programa permite elegir al iniciar si se desea utilizar la interfaz gráfica (Ventana Visual) o si se prefiere interactuar mediante comandos en la Consola.

## Estructura

El sistema está dividido en entidades principales para mantener el orden:
- **Estudiante**: Representa la información de cada alumno (nombre, RUT, curso, edad, etc).
- **Asistencia**: Controla los diferentes tipos de registros de asistencia.
- **Curso**: Permite agrupar alumnos y obtener listados ordenados de su estado.

## Cómo ejecutarlo

Hemos agregado ejecutables para iniciar el programa con un par de clics:

- **En Linux o Mac:** Haz doble clic en el archivo `Ejecutar.sh` (y selecciona "Ejecutar en terminal"). O bien, desde una consola, ejecuta `./Ejecutar.sh`.
- **En Windows:** Haz doble clic en el archivo `Ejecutar.bat`.

*Nota:* La primera vez que lo ejecutes puede tardar unos segundos extra en compilar. Una vez abra, escribe el número del modo que quieres usar (1 para Ventana, 2 para Consola).
