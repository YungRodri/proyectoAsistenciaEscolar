/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AsistenciaCurso;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static ArrayList<Estudiante> listaGlobal = new ArrayList<>();
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
        listaGlobal.add(alumno);
        
        System.out.println("¿Continuar? (S/N): ");
        opcion = entrada.nextLine();
        }while(opcion.equalsIgnoreCase("S"));
        System.out.println("Ingreso Finalizado");
        
    }
    
    public static void mostrarEstudiante(ArrayList<Estudiante> listaGlobal){
        
        System.out.println("-------Lista de Estudiantes------");
        
        for(int i=0;i<listaGlobal.size();i++){
            
            Estudiante alumno = listaGlobal.get(i);
            System.out.println("Estudiante #"+ (i+1));
            System.out.println(alumno.getNombre() + " " + alumno.getApellidoP() + " " + alumno.getApellidoM());
            System.out.println("Curso: "+ alumno.getCurso());
            System.out.println("===============");
            
        }
    }
    
    public static void main(String[] args){
        int opcion;
        System.out.println("=======MENÚ=======");
        System.out.println("Seleccione una Opción: ");
        System.out.println("1. Ingreso de Alumnos");
        System.out.println("2. Mostrar Alumnos Inscritos");
        
        opcion = Integer.parseInt(entrada.nextLine());
        
        if(opcion == 1){
            agregarEstudiante();
        }else if(opcion == 2){
            mostrarEstudiante(listaGlobal);
        }else{
            System.out.println("Ingrese una Opcion valida");
        }
        
        
        
        
    }
}
