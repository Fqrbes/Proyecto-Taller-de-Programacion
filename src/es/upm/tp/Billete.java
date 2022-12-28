package es.upm.tp;

import com.sun.org.apache.bcel.internal.generic.SWITCH;

import java.util.Random;
import java.util.Scanner;

/**
 * Description of the class
 *
 * @author      Cesar Jimenez Laguna
 * @author      Iñaki Ramos Iturria
 * @version     1.0
 */
public class Billete {
    enum TIPO { TURISTA, PREFERENTE, PRIMERA }
    private String localizador;
    private Vuelo vuelo;
    private Pasajero pasajero;
    private TIPO tipo;
    private int fila;
    private int columna;
    private double precio;
    private int ocupacion;
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
        this.ocupacion = 0;
    }

    public String getLocalizador(){
        return localizador;
    }

    public Vuelo getVuelo(){
        return vuelo;
    }

    public Pasajero getPasajero(){
        return pasajero;
    }

    public TIPO getTipo(){
        return tipo;
    }

    public int getFila(){
        return fila;
    }

    public int getColumna(){
        return columna;
    }

    // Ejemplos: "1A" para el asiento con fila 1 y columna 1, "3D" para el asiento con fila 3 y columna 4
    public String getAsiento(){
        char [] columna = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I' , 'J', 'K', 'L' ,'M', 'N', 'Ñ', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
        int numcolumna = getColumna();
        return getFila() + "" + columna[numcolumna-1];
        //String asiento = getFila() + "" + columna[numcolumna-1];
    }

    public double getPrecio(){
        if (tipo == TIPO.PRIMERA){
            precio = precio * 1.5;
        }
        if (tipo == TIPO.PREFERENTE){
            precio = precio * 1.25;
        }
        return precio;
    }
    //                                                :
    // Texto que debe generar: Billete PM1111AAAA para Vuelo PM1111 de MAD T4 (24/12/2022 12:35:00) a BCN T1 (24/12/2022 14:05:30) en asiento 6C (TURISTA) por 100.00€
    public String toString(){
        return "Billete " + localizador + " para Vuelo " + vuelo.getID() + " de " + vuelo.getOrigen().getCodigo() + vuelo.getTerminalOrigen() + " (" + vuelo.getSalida() + ") a "
                + vuelo.getDestino().getCodigo() + vuelo.getTerminalDestino() + " (" + vuelo.getLlegada() + ") en asiento " + getAsiento() + " (" + getTipo() +") por " + getPrecio() + "€";
    }

    // Cancela este billete, eliminandolo de la lista de billetes del vuelo y del pasajero correspondiente
    public boolean cancelar(){
        boolean cancelar = false;

        return cancelar;
    }

    // Imprime la informacion de este billete en un fichero siguiendo el formato de los ejemplos de ejecución del enunciado
    public boolean generarFactura(String fichero) {
        return false;
    }

    // Métodos estáticos

    // Genera un localizador de billete. Este consistirá en una cadena de 10 caracteres, de los cuales los seis 
    // primeros será el ID del vuelo asociado y los 4 siguientes serán letras mayúsculas aleatorias. Ejemplo: PM0123ABCD
    // NOTA: Usar el objeto rand pasado como argumento para la parte aleatoria.  
    /*public static String generarLocalizador(Random rand, String idVuelo){
        String localizador1 = String.valueOf((char)rand.nextInt('A', 'Z'));
        String localizador2 = String.valueOf((char)rand.nextInt('A', 'Z'));
        String localizador3 = String.valueOf((char)rand.nextInt('A', 'Z'));
        String localizador4 = String.valueOf((char)rand.nextInt('A', 'Z'));
        return idVuelo + localizador1 + localizador2 + localizador3 + localizador4;
    }*/

    // Crea un nuevo billete para un vuelo y pasajero específico, pidiendo por teclado los datos necesarios al usuario en el orden y 
    // con los textos indicados en los ejemplos de ejecución del enunciado
    // La función solicita repetidamente los parametros hasta que sean correctos
    public static Billete altaBillete(Scanner teclado, Random rand, Vuelo vuelo, Pasajero pasajero){
        vuelo.imprimirMatrizAsientos();
        System.out.println("Tipo de asiento: '[ ]' = TURISTA, '{ }' = PREFERENTE, '( )' = PRIMERA");
        String asiento;
        char columna;
        int numeroFila;
        double precioBilletes;
        do {
            numeroFila = Utilidades.leerNumero(teclado, "Ingrese fila del asiento (1-" + vuelo.getAvion().getFilas() + "):", 1, vuelo.getAvion().getFilas());
            columna = (char) (vuelo.getAvion().getColumnas() + 'A' - 1);
            char letraColumna = Utilidades.leerLetra(teclado, "Ingrese columna del asiento (A-" + columna + "):", 'A',columna);
            asiento = String.valueOf(numeroFila + String.valueOf(letraColumna));
            if (vuelo.asientoOcupado(numeroFila, columna))
                System.out.println("El asiento " +  asiento + " ya esta reservado.");
        }while(vuelo.asientoOcupado(numeroFila, columna));
        String tipo;
    return null;
    }
}