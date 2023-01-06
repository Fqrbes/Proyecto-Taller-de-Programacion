package es.upm.tp;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

/**
 * Description of the class
 *
 * @author Cesar Jimenez Laguna
 * @author Iñaki Ramos Iturria
 * @version 1.0
 */
public class Vuelo {
    /**
     * Atributo que contiene la matriz de los asientos de un avion
     */
    private boolean[][] asientos;

    /**
     * Atributo que contiene id de un vuelo
     */
    private String id;

    /**
     * Atributo que contiene un vuelo
     */
    private Avion avion;

    /**
     * Atributo que contiene el aeropuerto de origen de un vuelo
     */
    private Aeropuerto origen;

    /**
     * Atributo que contiene la terminal de origen de un vuelo
     */
    private int terminalOrigen;

    /**
     * Atributo que contiene la fecha de salida de un vuelo
     */
    private Fecha salida;

    /**
     * Atributo que contiene el aeropuertpo de destino de un vuelo
     */
    private Aeropuerto destino;

    /**
     * Atributo que contiene la terminal de destino de un vuelo
     */
    private int terminalDestino;


    /**
     * Atributo que contiene la fecha llegada de un vuelo
     */
    private Fecha llegada;

    /**
     * Atributo que contiene el perecio del billete de un vuelo
     */
    private double precio;

    private ListaBilletes listaBilletesVuelo;

    /**
     * Constructor of the class. Constructor que crea un vuelo con los parametros (id, avion, origen, terminal de origen, llegada, destino,
     * terminal de destino, llegada y precio) que recibe.
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

    public Vuelo(String id, Avion avion, Aeropuerto origen, int terminalOrigen, Fecha salida, Aeropuerto destino, int terminalDestino, Fecha llegada, double precio) {
        this.id = id;
        this.avion = avion;
        this.origen = origen;
        this.terminalOrigen = terminalOrigen;
        this.salida = salida;
        this.destino = destino;
        this.terminalDestino = terminalDestino;
        this.llegada = llegada;
        this.precio = precio;
        this.asientos = new boolean[avion.getFilas()][avion.getColumnas()];
        for (int i = 0; i < avion.getFilas(); i++) {
            for (int j = 0; j < avion.getColumnas(); j++) {
                asientos[i][j] = false;
            }
        }
        listaBilletesVuelo = new ListaBilletes(avion.getFilas() * avion.getColumnas());
    }

    /**
     * @return Devuelve el id del vuelo
     */
    public String getID() {
        return id;
    }

    /**
     * @return Devuelve el avion del vuelo
     */
    public Avion getAvion() {
        return avion;
    }

    /**
     * @return Devuelve el origen del vuelo
     */
    public Aeropuerto getOrigen() {
        return origen;
    }

    /**
     * @return Devuelve la terminal de origen del vuelo
     */
    public int getTerminalOrigen() {
        return terminalOrigen;
    }

    /**
     * @return Devuelve la fecha de salida del vuelo
     */
    public Fecha getSalida() {
        return salida;
    }

    /**
     * @return Devuelve el destino del vuelo
     */
    public Aeropuerto getDestino() {
        return destino;
    }

    /**
     * @return Devuelve la terminal de destino del vuelo
     */
    public int getTerminalDestino() {
        return terminalDestino;
    }

    /**
     * @return Devuelve la fecha de llegada del vuelo
     */
    public Fecha getLlegada() {
        return llegada;
    }

    /**
     * @return Devuelve el precio del vuelo
     */
    public double getPrecio() {
        return precio;
    }

    /**
     * @return Devuelve el precio de preferencia (economico)
     */
    public double getPrecioPreferente() {
        return precio * 1.25;
    }

    /**
     * @return Devuelve el precio de primera clase del vuelo
     */
    public double getPrecioPrimera() {
        return precio * 1.5;
    }


    /**
     * @return Devuelve el numero de los asientos libre en un Avion
     */
    public int numAsientosLibres() {
        int asientosLibres = 0;
        for (int i = 1; i <= avion.getFilas(); i++) {
            for (int j = 1; j <= avion.getColumnas(); j++) {
                if (!asientoOcupado(i, j)) {       //False no esta ocupado, true si lo esta.
                    asientosLibres++;
                }
            }
        }
        return asientosLibres;
    }

    /**
     * @return Devuelve si el vuelo elegido esta lleno en cuanto a ocupacion
     */
    public boolean vueloLleno() {
        boolean lleno = false;
        if (numAsientosLibres() == 0) {
            lleno = true;
        }
        return lleno;
    }

    /**
     * @param fila
     * @param columna
     * @return Devuelve true si el asiento elegido por un pasajero esta ocupado
     */
    public boolean asientoOcupado(int fila, int columna) {
        boolean ocupado = false;
        if (asientos[fila - 1][columna - 1]) {//Como la matriz asientos empieza en 0 hay que quitarle 1 porque si no este if impieza en 1
            ocupado = true;
        }
        return ocupado;
    }

    /**
     * @param localizador
     * @return Devuelve la ubicacion del billete buscado
     */
    public Billete buscarBillete(String localizador) {
        Billete localizado = null;
        localizado = listaBilletesVuelo.buscarBillete(localizador);
        return localizado;
    }

    /**
     * @param fila
     * @param columna
     * @return Devuelve un objeto billete que corresponde con una fila y columna determinado (dependiendo del asiento elegido),
     * en caso contrario devuelve null indicando que exede de los parametros establecidos por la fila y la columna del asiento elegido.
     */
    //Devuelve el obejeto billete que corresponde con una fila o columna,
    //Devolverá null si está libre o se excede en el límite de fila y columna
    public Billete buscarBillete(int fila, int columna) {
        Billete localizado = null;
        localizado = listaBilletesVuelo.buscarBillete(getID(), fila, columna);
        return localizado;
    }

    /**
     * @param billete
     * @return Devuelve si un asiento esta ocupado por un pasajero (true) y si no esta ocupado (false)
     */
    //Si está desocupado el asiento que indica el billete, lo pone ocupado y devuelve true, si no devuelve false
    public boolean ocuparAsiento(Billete billete) {
        boolean ocupado = false;
        if (!asientoOcupado(billete.getFila(), billete.getColumna())) {
            asientos[billete.getFila() - 1][billete.getColumna() - 1] = true; //true de ocupado
            ocupado = true;
        }
        return ocupado;
    }

    /**
     * @param localizador
     * @return Devuelve que un asiento esta desocupado a traves del localizador
     */
    //A traves del localizador de billete, se desocupará el asiento
    public boolean desocuparAsiento(String localizador) {
        boolean desocupado = false;
        Billete billete = buscarBillete(localizador);
        if (asientoOcupado(billete.getFila(), billete.getColumna())) {
            asientos[billete.getFila()-1][billete.getColumna()-1] = false;
            desocupado = true;
        }
        return desocupado;
    }

    /**
     * @param fichero
     * @return devuelve la lista de billetes del vuelo con la informacion añadida al fichero CSV
     */
    // Añade los billetes al final de un fichero CSV, sin sobreescribirlo
    public boolean aniadirBilletesCsv(String fichero) {
        return listaBilletesVuelo.aniadirBilletesCsv(fichero);
    }


    /**
     * @return
     */
    // Devuelve una cadena con información completa del vuelo
    //Ejemplo: Vuelo PM0066 de Josep Tarradellas Barcelona-El Prat(BCN) T2 (01/01/2023 08:15:00) a Gran Canaria(LPA) T1 (01/01/2023 11:00:05) en Boeing 747(EC-LKD) por 182,52€, asientos libres: 409
    public String toString() {
        return "Vuelo " + id + " de " + origen.getNombre() + "(" + origen.getCodigo() + ") T" + terminalOrigen + " (" + salida.toString() + ") a "
                + destino.getNombre() + "(" + destino.getCodigo() + ") T" + terminalDestino + " (" + llegada.toString() + ") en " + avion.getMarca() + " " + avion.getModelo()
                + "(" + avion.getMatricula() + ") por " + precio + "€, asientos libres: " + numAsientosLibres();
    }

    // Devuelve una cadena con información abreviada del vuelo
    //Ejemplo: Vuelo PM0066 de Josep Tarradellas Barcelona-El Prat(BCN) T2 (01/01/2023 08:15:00) a Gran Canaria(LPA) T1 (01/01/2023 11:00:05)
    public String toStringSimple() {
        return "Vuelo " + id + " de " + origen.getNombre() + "(" + origen.getCodigo() + ") " + terminalOrigen + " (" + salida.toString() + ") a "
                + destino.getNombre() + "(" + destino.getCodigo() + ") " + terminalDestino + " (" + llegada.toString() + ")";
    }

    //Devuelve true si el código origen, destino y fecha son los mismos que el vuelo
    public boolean coincide(String codigoOrigen, String codigoDestino, Fecha fecha) {
        boolean correcto = false;
        if (codigoDestino.equals(codigoOrigen) && fecha.coincide(fecha)) {
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
    public void imprimirMatrizAsientos() {
        System.out.print("    ");
        for (int i = 1; i <= avion.getColumnas(); i++) {
            System.out.printf("%c  ", 64 + i);
        }
        System.out.println();
        System.out.printf("  1");
        for (int j = 1; j <= avion.getColumnas(); j++) {
            if (asientoOcupado(1, j)) {
                System.out.print("(X)");
            } else {
                System.out.print("( )");
            }
        }
        System.out.println();
        for (int f = 2; f < 6; f++) {
            System.out.printf(" %2d", f);
            for (int c = 1; c <= avion.getColumnas(); c++){
                if (asientoOcupado(f, c)) {
                    System.out.print("{X}");
                } else {
                    System.out.print("{ }");
                }
            }
            System.out.println();
        }
        for (int f = 6; f <= avion.getFilas(); f++) {
            System.out.printf(" %2d", f);
            for (int c = 1; c <= avion.getColumnas(); c++){
                if (asientoOcupado(f, c)) {
                    System.out.print("[X]");
                } else {
                    System.out.print("[ ]");
                }
            }
            System.out.println();
        }
    }

    //Devuelve true si ha podido escribir en un fichero la lista de pasajeros del vuelo, siguiendo las indicaciones del enunciado
    public boolean generarListaPasajeros(String fichero) {
        boolean ficheroActualizado = false;
        Billete billete = null;
        FileWriter fileWriter = null;
        char[] letraAsiento = {'A', 'B', 'C', 'D', 'E', 'F'};
        try {
            fileWriter = new FileWriter(fichero, true);
            fileWriter.write("--------------------------------------------------\n");
            fileWriter.write("--------- Lista de pasajeros en vuelo " + this.id + " ---------\n");
            fileWriter.write("--------------------------------------------------\n");
            fileWriter.write("Asiento Tipo        Pasajero");
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 6; j++) {
                    if (asientos[i + 1][j + 1]) {
                        fileWriter.write(i + "" + letraAsiento[j] + "       " + (billete.getTipo() == Billete.TIPO.PREFERENTE ? billete.getTipo().toString() : billete.getTipo().toString() + "   ") + "  " +
                                billete.getPasajero().toString());
                    } else {
                        fileWriter.write(i + "" + letraAsiento[j]);
                    }
                }
            }
        } catch (FileNotFoundException excepcion1) {
            System.out.println("El fichero " + fichero + " no encontrado");
            ficheroActualizado = false;
        } catch (IOException excepcion2) {
            System.out.println("Error de escritura en fichero " + fichero + ".");
            ficheroActualizado = false;
        } finally {
            if (fileWriter != null) {
                try {
                    fileWriter.close();
                } catch (IOException excepcion3) {
                    System.out.println("Error de cierre del fichero " + fichero + ".");
                    ficheroActualizado = false;
                }
            }
        }
        return ficheroActualizado;
    }

    //Métodos estáticos

    //Genera un ID de vuelo. Este consistirá en una cadena de 6 caracteres, de los cuales los dos
    //primeros serán PM y los 4 siguientes serán números aleatorios. Ejemplo: PM0123
    //NOTA: Usar el objeto rand pasado como argumento para la parte aleatoria.
    public static String generarID(Random rand) {
        return String.format("PM%04d", rand.nextInt(9999));
    }

    //Crea y devuelve un objeto Vuelo de los datos que selecciona el usuario de aeropuertos y aviones y la restricción de que
    //no puede estar repetido el identificador, siguiendo las indicaciones del enunciado
    //La función solicita repetidamente los parametros hasta que sean correctos
    public static Vuelo altaVuelo(Scanner teclado, Random rand, ListaAeropuertos aeropuertos, ListaAviones aviones, ListaVuelos vuelos) {
        boolean vueloCorrecto = false;

        String vueloID;
        do {
            vueloID = generarID(rand);
            for (int i = 0; i < vuelos.getOcupacion(); i++) {
                if (vueloID.equals(vuelos.getVuelo(i).getID())) {
                    vueloCorrecto = true;
                }
            }
        } while (!vueloCorrecto);

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
            if (salida.anterior(llegada)) {
                posterior = true;
            } else {
                System.out.println("Llegada debe ser posterior a salida.");
                posterior = false;
            }
        } while (!posterior);

        double precio = Utilidades.leerNumero(teclado, "Ingrese precio de pasaje:", 0D, 999D);
        Vuelo resultado = new Vuelo(vueloID, avionUso, origen, terminalO, salida, destino, termialD, llegada, precio);
        return resultado;
    }
}