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

Al tratarse de un proyecto Java gestionado con **Maven**, puedes abrir la carpeta `Proyecto` en tu editor de código favorito (como IntelliJ IDEA, Eclipse o VS Code) y simplemente ejecutar la clase principal: `AsistenciaCurso.Main`.

¡Al iniciar, el programa te preguntará qué modo de visualización prefieres usar!
