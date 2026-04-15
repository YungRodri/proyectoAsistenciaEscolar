/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AsistenciaCurso;

import java.util.TreeMap;
import java.util.Scanner;

public class Main {

    public static TreeMap<String, Estudiante> listaGlobal = new TreeMap<>();
    static Scanner entrada = new Scanner(System.in);
    public static String listaCursos[] = {"1° Básico", "2° Básico", "3° Básico", "4° Básico","5° Básico",
    "6° Básico", "7° Básico", "8° Básico", "1° Medio","2° Medio", "3° Medio", "4° Medio"};
    
    
    public static void agregarEstudiante(){
        String opcion;
        int numCurso;
        
        do{
        
        System.out.println("Nombre Estudiante: ");
        String nombre = entrada.nextLine();
        
        System.out.println("Apellido Paterno: ");
        String apellidoP = entrada.nextLine();
        
        System.out.println("Apellido Materno: ");
        String apellidoM = entrada.nextLine();
        
        System.out.println("RUT: ");
        String rut = entrada.nextLine();
        
        System.out.println("Edad: ");
        int edad = Integer.parseInt(entrada.nextLine());
        
        do{
        System.out.println("Seleccione un Curso (1-12): ");
            for(int i=0; i < listaCursos.length; i++){
                System.out.println((i+1)+". " +listaCursos[i]);
            }
            numCurso = Integer.parseInt(entrada.nextLine());
            if(numCurso <1 || numCurso > listaCursos.length){
                System.out.println("Ingrese un número válido (1-12)");
            }
        }while(numCurso <1 || numCurso > listaCursos.length);
        
            String curso = listaCursos[numCurso -1];
        
        Estudiante alumno = new Estudiante(nombre,apellidoP,apellidoM,rut,edad,curso);
        listaGlobal.put(alumno.getRut(), alumno);
        
        System.out.println("¿Continuar? (S/N): ");
        opcion = entrada.nextLine();
        }while(opcion.equalsIgnoreCase("S"));
        System.out.println("Ingreso Finalizado");
        
    }
    
    public static void mostrarEstudiante(TreeMap<String, Estudiante> mapa){
        System.out.println("-------Lista de Estudiantes------");
        int i = 1;
        for (Estudiante alumno : mapa.values()) {
            System.out.println("Estudiante #" + i++);
            System.out.println(alumno.getNombre() + " " + alumno.getApellidoP() + " " + alumno.getApellidoM());
            System.out.println("Curso: " + alumno.getCurso());
            System.out.println("===============");
        }
    }
    
    public static Curso GenerarCurso(){
        int numCurso;
        
        System.out.println("Seleccione un curso: ");
        for(int i=0; i < listaCursos.length; i++){
                System.out.println((i+1)+". " +listaCursos[i]);
            }
        numCurso = Integer.parseInt(entrada.nextLine());
        if(numCurso <= listaCursos.length && numCurso > 0){
            System.out.println("Curso generado!!");
            return new Curso(listaCursos[numCurso-1]);
        }else{
            return null;
        }
    }
    
    public static void main(String[] args){
        // Sistema Batch: Cargar datos al iniciar
        listaGlobal = GestorArchivos.cargarEstudiantes();
        
        System.out.println("====== INICIO ======");
        System.out.println("Seleccione el modo de ejecución:");
        System.out.println("1. Modo Interfaz Gráfica (Ventana)");
        System.out.println("2. Modo Consola");
        System.out.print("Opción: ");
        
        int modo = 2;
        try {
            modo = Integer.parseInt(entrada.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida. Iniciando en modo consola por defecto...");
        }

        if (modo == 1) {
            java.awt.EventQueue.invokeLater(new Runnable() {
                public void run() {
                    new Ventana().setVisible(true);
                }
            });
            return; // Termina el hilo de consola, el de GUI sigue vivo
        }
        
        int opcion;
        
        do{
        System.out.println("=======MENÚ=======");
        System.out.println("Seleccione una Opción: ");
        System.out.println("1. Ingreso de Alumnos");
        System.out.println("2. Mostrar Alumnos Inscritos");
        System.out.println("3. Generar Curso");
        
        opcion = Integer.parseInt(entrada.nextLine());
        
        switch(opcion){
            case 1:
              agregarEstudiante();
              break;
            case 2:
               mostrarEstudiante(listaGlobal);
               break;
            case 3:
                Curso curso = GenerarCurso();
                curso.poblarCurso(listaGlobal);
                curso.generarLista();
                break;
            case 4:
                System.out.println("Guardando datos...");
                GestorArchivos.guardarEstudiantes(listaGlobal);
                System.out.println("Saliendo...");
                break;
            default:
                System.out.println("Ingresa un valor valido!!");
                
        }     
      }while(opcion !=4);
    }  
}
