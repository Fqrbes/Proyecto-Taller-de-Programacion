package es.upm.tp;

import javax.swing.*;
import java.io.*;
import java.util.Objects;
import java.util.Scanner;

/**
 * Description of the class
 *
 * @author      Cesar Jimenez Laguna
 * @author      Iñaki Ramos Iturria
 * @version     1.0
 */
public class ListaAviones {

    /**
     * Atributo que devuelve la capacidad del avion
     */
    private int capacidad;

    /**
     * Atributo que devuelve la ocupacion del avion
     */
    private int ocupacion;

    /**
     * Atributo que contiene el vector donde estan los aviones
     */
    private Avion[] ListaAviones;

    /**
     * Constructor of the class. Constructor que crea un vector con la cantidad de aviones recibidos
     * crea un array con la capacidad de los aviones
     * @param capacidad especifica la capacidad del avion
     */
    public ListaAviones(int capacidad){
        this.capacidad = capacidad;
        ListaAviones = new Avion[capacidad];
    }

    /** Devuelve la cantidad de aviones que hay en ListaAviones
     * @return especifica la ocupacion
     */
    public int getOcupacion(){
        return ocupacion;
    }

    /** Devuelve verdadero si la lista aviones esta llena, si no, devuelve falso
     *  @return especifica verdadero o falso segun como este ListaAviones
     */
    public boolean estaLlena(){
        boolean estaLlena;
        if (ocupacion == ListaAviones.length){
            estaLlena = true;
        }else{
            estaLlena = false;
        }
        return estaLlena;
    }

    /** Devuelve el avion que se encuentre en la posicion recibida por el parametro
     * @param posicion especifica la posicion del avion requerido
     * @return devuelve la posicion del avion pedido por parametro
     */
    public Avion getAvion(int posicion){
        return ListaAviones[posicion];
    }

    /** Inserta un avion en el array ListaAviones
     * @param avion avion que se quiere insertar en la lista
     * @return devuelve true si se ha insertado el avion o false si no se ha añadido
     */
    public boolean insertarAvion(Avion avion) {
        boolean insertar = false;
        if (!estaLlena()){
            ListaAviones[ocupacion] = avion;
            ocupacion ++;
            insertar = true;
        }
        else {
            insertar = false;
        }
        return insertar;
    }

    /** Busca un avion por medio de su matricula
     * @param matricula codigo que identifica al avion
     * @return devuelve el avion, introducido por parametro
     */
    public Avion buscarAvion(String matricula){
        Avion resultado = null;
        for (int i = 0; i < ocupacion; i ++){
            if (Objects.equals(ListaAviones[i].getMatricula(), matricula)){
                resultado = ListaAviones[i];
            }
        }
        return resultado;
    }

    /** Selecciona el avion, si existe, de la matricula que pasa el usuario. Y comprueba si tiene alcance suficiente para el vuelo
     * @param teclado el usuario introduce la matricula del avion que desea
     * @param mensaje mensaje que se muestra por pantalla
     * @param alcance alcance que tiene el avion seleccionado
     * @return devuelve el avion seeleccionado si cumple los requisitos (que exista  y tenga alcance suficiente)
     */
    // Permite seleccionar un avión existente a partir de su matrícula, y comprueba si dispone de un alcance mayor o igual que el pasado como argumento,
    // usando el mensaje pasado como argumento para la solicitud y siguiendo el orden y los textos mostrados en el enunciado
    // La función solicita repetidamente la matrícula del avión hasta que se introduzca uno con alcance suficiente
    public Avion seleccionarAvion(Scanner teclado, String mensaje, double alcance){
        String matricula;
        boolean alcanceSuficiente = false;
        Avion avion = null;
        do {
            System.out.print("Ingrese matrícula de Avión:");
            matricula = teclado.nextLine();
           avion = buscarAvion(matricula);
            if (buscarAvion(matricula) == null){
                System.out.println("Matricula de avion no encontrado.");
            }
            else if (avion.getAlcance() < alcance){
                System.out.printf("Avión seleccionado con alcance insuficiente (menor que %.3f km).\n", alcance);
            }
            else {
                alcanceSuficiente = true;
            }
        }while (buscarAvion(matricula) == null || !alcanceSuficiente);
        return avion;
    }

    /** Crea una lista con los aviones y cada uno de sus datos
     * @param nombre es el nombre del fichero
     * @return devuelve true si se ha copiado en el fichero y false si no se ha podido
     */
    // Genera un fichero CSV con la lista de aviones, sobreescribiendolo
    public boolean escribirAvionesCsv(String nombre){
        PrintWriter printW = null;
        boolean copiado= true;
        try{
            printW = new PrintWriter(nombre);
            for (int i = 0; i < ocupacion; i ++){
                printW.println(ListaAviones[i].getMarca() + ";" + ListaAviones[i].getModelo() + ";" + ListaAviones[i].getMatricula()
                        + ";" + ListaAviones[i].getFilas() + ";" + ListaAviones[i].getColumnas() + ";" + ListaAviones[i].getAlcance());
            }
        } catch (FileNotFoundException e) {
            System.out.println("El fichero " + nombre + " no encontrado");
            copiado = false;
        }finally{
            if (printW != null){
                printW.close();
            }
        }
        return copiado;
    }

    /** Crea una lista con la funcion escribirAvionesCsv, tomando los datos del fichero Csv
     * @param fichero fichero donde se guardan las listas de los aviones seleccionados
     * @param capacidad es la capacidad de los aviones al los que se hace referencia
     * @return devuelve la listaImportada desde el fichero CSV
     */
    //Métodos estáticos
    // Genera una lista de aviones a partir del fichero CSV, usando el argumento como   
    // capacidad máxima de la lista
    public static ListaAviones leerAvionesCsv(String fichero, int capacidad){
        ListaAviones ListaAvionesCsv = new ListaAviones(capacidad);
        Scanner teclado = null;
        String entrada;
        try{
            teclado = new Scanner(new FileReader(fichero));
            String marca;
            String modelo;
            String matricula;
            int columnas;
            int filas;
            double alcance;
            do{
                entrada = teclado.nextLine();
                String [] matrizAvion = entrada.split(";");
                marca = matrizAvion[0];
                modelo = matrizAvion[1];
                matricula = matrizAvion[2];
                columnas = Integer.parseInt(matrizAvion[3]);
                filas = Integer.parseInt(matrizAvion[4]);
                alcance = Double.parseDouble(matrizAvion[5]);
                ListaAvionesCsv.insertarAvion(new Avion(marca, modelo, matricula, columnas, filas, alcance));
            }while(teclado.hasNext());
        }catch (FileNotFoundException e){
            System.out.println("El fichero " + fichero + " no se ha encontrado");
        }finally {
            if (teclado != null){
                teclado.hashCode();
            }
        }
        return ListaAvionesCsv;
    }
}