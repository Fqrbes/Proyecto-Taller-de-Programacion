package es.upm.tp;

import java.util.Random;
import java.util.Scanner;

/**
 * Description of the class
 *
 * @author      Cesar Gimenez Laguna
 * @author      Iñaki Ramos Iturria
 * @version     1.0
 */
public class Vuelo {
    private boolean[][] asientos;
    private String id;
    private Avion avion;
    private Aeropuerto origen;
    private int terminalOrigen;
    private Fecha salida;
    private Aeropuerto destino;
    private int terminalDestino;
    private Fecha llegada;
    private double precio;

    /**
     * Constructor of the class
     *
     * @param id
     * @param avion
     * @param origen
     * @param terminalOrigen
     * @param salida
     * @param destino
     * @param terminalDestino
     * @param llegada
     * @param precio
     */
    public Vuelo(String id, Avion avion, Aeropuerto origen, int terminalOrigen, Fecha salida, Aeropuerto destino, int terminalDestino, Fecha llegada, double precio){
        this.id = id;
        this.avion = avion;
        this.origen = origen;
        this.terminalOrigen = terminalOrigen;
        this.salida = salida;
        this.destino = destino;
        this.terminalDestino = terminalDestino;
        this.llegada = llegada;
        this.precio = precio;

        asientos = new boolean[avion.getFilas()][avion.getColumnas()];
        for (int i = 0; i < asientos.length; i++){
            for (int j = 0; j < asientos.length; j++){
                asientos[i][j] = false;
            }
        }
    }

    public String getID(){
        return id;
    }

    public Avion getAvion(){
        return avion;
    }

    public Aeropuerto getOrigen(){
        return origen;
    }

    public int getTerminalOrigen(){
        return terminalOrigen;
    }

    public Fecha getSalida(){
        return salida;
    }

    public Aeropuerto getDestino(){
        return destino;
    }

    public int getTerminalDestino(){
        return terminalDestino;
    }

    public Fecha getLlegada(){
        return llegada;
    }

    public double getPrecio(){
        return precio;
    }

    public double getPrecioPreferente(){
        return precio * 1.25;
    }

    public double getPrecioPrimera(){
        return precio * 1.5;
    }

    public int numAsientosLibres(){
        int asientosLibres = 0;
        Avion resultado = null;
        for (int i = 1; i <= avion.getFilas(); i++ ){
            for (int j = 1; j < avion.getColumnas(); j++){
                if (asientoOcupado(i, j) == false){       //False no esta ocupado, true si lo esta.
                    asientosLibres ++;
                }
            }
        }
        return asientosLibres;
    }

    public boolean vueloLleno(){
        boolean lleno = false;
        if (numAsientosLibres() == 0){
            lleno = true;
        }
        return lleno;
    }

    public boolean asientoOcupado(int fila, int columna){
        boolean ocupado = false;
        if (asientoOcupado(fila, columna)) {
            asientos[fila][columna] = false;
            ocupado=true;
        }
        return ocupado;
    }

   // public Billete buscarBillete(String localizador);

    //Devuelve el obejeto billete que corresponde con una fila o columna,
    //Devolverá null si está libre o se excede en el límite de fila y columna
    //public Billete buscarBillete(int fila, int columna);

    //Si está desocupado el asiento que indica el billete, lo pone ocupado y devuelve true, si no devuelve false
    //public boolean ocuparAsiento(Billete billete);

    //A traves del loalizador de billete, se desocupará el asiento
    //public boolean desocuparAsiento(String localizador);

    // Añade los billetes al final de un fichero CSV, sin sobreescribirlo
    //public boolean aniadirBilletesCsv(String fichero);

    // Devuelve una cadena con información completa del vuelo
    //Ejemplo: Vuelo PM0066 de Josep Tarradellas Barcelona-El Prat(BCN) T2 (01/01/2023 08:15:00) a Gran Canaria(LPA) T1 (01/01/2023 11:00:05) en Boeing 747(EC-LKD) por 182,52€, asientos libres: 409
    public String toString() {
        return "Vuelo " + id + " de " + origen.getNombre() + "(" + origen.getCodigo() + ") " + terminalOrigen  + " (" + salida + ") a "
                + destino.getNombre() + "(" + destino.getCodigo() + ") " + terminalDestino + " (" + llegada + ") en " + avion.getMarca() + avion.getModelo()
                + "(" + avion.getMatricula() + ") por " + precio + "€, asientos libres: " + numAsientosLibres();
    }

    // Devuelve una cadena con información abreviada del vuelo
    //Ejemplo: Vuelo PM0066 de Josep Tarradellas Barcelona-El Prat(BCN) T2 (01/01/2023 08:15:00) a Gran Canaria(LPA) T1 (01/01/2023 11:00:05)
    public String toStringSimple(){
        return "Vuelo " + id + " de " + origen.getNombre() + "(" + origen.getCodigo() + ") " + terminalOrigen  + " (" + salida + ") a "
                + destino.getNombre() + "(" + destino.getCodigo() + ") " + terminalDestino + " (" + llegada + ")";
    }

    //Devuelve true si el código origen, destino y fecha son los mismos que el vuelo
    public boolean coincide(String codigoOrigen, String codigoDestino, Fecha fecha){
        boolean correcto = false;
        if (codigoOrigen == codigoDestino && fecha.coincide(fecha) == true){
            correcto = true;
        }
        return correcto;
    }

    // Muestra la matriz  de asientos del vuelo, ejemplo:
    //   A  B  C  D  E  F
    // 1( )(X)( )( )( )( )
    // 2{X}{X}{ }{ }{ }{ }
    // 3{ }{ }{ }{X}{X}{X}
    // 4{ }{ }{ }{ }{ }{ }
    // 5{ }{ }{X}{ }{ }{ }
    // 6[ ][ ][ ][ ][ ][ ]
    // 7[X][X][X][ ][ ][ ]
    // 8[ ][ ][ ][ ][ ][ ]
    // 9[ ][X][ ][ ][ ][X]
    //10[ ][ ][ ][ ][ ][ ]
    public void imprimirMatrizAsientos(){
    for (int i = 1; i <= asientos.length; i++){
        System.out.println();
        for (int j = 1; j <= asientos.length; j++){
            System.out.println(asientos[i][j]);
        }
    }
}
    //Devuelve true si ha podido escribir en un fichero la lista de pasajeros del vuelo, siguiendo las indicaciones del enunciado
    public boolean generarListaPasajeros(String fichero){
        return true;
    }

    //Métodos estáticos

    //Genera un ID de vuelo. Este consistirá en una cadena de 6 caracteres, de los cuales los dos 
    //primeros serán PM y los 4 siguientes serán números aleatorios. Ejemplo: PM0123
    //NOTA: Usar el objeto rand pasado como argumento para la parte aleatoria.
    public static String generarID(Random rand){
        return String.format("PM%04d", rand.nextInt(9999));
    }

    //Crea y devuelve un objeto Vuelo de los datos que selecciona el usuario de aeropuertos y aviones y la restricción de que
    //no puede estar repetido el identificador, siguiendo las indicaciones del enunciado
    //La función solicita repetidamente los parametros hasta que sean correctos
    public static Vuelo altaVuelo(Scanner teclado, Random rand, ListaAeropuertos aeropuertos, ListaAviones aviones, ListaVuelos vuelos){
            boolean vueloCorrecto = false;
            String vueloID;

            do {
                vueloID = generarID(rand);
                for (int i = 0; i<vuelos.getOcupacion(); i ++){
                    if (vueloID.equals(vuelos.getVuelo(i + 1))){
                        vueloCorrecto = true;
                    }
                }
            }while(vueloCorrecto);

            Aeropuerto origen = aeropuertos.seleccionarAeropuerto(teclado, "Ingrese código de Aeropuerto Origen:");
            String primerMensaje = "Ingrese Terminal Origen (1 - " + origen.getTerminales() + "):";
            int terminalO = Utilidades.leerNumero(teclado, primerMensaje, 1, origen.getTerminales());
            Aeropuerto destino = aeropuertos.seleccionarAeropuerto(teclado, "Ingrese código de Aeropuerto Destino:");
            String segundoMensaje = "Ingrese Terminal Destino (1 - " + destino.getTerminales() + "):";
            int termialD = Utilidades.leerNumero(teclado, segundoMensaje, 1, destino.getTerminales());
            double distancia = origen.distancia(destino);
            Avion avionUso = aviones.seleccionarAvion(teclado, "Ingrese matrícula de Avión:", distancia);
            Fecha salida = null;
            boolean posterior = true;
            Fecha llegada = null;

            do {
                salida = Utilidades.leerFechaHora(teclado, "Fecha de Salida:");
                llegada = Utilidades.leerFechaHora(teclado, "Fecha de Llegada:");
                if (salida.anterior(llegada)){
                    posterior = true;
                }else {
                    System.out.println("Llegada debe ser posterior a salida.");
                    posterior = false;
                }
            }while (!posterior);

            double precio = Utilidades.leerNumero(teclado, "Ingrese precio de pasaje:", 0, 999D);
            Vuelo resultado = new Vuelo(vueloID, avionUso, origen, terminalO, salida, destino, termialD, llegada, precio);
            vuelos.insertarVuelo(resultado);
            //if ()
            //System.out.println("Vuelo " + vueloID + " creado con éxito.");
        return resultado;
        }
}