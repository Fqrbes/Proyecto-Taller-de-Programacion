package es.upm.tp;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Objects;
import java.util.Scanner;

/**
 * ListaPasajeros es una clase que encapsula las variables enteras usadas para definir los pasajeros,
 * así como también contiene funciones bara buscar, seleccionar e insertar estos mismos pasajeros en el array
 * de nombre listaPasajeros
 * Tambien escribe un fichero.csv con los datos de cada pasajero
 *
 * @author      Cesar Jimenez Laguna
 * @author      Iñaki Ramos Iturria
 * @version     1.0
 */
public class ListaPasajeros {

    /**
     * Atributo que devuelve la capacidad de la ListaPasajeros
     */
    private int capacidad;

    /**
     * Atributo que contiene la ocupacion de los pasajeros dentro de la lista
     */
    private int ocupacion;

    /**
     * Atributo que contiene el array donde se guardan los pasajeros
     */
    private Pasajero[] listaPasajeros;

    /**
     * Constructor de la clase, crea un vector que contiene la cantidad de pasajeros
     * @param capacidad especifica la capacidad de la lista de pasajeros
     */
    public ListaPasajeros(int capacidad){
        this.capacidad = capacidad;
        listaPasajeros = new Pasajero[capacidad];
    }

    /**
     * Getter del atributo ocupacion
     * @return Devuelve la cantidad de pasajeros que hay en la listaPasajeros como una variable ocupacion
     */
    public int getOcupacion(){
        return ocupacion;
    }

    /**
     * Devuelve verdadero si la listaPasajeros esta llena, si no, devuelve falso
     * @return estaLlena
     */
    public boolean estaLlena(){
        boolean estaLlena = false;
        if (ocupacion == listaPasajeros.length){
            estaLlena = true;
        }
        return estaLlena;
    }

    /**
     * Getter para conseguir al pasajero que se encuentra en la posicion i del array listaPasajeros
     * @param i especifica la posicion del pasajero dentro del array
     * @return Devuelve el pasajero que se encuentra en la posicion recibida por el parametro
     */
    public Pasajero getPasajero(int i){
        return listaPasajeros[i];
    }

    /**
     * Inserta un pasajero en el array ListaPasajeros
     * @param pasajero Es el pasajero que se quiere insertar en la lista
     * @return devuelve true si se ha insertado el pasajero o false si no se ha añadido
     */
    public boolean insertarPasajero(Pasajero pasajero){
        boolean insertar = false;
        if (!estaLlena()){
            listaPasajeros[ocupacion] = pasajero;
            ocupacion ++;
            insertar = true;
        }
        return insertar;
    }

    /**
     * Funcion que busca a un pasajero por medio de su DNI pasado por parametro
     * @param dni DNI que esta asociado a un pasajero
     * @return Devuelve el pasajero que coincide con el DNI
     */
    public Pasajero buscarPasajeroDNI(String dni){
        Pasajero resultado = null;
        boolean encontrado = false; //IMPORTANTE
        for (int i = 0; i < ocupacion && !encontrado; i++){
            if (listaPasajeros[i].getDNI().equalsIgnoreCase(dni)){
                resultado = listaPasajeros[i];
                encontrado = true;
            }
        }
        return resultado;
    }

    /**
     * Funcion que busca a un pasajero por medio de su email pasado por parametro
     * @param email email del pasajero
     * @return Devuelve el pasajero que coincide con el email pasado por parametro
     */
    public Pasajero buscarPasajeroEmail(String email){
        Pasajero resultado = null;
        for (int i = 0; i < ocupacion; i ++){
            if (Objects.equals(listaPasajeros[i].getEmail(), email)){
                resultado = listaPasajeros[i];
            }
        }
        return resultado;
    }

    /** Selecciona el pasajero, si existe, del dni que pasa el usuario
     * @param teclado el usuario introduce el dni del pasajero que desea
     * @param mensaje mensaje que se muestra por pantalla
     * @return devuelve el pasajero seeleccionado que coincide con el DNI
     */
    // Permite seleccionar un pasajero existente a partir de su DNI, usando el mensaje pasado como argumento para la solicitud
    // y siguiendo el orden y los textos mostrados en el enunciado
    // La función solicita repetidamente hasta que se introduzca un DNI correcto
    public Pasajero seleccionarPasajero(Scanner teclado, String mensaje){
        String DNI;
        do {
            System.out.print(mensaje);
            DNI = teclado.nextLine();
            if (buscarPasajeroDNI(DNI) == null){
                System.out.println("DNI no encontrado.");
                //System.out.println("DNI incorrecto.");
            }
        }while (buscarPasajeroDNI(DNI) == null);
        return buscarPasajeroDNI(DNI);
    }

    /**
     * Genera un fichero CSV con la lista de pasajeros
     * @param fichero Fichero sobre el que se sobreescriben los datos de los pasajeros
     * @return Devuelve True si se ha sobreescrito el fichero y false si no se ha podido
     */
    // Genera un fichero CSV con la lista de pasajeros, sobreescribiendolo
    public boolean escribirPasajerosCsv(String fichero){
        PrintWriter printW = null;
        boolean copiado= true;
        try{
            printW = new PrintWriter(fichero);
            for (int i = 0; i < ocupacion; i ++){
                printW.println(listaPasajeros[i].getNombre() + ";" + listaPasajeros[i].getApellidos() + ";"
                        + listaPasajeros[i].getNumeroDNI() + ";" + listaPasajeros[i].getLetraDNI() + ";" + listaPasajeros[i].getEmail());
            }
        } catch (FileNotFoundException e) {
            System.out.println("El fichero " + fichero + " no encontrado");
            copiado = false;
        }finally{
            if (printW != null){
                printW.close();
            }
        }
        return copiado;
    }


    /**
     * Funcion que genera una lista con los pasajeros, tomando los datos del fichero Csv
     * @param fichero fichero donde lee los datos de los vuelos
     * @param capacidad capacidad del array ListaPasajeros
     * @param maxBilletesPasajeros cantidad maxima de billetes posibles por pasajeros en un vuelo
     * @return devuelve la lista que ha creado
     */

    //Métodos estáticos
    // Genera una lista de pasajeros a partir del fichero CSV, usando los límites especificados como argumentos para la capacidad
    // de la lista y el número de billetes máximo por pasajero
    public static ListaPasajeros leerPasajerosCsv(String fichero, int capacidad, int maxBilletesPasajeros){
        ListaPasajeros ListaPasajerosCsv = new ListaPasajeros(capacidad);
        Scanner teclado = null;
        String entrada;
        try{
            teclado = new Scanner(new FileReader(fichero));
            String nombre;
            String appellidos;
            String email;
            char letraDNI;
            long numeroDNI;
            while(teclado.hasNext()){
                entrada = teclado.nextLine();
                String[] matrizPasajeros = entrada.split(";");
                nombre = matrizPasajeros[0];
                appellidos = matrizPasajeros[1];
                numeroDNI = Long.parseLong(matrizPasajeros[2]);
                letraDNI = matrizPasajeros[3].charAt(0);
                email = matrizPasajeros[4];
                Pasajero pasajeroCreado = new Pasajero(nombre, appellidos, numeroDNI, letraDNI, email, maxBilletesPasajeros);
                ListaPasajerosCsv.insertarPasajero(pasajeroCreado);
            }
        }catch (FileNotFoundException e){
            System.out.println("El fichero " + fichero + " no se ha encontrado");
        }finally {
            if (teclado != null){
                teclado.close();
            }
        }
        return ListaPasajerosCsv;
    }
}