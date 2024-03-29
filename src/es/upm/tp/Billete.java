package es.upm.tp;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

/**
 * Billete es una clase que encapsula las variables enteras usadas para definir un billete concreto
 *
 * @author      Cesar Jimenez Laguna
 * @author      Iñaki Ramos Iturria
 * @version     1.0
 */
public class Billete {

    /**
     * Atributo que contiene el enumerador TIPO, con los distintos tipos de billetes para comprar por los pasajeros
     */
    enum TIPO { TURISTA, PREFERENTE, PRIMERA }

    /**
     * Atributo que contiene el localizador de un billete
     */
    private String localizador;

    /**
     * Atributo que contiene el vuelo de donde se ha comprado un billete
     */
    private Vuelo vuelo;

    /**
     * Atributo que contiene el pasajero que compro un billete
     */
    private Pasajero pasajero;

    /**
     * Atributo que contiene el tipo de billete comprado (TURISTA, PREFERENTE, PRIMERA)
     */
    private TIPO tipo;

    /**
     * Atributo que contiene la fila del billete comprado
     */
    private int fila;

    /**
     * Atributo que contiene la columna del billete comprado
     */
    private int columna;

    /**
     * Atributo que contiene el precio del billete comprado
     */
    private double precio;

    /**
     * Atributo que contiene el la ocupacion del billete comprado
     */
    private int ocupacion;

    /**
     * Atributo que contiene el array donde se guardan los billetes (comprados o reservados) de un vuelo
     */
    private ListaBilletes listaBilletesVuelo;

    /**
     * Constructor of the class
     *
     * @param localizador
     * @param vuelo
     * @param pasajero
     * @param tipo
     * @param fila
     * @param columna
     * @param precio
     */
    public Billete(String localizador, Vuelo vuelo, Pasajero pasajero, TIPO tipo, int fila, int columna, double precio) {
        this.localizador = localizador;
        this.vuelo = vuelo;
        this.pasajero = pasajero;
        this.tipo = tipo;
        this.fila = fila;
        this.columna = columna;
        this.precio = precio;
        ocupacion = 0;
        listaBilletesVuelo = new ListaBilletes(vuelo.getAvion().getFilas() * vuelo.getAvion().getColumnas());
    }

    /**
     * Getter del atributo localizador
     * @return Devuelve el localizador de un billete
     */
    public String getLocalizador(){
        return localizador;
    }

    /**
     * Getter del atributo vuelo
     * @return Devuelve el vuelo del billete comprado
     */
    public Vuelo getVuelo(){
        return vuelo;
    }

    /**
     * Getter del atributo pasajero
     * @return Devuelve el pasajero que compro el billete
     */
    public Pasajero getPasajero(){
        return pasajero;
    }

    /**
     * Getter del atributo tipo
     * @return Devuelve el tipo de billete comprado
     */
    public TIPO getTipo(){
        return tipo;
    }

    /**
     * Getter del atributo fil
     * @return Devuelve la fila del asiento del billete comprado
     */
    public int getFila(){
        return fila;
    }

    /**
     * Getter del atributo columna
     * @return Devuelve la columna del asiento del billete comprado
     */
    public int getColumna(){
        return columna;
    }

    /**
     * Getter del para conseguir un asiento dependiendo de una fila y una columna
     * @return Devuelve el asiento de un pasajero dependiendo de la fila y la columna seleccionada
     */
    // Ejemplos: "1A" para el asiento con fila 1 y columna 1, "3D" para el asiento con fila 3 y columna 4
    public String getAsiento(){
        char [] columna = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I' , 'J', 'K', 'L' ,'M', 'N', 'Ñ', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
        int numcolumna = getColumna();
        return getFila() + "" + columna[numcolumna-1];
        //String asiento = getFila() + "" + columna[numcolumna-1];
    }

    /**
     * Getter del atributo precio
     * @return Devuelve el precio de un billete (dependiedo del tipo del mismo comprado)
     */
    public double getPrecio(){
        if (tipo == TIPO.PRIMERA){
            precio = precio * 1.5;
        }
        if (tipo == TIPO.PREFERENTE){
            precio = precio * 1.25;
        }
        return precio;
    }

    /**
     * Funcion que muestra la informacion completa del billete de una forma especifica segun el enunciado
     * @return Devuelve un String con toda la informacion del billete
     */
    //                                                :
    // Texto que debe generar: Billete PM1111AAAA para Vuelo PM1111 de MAD T4 (24/12/2022 12:35:00) a BCN T1 (24/12/2022 14:05:30) en asiento 6C (TURISTA) por 100.00€
    public String toString(){
        return "Billete " + localizador + " para Vuelo " + vuelo.getID() + " de " + vuelo.getOrigen().getCodigo() + " T" + vuelo.getTerminalOrigen() + " (" + vuelo.getSalida().toString() + ") a "
                + vuelo.getDestino().getCodigo() + " T" + vuelo.getTerminalDestino() + " (" + vuelo.getLlegada().toString() + ") en asiento " +
                getAsiento() + " (" + getTipo() +") por " + String.format("%.2f", getPrecio()).replace(".",",") + "€";
    }

    /**
     * Funcion que cancela el billete y lo elimina de la lista y del pasajero hay que correspondia
     * @return Devuelve True si se ha podido cancelar y false si no se ha podido
     */
    // Cancela este billete, eliminandolo de la lista de billetes del vuelo y del pasajero correspondiente
    public boolean cancelar(){
        boolean cancelar = true;
        if (!getVuelo().desocuparAsiento(localizador)){
            cancelar = false;
        }
        if (!getPasajero().cancelarBillete(localizador)){
            cancelar = false;
        }
        if (cancelar){
            System.out.println("Billete " + localizador + " cancelado.");
        }
        return cancelar;
    }


    /**
     * Funcion que imprime la informacion del billete en un fichero siguiendo los ejemplos del enunciado
     * @param fichero Fichero en el que se escribe la informacion del billete
     * @return Devuelve True si se ha escrito en el fichero y false si no se ha podido
     */
    // Imprime la informacion de este billete en un fichero siguiendo el formato de los ejemplos de ejecución del enunciado
    public boolean generarFactura(String fichero) {
        FileWriter fileWriter = null;
        boolean facturaGenerada = true;
        try {
            fileWriter = new FileWriter(fichero, false);
            fileWriter.write("--------------------------------------------------\n");
            fileWriter.write("--------- Factura del billete " + this.localizador + " ---------\n");
            fileWriter.write("--------------------------------------------------\n");
            fileWriter.write("Vuelo " + this.getVuelo().getID() + "\n");
            fileWriter.write("Origen " + this.getVuelo().getOrigen() + " (" + this.getVuelo().getOrigen().getCodigo() + ") T" + this.getVuelo().getTerminalOrigen() + "\n");
            fileWriter.write("Destino " + this.getVuelo().getOrigen() + " (" + this.getVuelo().getDestino().getCodigo() + ") T" + this.getVuelo().getTerminalDestino() + "\n");
            fileWriter.write("Salida " + this.getVuelo().getSalida().toString() + "\n");
            fileWriter.write("Llegada " + this.getVuelo().getLlegada().toString() + "\n");
            fileWriter.write("Pasajero " + this.getPasajero().toString() + "\n");
            fileWriter.write("Tipo de billete: " + this.getTipo().name() + "\n"); //el name está bien puesto?
            fileWriter.write("Asiento " + this.getAsiento() + "\n");
            fileWriter.write("Precio " + this.getPrecio() + "€");
        }catch (FileNotFoundException excepcion1){
            System.out.println("El fichero " + fichero + " no encontrado");
            facturaGenerada = false;
        }
        catch (IOException excepcion2){
            System.out.println("Error de escritura en fichero " + fichero + ".");
            facturaGenerada = false;
        }
        finally {
            if (fileWriter != null){
                try{
                    fileWriter.close();
                }catch (IOException excepcion3){
                    System.out.println("Error de cierre del fichero " + fichero + ".");
                    facturaGenerada = false;
                }
            }
        }
        if (facturaGenerada){
            System.out.println("Factura de Billete " + localizador + " generada en factura.csv");
        }
        return facturaGenerada;
    }

    // Métodos estáticos

    /**
     * Funcion que genera un localizador para un billete concreto
     * @param rand Objeto para que el localizador generado sea aleatorio
     * @param idVuelo ID del vuelo al que esta asociado el billete
     * @return Devuelve el localizador del billete en el que se encuentra el ID del vuelo
     */
    // Genera un localizador de billete. Este consistirá en una cadena de 10 caracteres, de los cuales los seis
    // primeros será el ID del vuelo asociado y los 4 siguientes serán letras mayúsculas aleatorias. Ejemplo: PM0123ABCD
    // NOTA: Usar el objeto rand pasado como argumento para la parte aleatoria.
    public static String generarLocalizador(Random rand, String idVuelo){
        Character posicion1 = (char) ('A' + rand.nextInt(26));
        Character posicion2 = (char) ('A' + rand.nextInt(26));
        Character posicion3 = (char) ('A' + rand.nextInt(26));
        Character posicion4 = (char) ('A' + rand.nextInt(26));
        return idVuelo + posicion1 + posicion2 + posicion3 + posicion4;
    }

    /**
     * Funcion que crea un nuevo billete para un vuelo y pasajero especifico pidiendo por teclado los datos necesarios al usuario como el vuelo y el pasajero
     * @param teclado Teclado por donde pasa el usuario la informacion requerida
     * @param rand Parametro para generar un localizador aleatorio del billete
     * @param vuelo Vuelo del cual se ha comprado el billete
     * @param pasajero Pasajero que comprar el billete y al que esta asociado este
     * @return Devuelve el billete que se ha creado con los datos pasados por parametro
     */
    // Crea un nuevo billete para un vuelo y pasajero específico, pidiendo por teclado los datos necesarios al usuario en el orden y
    // con los textos indicados en los ejemplos de ejecución del enunciado
    // La función solicita repetidamente los parametros hasta que sean correctos
    public static Billete altaBillete(Scanner teclado, Random rand, Vuelo vuelo, Pasajero pasajero){
        vuelo.imprimirMatrizAsientos();
        System.out.println("Tipo de asiento: '[ ]' = TURISTA, '{ }' = PREFERENTE, '( )' = PRIMERA");
        String asiento;
        char columna;
        int numeroFila;
        char letraColumna;
        double precioBilletes;
        do {
            numeroFila = Utilidades.leerNumero(teclado, "Ingrese fila del asiento (1-" + vuelo.getAvion().getFilas() + "):", 1, vuelo.getAvion().getFilas());
            columna = (char) (vuelo.getAvion().getColumnas() + 'A' - 1);
            letraColumna = Utilidades.leerLetra(teclado, "Ingrese columna del asiento (A-" + columna + "):", 'A',columna);
            asiento = String.valueOf(numeroFila) + letraColumna; //String.valueOf(letraColumna)

            if (vuelo.asientoOcupado(numeroFila, Integer.parseInt(String.valueOf((char)letraColumna - 64))))
                System.out.println("El asiento " +  asiento + " ya está reservado.");

        }while(vuelo.asientoOcupado(numeroFila, Integer.parseInt(String.valueOf((char)letraColumna - 64))));
        TIPO tipo;
        switch (numeroFila){
            case 1:
                tipo = TIPO.PRIMERA; precioBilletes = vuelo.getPrecioPrimera();
                break;
            case 2,3,4,5:
                tipo = TIPO.PREFERENTE; precioBilletes = vuelo.getPrecioPreferente();
                break;
            default: tipo = TIPO.TURISTA; precioBilletes = vuelo.getPrecio();
        }
        Billete nuevoBillete = new Billete(generarLocalizador(rand, vuelo.getID()), vuelo, pasajero, tipo, numeroFila, Integer.parseInt(String.valueOf((char)columna - 66)), precioBilletes);
        pasajero.aniadirBillete(nuevoBillete);
        return nuevoBillete;
    }
}