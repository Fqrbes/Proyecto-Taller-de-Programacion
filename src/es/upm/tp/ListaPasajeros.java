package es.upm.tp;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Objects;
import java.util.Scanner;

/**
 * Description of the class
 *
 * @author      Cesar Jimenez Laguna
 * @author      Iñaki Ramos Iturria
 * @version     1.0
 */
public class ListaPasajeros {
    //Atributos privados, todos


    /**
     * Atributo que devuelve la capacidad de la lista de pasajeros
     */
    private int capacidad;


    /**
     * Atributo que contiene la ocupacion de la lista de pasajeros
     */
    private int ocupacion;


    /**
     * Atributo que contiene el vector donde estan los pasajeros
     */
    private Pasajero[] listaPasajeros;

    /**
     * Constructor de la clase, crea un vector que contiene la cantidad de pasajeros recibidos que van a abordar
     * sus respectivos aviones
     *
     * @param capacidad especifica la capacidad de la lista de pasajeros
     */
    public ListaPasajeros(int capacidad){
        this.capacidad = capacidad;
        listaPasajeros = new Pasajero[capacidad];
    }

    /**
     * @return Devuelve la cantidad de pasajeros que hay en ListaPasajeros
     */
    public int getOcupacion(){
        return ocupacion;
    }

    /** Devuelve verdadero si la listaPasajeros esta llena, si no, devuelve falso
     * @return especifica verdadero o falso segun como este listaPasajeros
     */
    public boolean estaLlena(){
        boolean estaLlena = false;
        if (ocupacion == listaPasajeros.length){
            estaLlena = true;
        }
        return estaLlena;
    }

    /** Devuelve el pasajero que se encuentre en la posicion recibida por el parametro
     * @param i especifica la posicion del pasajero en la lista
     * @return devuelve el pasajero pedido por parametro
     */
    public Pasajero getPasajero(int i){
        return listaPasajeros[i];
    }

    /** Inserta un pasajero en el array ListaPasajeros
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
     * @param dni
     * @return
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
     * @param email
     * @return
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

    /** Selecciona el pasajero, si existe, del dni que pasa el usuario. A su vez comprueba si este es correcto y no fue utilizado por otros
     * @param teclado el usuario introduce el dni del pasajero que desea
     * @param mensaje mensaje que se muestra por pantalla
     * @return devuelve el pasajero seeleccionado si cumple los requisitos (que exista  y tenga alcance suficiente)
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
                System.out.println("DNI incorrecto.");
            }
        }while (buscarPasajeroDNI(DNI) == null);
        return buscarPasajeroDNI(DNI);
    }

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