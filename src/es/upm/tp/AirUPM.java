package es.upm.tp;

import java.util.Random;
import java.util.Scanner;

/**
 * Description of the clas
 *
 * @author Cesar Jimenez Laguna
 * @author Iñaki Ramos Iturria
 * @version 1.0
 */
public class AirUPM {

    /**
     * Atributo que contiene el numero maximo de Aeropuertos
     */
    private int maxAeropuertos;

    /**
     * Atributo que contiene el numero maximo de Aviones
     */
    private int maxAviones;

    /**
     * Atributo que contiene el numero maximo de Vuelos
     */
    private int maxVuelos;

    /**
     * Atributo que contiene el numero maximo de Pasajeros
     */
    private int maxPasajeros;

    /**
     * Atributo que contiene el numero
     */
    private int maxBilletesPasajeros;

    /**
     * Atributo que contiene la capacidad de la ListaBilletes
     */
    private String aeropuerto, avion, vuelo, pasajeros, billetes;

    /**
     * Atributo que contiene la capacidad de la ListaBilletes
     */
    private ListaAeropuertos listaAeropuertos;

    /**
     * Atributo que contiene la capacidad de la ListaBilletes
     */
    private ListaAviones listaAviones;

    /**
     * Atributo que contiene la capacidad de la ListaBilletes
     */
    private ListaVuelos listaVuelos;

    /**
     * Atributo que contiene la capacidad de la ListaBilletes
     */
    private ListaPasajeros listaPasajeros;

    /**
     * Atributo que contiene la capacidad de la ListaBilletes
     */
    private ListaBilletes listaBilletes;

    /**
     * Constructor of the class
     *
     * @param maxAeropuertos
     * @param maxAviones
     * @param maxVuelos
     * @param maxPasajeros
     * @param maxBilletesPasajero
     */
    public AirUPM(int maxAeropuertos, int maxAviones, int maxVuelos, int maxPasajeros, int maxBilletesPasajero) {
        this.maxAeropuertos = maxAeropuertos;
        this.maxAviones = maxAviones;
        this.maxVuelos = maxVuelos;
        this.maxPasajeros = maxPasajeros;
        this.maxBilletesPasajeros = maxBilletesPasajero;
    }

    /**
     * funcion que carga los datos de los ficheros
     *
     * @param ficheroAeropuertos fichero aeropuerto
     * @param ficheroAviones     fichero aviones
     * @param ficheroVuelos      fichero vuelos
     * @param ficheroPasajeros   ficheros pasajeros
     * @param ficheroBilletes    ficheros billetes
     */
    // Lee los datos de los ficheros especificados y los agrega a AirUPM
    public void cargarDatos(String ficheroAeropuertos, String ficheroAviones, String ficheroVuelos, String ficheroPasajeros, String ficheroBilletes) {
        listaAeropuertos = ListaAeropuertos.leerAeropuertosCsv(ficheroAeropuertos, maxAeropuertos);
        listaAviones = ListaAviones.leerAvionesCsv(ficheroAviones, maxAviones);
        listaVuelos = ListaVuelos.leerVuelosCsv(ficheroVuelos, maxVuelos, listaAeropuertos, listaAviones);
        listaPasajeros = ListaPasajeros.leerPasajerosCsv(ficheroPasajeros, maxPasajeros, maxBilletesPasajeros);
        ListaBilletes.leerBilletesCsv(ficheroBilletes, listaVuelos, listaPasajeros);
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
        if (!listaBilletes.aniadirBilletesCsv(ficheroBilletes)) {
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
        char respuesta;
        String DNI;
        Pasajero pasajeroVigente = null;
        Billete billetePasajero = null;

        if (vuelo.vueloLleno()) {
            System.out.println("El vuelo " + vuelo.getID() + " está lleno, no se pueden comprar más billetes");
        } else {
            if (listaPasajeros.estaLlena()) {
                pasajeroVigente = listaPasajeros.seleccionarPasajero(teclado, "Ingrese DNI del pasajero");
                if (pasajeroVigente.maxBilletesAlcanzado()) {
                    System.out.println("El pasajero seleccionado no puede adquirir más billetes.");
                } else {
                    billetePasajero = Billete.altaBillete(teclado, rand, vuelo, pasajeroVigente);
                    System.out.println("Billete " + billetePasajero.getLocalizador() + " comprado con éxito.");
                }
            } else {
                do {
                    respuesta = Utilidades.leerLetra(teclado, "¿Comprar billete para un nuevo pasajero (n), o para uno ya existente (e)?", 'a', 'z');
                    if (respuesta != 'n' && respuesta != 'e')
                        System.out.println("El valor de entrada debe ser 'n' o 'e'");
                    if (respuesta == 'e') {
                        pasajeroVigente = listaPasajeros.seleccionarPasajero(teclado, "Ingrese DNI del pasajero:");
                        if (pasajeroVigente.maxBilletesAlcanzado()) {
                            System.out.println("El Pasajero seleccionado no puede adquirir más billetes.");
                        } else {
                            billetePasajero = Billete.altaBillete(teclado, rand, vuelo, pasajeroVigente);
                            vuelo.ocuparAsiento(billetePasajero);
                            if (vuelo.ocuparAsiento(billetePasajero) && pasajeroVigente.aniadirBillete(billetePasajero)) {
                                System.out.println("Billete " + billetePasajero.getLocalizador() + " comprado con éxito.");
                            }
                        }
                    }
                    if (respuesta == 'n') {
                        pasajeroVigente = Pasajero.altaPasajero(teclado, listaPasajeros, maxBilletesPasajeros);
                        billetePasajero = Billete.altaBillete(teclado, rand, vuelo, pasajeroVigente);
                        vuelo.ocuparAsiento(billetePasajero);
                        System.out.println("Billete " + billetePasajero.getLocalizador() + " comprado con éxito.");
                    }
                } while (respuesta != 'n' && respuesta != 'e');
            }
        }
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
        Scanner scanner = new Scanner(System.in);
        AirUPM airUPM = new AirUPM(Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3]), Integer.parseInt(args[4]));
        airUPM.cargarDatos(args[5], args[6], args[7], args[8], args[9]);
        int opcion;
        do {
            opcion = menu(scanner);
            switch (opcion) {
                case 1:
                    if (airUPM.listaVuelos.estaLlena()) { //airUPM.maxVuelosAlcanzado() airUPM.listaVuelos.estaLlena()
                        System.out.println("No se pueden dar de alta más vuelos.");
                    } else {
                        Vuelo.altaVuelo(scanner, new Random(), airUPM.listaAeropuertos, airUPM.listaAviones, airUPM.listaVuelos);

                        /*
                        Vuelo nuevoVuelo = Vuelo.altaVuelo(scanner, new Random(), airUPM.listaAeropuertos, airUPM.listaAviones, airUPM.listaVuelos);
                        if (airUPM.insertarVuelo(nuevoVuelo)) {
                            System.out.println("Vuelo " + nuevoVuelo.getID() + " creado con éxito.");
                        }
                        */
                    }
                    break;
                case 2:
                    if (airUPM.listaPasajeros.estaLlena()) { //airUPM.maxPasajerosAlcanzado()
                        System.out.println("No se pueden dar de alta más pasajeros.");
                    } else {
                        Pasajero nuevoPasajero = Pasajero.altaPasajero(scanner, airUPM.listaPasajeros, airUPM.maxBilletesPasajeros);
                        if (nuevoPasajero != null) //airUPM.insertarPasajero(nuevoPasajero)
                            System.out.printf("Pasajero con DNI %08d%c dado de alta con éxito.\n", nuevoPasajero.getNumeroDNI(), nuevoPasajero.getLetraDNI());
                    }
                    break;
                case 3:
                    ListaVuelos ListaVuelos = airUPM.buscarVuelo(scanner);
                    if (ListaVuelos.getOcupacion() == 0) { //lista.getVuelo(0) == null
                        System.out.println("No se ha encontrado ningún vuelo.");
                    } else {
                        ListaVuelos.listarVuelos();
                        //TERMINAR COMPRAR BILLETE CON VUELO SELECCIONADO.
                        //quitar comentario y cambiar el nombre de ka variable aComprar
                        Vuelo aComprar = ListaVuelos.seleccionarVuelo(scanner, "Ingrese ID de vuelo para comprar billete o escriba CANCELAR:", "CANCELAR");
                        if (aComprar != null) {
                            airUPM.comprarBillete(scanner, new Random(), aComprar);
                        }
                    }
                    break;
                case 4:
                    Pasajero pasajeroActual = airUPM.listaPasajeros.seleccionarPasajero(scanner, "Ingrese DNI del pasajero:");
                    if (pasajeroActual.numBilletesComprado() == 0) {
                        System.out.println("El pasajero seleccionado no ha adquirido ningún billete.");
                    } else {
                        pasajeroActual.getListaBilletesPasajeros().listarBilletes();
                        Billete billeteActual = pasajeroActual.getListaBilletesPasajeros().seleccionarBillete(scanner, "Ingrese localizador del billete:");
                        char opcion2;
                        do {
                            opcion2 = Utilidades.leerLetra(scanner, "¿Generar factura del billete (f), cancelarlo (c) o volver al menú (m)?", 'a', 'z');
                            if (opcion2 != 'f' && opcion2 != 'c' && opcion2 != 'm') {
                                System.out.println("El valor de entrada debe ser 'f', 'c' o 'm'");
                            }
                        } while (opcion2 != 'f' && opcion2 != 'c' && opcion2 != 'm');
                        if (opcion2 == 'f') {
                            System.out.print("Introduzca la ruta donde generar la factura:");
                            String ruteFactura = scanner.nextLine();
                            billeteActual.generarFactura(ruteFactura);
                        } else if (opcion2 == 'c') {
                            String localizadorBilleteActual = billeteActual.getLocalizador();
                            if (billeteActual.cancelar())
                                System.out.println("Billete " + localizadorBilleteActual + " cancelado.");
                        }
                    }
                    break;
                case 5:
                    Vuelo vueloActual = airUPM.listaVuelos.seleccionarVuelo(scanner, "Ingrese ID del vuelo:", "CANCELAR");
                    System.out.print("Introduzca la ruta donde generar la lista de pasajeros:");
                    String rutaPasajeros = scanner.nextLine();
                    if (vueloActual.generarListaPasajeros(rutaPasajeros)) {
                        System.out.println("Lista de pasajeros del Vuelo " + vueloActual.getID() + " generada en " + rutaPasajeros);
                    }
                    break;
                case 0:
                    airUPM.guardarDatos(args[5], args[6], args[7], args[8], args[9]);
                    break;
            }
        } while (opcion != 0);
    }
}