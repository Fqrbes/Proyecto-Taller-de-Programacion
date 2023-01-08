package es.upm.tp;

import java.io.*;
import java.util.Objects;
import java.util.Scanner;

/**
 * ListaAviones es una clase que encapsula las variables enteras usadas para definir los aviones,
 * así como también contiene funciones bara buscar, seleccionar e insertar aviones en el array de nombre ListaAviones
 * Tambien escribe un fichero.csv con los datos de cada avion
 *
 * @author      Cesar Jimenez Laguna
 * @author      Iñaki Ramos Iturria
 * @version     1.0
 */
public class ListaAviones {

    /**
     * Atributo que devuelve la capacidad de la ListaAvion
     */
    private int capacidad;

    /**
     * Atributo que devuelve la ocupacion del avion dentro de la lista
     */
    private int ocupacion;

    /**
     * Atributo que contiene el array donde estan los aviones
     */
    private Avion[] ListaAviones;

    /**
     * Constructor que crea un array con la cantidad de aviones recibidos
     * @param capacidad especifica la capacidad de la lista que contiene los aviones
     */
    public ListaAviones(int capacidad){
        this.capacidad = capacidad;
        ListaAviones = new Avion[capacidad];
    }

    /**
     * Getter del atributo ocupacion
     * @return devuelve la cantidad de aviones que hay en el array ListaAviones como variable ocupacion
     */
    public int getOcupacion(){
        return ocupacion;
    }

    /**
     * Devuelve verdadero si la lista aviones esta llena, si no, devuelve falso
     *  @return estaLlena
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

    /**
     * Getter  para conseguir un avion
     * @return Devuelve la posicion de un avion dentro del array ListaAviones
     */
    public Avion getAvion(int posicion){
        return ListaAviones[posicion];
    }

    /**
     * Inserta un avion en el array ListaAviones
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

    /**
     * Busca un avion por medio de su matricula
     * @param matricula codigo que identifica a un avion
     * @return devuelve el avion correspondiente a la matricula introducida por parametro
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

    /**
     * Selecciona el avion, si existe, de la matricula que se pasa por parametro. Ademas, comprueba si tiene alcance suficiente para el vuelo requerido
     * @param teclado el usuario introduce la matricula del avion que desea
     * @param mensaje mensaje que se muestra por pantalla
     * @param alcance alcance maximo que tiene el avion seleccionado
     * @return devuelve el avion seleccionado si cumple los requisitos (que exista y tenga alcance suficiente)
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
                System.out.printf(String.format("Avión seleccionado con alcance insuficiente (menor que %.3f km).\n", alcance).replace(',', '.'));
            }
            else {
                alcanceSuficiente = true;
            }
        }while (buscarAvion(matricula) == null || !alcanceSuficiente);
        return avion;
    }

    /**
     * Escribe en un fichero los aviones con sus caracteristicas
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

    /**
     * Crea una lista con la funcion escribirAvionesCsv, tomando los datos del fichero Csv
     * @param fichero fichero donde se guardan los aviones con sus caracteristicas
     * @param capacidad es la capacidad de la lista de aviones a la que se hace referencia
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
                filas = Integer.parseInt(matrizAvion[3]);
                columnas = Integer.parseInt(matrizAvion[4]);
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