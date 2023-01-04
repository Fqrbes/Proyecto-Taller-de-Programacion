package es.upm.tp;

import java.util.Random;
import java.util.Scanner;

/**
 * Description of the class
 *
 * @author Cesar Jimenez Laguna
 * @author Iñaki Ramos Iturria
 * @version 1.0
 */
public class AirUPM {
    private int maxAeropuertos;

    private int maxAviones;

    private int maxVuelos;

    private int maxPasajeros;

    private int maxBilletesPasajeros;

    private String aeropuerto, avion, vuelo, pasajeros, billetes;

    private ListaAeropuertos listaAeropuertos;

    private ListaAviones listaAviones;

    private ListaVuelos listaVuelos;

    private ListaPasajeros listaPasajeros;

    //private Billetes[] listaBilletes;

    /**
     * Constructor of the class
     *
     * @param maxAeropuertos
     * @param maxAviones
     * @param maxVuelos
     * @param maxPasajeros
     * @param maxBilletesPasajero
     */
    public AirUPM(int maxAeropuertos, int maxAviones, int maxVuelos, int maxPasajeros, int maxBilletesPasajero, String aeropuerto, String avion, String vuelo, String pasajeros, String billetes) {
        this.maxAeropuertos = maxAeropuertos;
        this.maxAviones = maxAviones;
        this.maxVuelos = maxVuelos;
        this.maxPasajeros = maxPasajeros;
        this.maxBilletesPasajeros = maxBilletesPasajero;
        this.aeropuerto = aeropuerto;
        this.avion = avion;
        this.vuelo = vuelo;
        this.pasajeros = pasajeros;
        this.billetes = billetes;
        cargarDatos(aeropuerto, avion, vuelo, pasajeros, billetes);
    }

    // Lee los datos de los ficheros especificados y los agrega a AirUPM
    public void cargarDatos(String ficheroAeropuertos, String ficheroAviones, String ficheroVuelos, String ficheroPasajeros, String ficheroBilletes) {
        listaAeropuertos = ListaAeropuertos.leerAeropuertosCsv(ficheroAeropuertos, maxAeropuertos);
        listaAviones = ListaAviones.leerAvionesCsv(ficheroAviones, maxAviones);
        listaVuelos = ListaVuelos.leerVuelosCsv(ficheroVuelos, maxVuelos, listaAeropuertos, listaAviones);
        listaPasajeros = ListaPasajeros.leerPasajerosCsv(ficheroPasajeros, maxPasajeros, maxBilletesPasajeros);
    }

    // Almacena los datos de AirUPM en los ficheros CSV especificados
    public boolean guardarDatos(String ficheroAeropuertos, String ficheroAviones, String ficheroVuelos, String ficheroPasajeros, String ficheroBilletes) {
        boolean listasCorrectas = true;
        if (!listaAeropuertos.escribirAeropuertosCsv(ficheroAeropuertos)) {
            listasCorrectas = false;
        }
        if (!listaAviones.escribirAvionesCsv(ficheroAviones)) {
            listasCorrectas = false;
        }
        if (!listaVuelos.escribirVuelosCsv(ficheroVuelos)) {
            listasCorrectas = false;
        }
        if (!listaPasajeros.escribirPasajerosCsv(ficheroPasajeros)) {
            listasCorrectas = false;
        }
        return listasCorrectas;
    }

    public boolean maxVuelosAlcanzado() {
        return listaVuelos.estaLlena();
    }

    public boolean insertarVuelo(Vuelo vuelo) {
        return listaVuelos.insertarVuelo(vuelo);
    }

    public boolean maxPasajerosAlcanzado() {
        return listaPasajeros.estaLlena();
    }

    public boolean insertarPasajero(Pasajero pasajero) {
        return listaPasajeros.insertarPasajero(pasajero);
    }

    // Funcionalidad buscarVuelo especificada en el enunciado del proyecto, que devuelve una lista de vuelos entre dos aeropuertos y
    // con una fecha de salida solicitados por teclado al usuario en el orden y con los textos indicados en los ejemplos de
    // ejecución del enunciado
    public ListaVuelos buscarVuelo(Scanner teclado) {
        Aeropuerto aeropuertoOrigen = listaAeropuertos.seleccionarAeropuerto(teclado, "Ingrese código de Aeropuerto Origen:");
        Aeropuerto aeropuertoDestino = listaAeropuertos.seleccionarAeropuerto(teclado, "Ingrese código de Aeropuerto Destino:");
        Fecha fechaSalidaVuelo = Utilidades.leerFecha(teclado, "Fecha de Salida:");

        return listaVuelos.buscarVuelos(aeropuertoOrigen.getCodigo(), aeropuertoDestino.getCodigo(), fechaSalidaVuelo);
    }


    // Funcionalidad comprarBillete especificada en el enunciado del proyecto, que compra un billete para un vuelo especificado,
    // pidiendo por teclado los datos necesarios al usuario en el orden y con los textos indicados en los ejemplos de ejecución del
    // enunciado. Si la lista de pasajeros está vacía, creará un nuevo pasajero, si está llena seleccionará un pasajero, en cualquier
    // otro caso, deberá preguntar al usuario si crear o seleccionar
    public void comprarBillete(Scanner teclado, Random rand, Vuelo vuelo) {

    }

    //Métodos estáticos

    // Muestra el menú y solicita una opción por teclado
    public static int menu(Scanner teclado) {
        System.out.println("1. Alta Vuelo");
        System.out.println("2. Alta Pasajero");
        System.out.println("3. Buscar Vuelo");
        System.out.println("4. Mostrar billetes de Pasajero");
        System.out.println("5. Generar lista de Pasajeros");
        System.out.println("0. Salir");
        return Utilidades.leerNumero(teclado, "Seleccione opción:", 0, 5);
    }

    // Carga los datos de los ficheros CSV pasados por argumento (consola) en AirUPM, llama iterativamente al menú y realiza la
    //  opción especificada hasta que se indique la opción Salir, y finalmente guarda los datos de AirUPM en los mismos ficheros CSV
    public static void main(String[] args) {
        if (args.length != 10) {
            System.out.println("Número de argumentos incorrecto.");
        } else {
            Random random = new Random();
            Scanner scanner = new Scanner(System.in);
            AirUPM airUPM = new AirUPM(Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3]), Integer.parseInt(args[4]), args[5], args[6], args[7], args[8], args[9]);
            boolean salir = false;
            while (!salir) {
                switch (menu(scanner)) {
                    case 1:
                        if (airUPM.maxVuelosAlcanzado()) {
                            System.out.println("No se pueden dar de alta más vuelos.");
                        } else {
                            Vuelo nuevoVuelo = Vuelo.altaVuelo(scanner, random, airUPM.listaAeropuertos, airUPM.listaAviones, airUPM.listaVuelos);
                            if (airUPM.insertarVuelo(nuevoVuelo)) {
                                System.out.println("Vuelo " + nuevoVuelo.getID() + " creado con éxito.");
                            }
                        }
                        break;
                    case 2:
                        if (airUPM.maxPasajerosAlcanzado()) {
                            System.out.println("No se pueden dar de alta más pasajeros.");
                        } else {
                            Pasajero nuevoPasajero = Pasajero.altaPasajero(scanner, airUPM.listaPasajeros, airUPM.maxBilletesPasajeros);
                            if (airUPM.insertarPasajero(nuevoPasajero)) {
                                System.out.printf("Pasajero con DNI %08d" + nuevoPasajero.getLetraDNI() + " dado de alta con éxito.\n" ,nuevoPasajero.getNumeroDNI());
                                //System.out.printf("Pasajero con DNI " + nuevoPasajero.getDNI() + " dado de alta con éxito.\n");
                                //Juan Alberto
                                //García Gámez
                                //ja.garcia@alumnos.upm.es
                            }
                        }
                        break;
                    case 3:
                        ListaVuelos lista = airUPM.buscarVuelo(scanner);
                        if (lista.getVuelo(0) == null) {
                            System.out.println("No se ha encontrado ningún vuelo.");
                        } else {
                            lista.listarVuelos();
                            Vuelo comprarBillete = lista.seleccionarVuelo(scanner, "Ingrese ID de vuelo para comprar billete o escribir CANCELAR:", "CANCELAR");
                            if (comprarBillete != null){
                                airUPM.comprarBillete(scanner, new Random(), comprarBillete);
                            }
                        }
                        break;
                    case 4:
                        Pasajero pasajeroActual = airUPM.listaPasajeros.seleccionarPasajero(scanner, "Ingrese DNI del pasajero:");
                        if (pasajeroActual.numBilletesComprado() == 0){
                            System.out.println("El pasajero seleccionado no ha adquirido ningun billete.");
                        }else{
                            Billete billeteActual = pasajeroActual.seleccionarBillete(scanner, "Ingrese localizador del billete:");
                            char opcion;
                            do {
                                opcion = Utilidades.leerLetra(scanner, "¿Generar factura del billete (f), cancelar (c) o volver al menu (m)?", 'a', 'z');
                                if (opcion != 'f' && opcion != 'c' && opcion != 'm'){
                                    System.out.println("El valor de la entrada debe ser 'f', 'c' o 'm'");
                                }
                            }while (opcion != 'f' && opcion != 'c' && opcion != 'm');
                            if (opcion == 'f'){
                                System.out.print("Introduzca la ruta donde generar la factura");
                                String rutaFactura = scanner.nextLine();
                                billeteActual.generarFactura(rutaFactura);
                            }else{
                                if (opcion == 'c'){
                                  billeteActual.cancelar();
                                }
                            }
                        }
                        break;
                    case 5:
                        Vuelo vueloActual = airUPM.listaVuelos.seleccionarVuelo(scanner, "Ingrese ID de vuelo:", "CANCELAR");
                        System.out.println("Introduzca la ruta donde generar la lista de pasajeros:");
                        String rutaPasajeros = scanner.nextLine();
                        if (vueloActual.generarListaPasajeros(rutaPasajeros)){
                            System.out.println("Lista de pasajeros del Vuelo " + vueloActual.getID() + "generada en " + rutaPasajeros);
                        }
                        break;

                    case 0:
                        salir = true;
                        //airUPM.guardarDatos(airUPM.aeropuerto, airUPM.avion, airUPM.vuelo, airUPM.pasajeros, airUPM.billetes);
                }
            }
        }
    }
}