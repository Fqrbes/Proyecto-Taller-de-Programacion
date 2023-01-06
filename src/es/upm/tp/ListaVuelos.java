package es.upm.tp;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Objects;
import java.util.Scanner;

/**
 * ListaVuelos es una clase que encapsula las variables enteras usadas para definir los vuelos,
 * así como también contiene funciones bara buscar, seleccionar e insertar vuelos en el array de nombre listavuelos
 * Tambien escribe un fichero.csv con los datos de cada vuelo
 *
 * @author      Cesar Jimenez Laguna
 * @author      Iñaki Ramos Iturria
 * @version     1.0
 */
public class ListaVuelos {

    /**
     * Atributo que contiene la capacidad de un vuelo
     */
    private int capacidad;

    /**
     * Atributo que contiene la ocupacion de un vuelo
     */
    private int ocupacion;

    /**
     * Atributo que contiene el vector donde estan los vuelos
     */
    private Vuelo[] ListaVuelos;

    /**
     * Constructor of the class.
     *
     * @param capacidad
     */
    public ListaVuelos(int capacidad){
        this.ocupacion = 0;
        this.capacidad = capacidad;
        ListaVuelos = new Vuelo[capacidad];
    }
    public int getOcupacion(){
        return ocupacion;
    }

    public boolean estaLlena(){
        boolean estaLlena = false;
        if (ocupacion == ListaVuelos.length){
            estaLlena = true;
        }
        return estaLlena;
    }

    //Devuelve el objeto vuelo que está en la posición i del array
    public Vuelo getVuelo(int i){
        return ListaVuelos[i];
    }

    //Devuelve true si puede insertar el vuelo
    public boolean insertarVuelo (Vuelo vuelo){
        boolean insertar = false;
        if (!estaLlena()){
            ListaVuelos[getOcupacion()] = vuelo;
            ocupacion++;
            insertar = true;
        }
        return insertar;
    }

    //Devuelve el objeto vuelo que tenga el identificador igual al parámetro id
    //Si no lo encuentra, devolverá null
    public Vuelo buscarVuelo (String id){
        Vuelo resultado = null;
        for (int i = 0; i < getOcupacion(); i++){
            if (Objects.equals(ListaVuelos[i].getID(), id)){
                resultado = ListaVuelos[i];
            }
        }
        /*
        for (int i = 0; i < ListaVuelos.length; i ++){
            if (Objects.equals(ListaVuelos[i].getID(), id)){
                resultado = ListaVuelos[i];
            }
        }*/
        return resultado;
    }

    //Devuelve un nuevo objeto ListaVuelos conteniendo los vuelos que vayan de un aeropuerto a otro en una determinada fecha
    public ListaVuelos buscarVuelos(String codigoOrigen, String codigoDestino, Fecha fecha){
        ListaVuelos vuelosBuscados = new ListaVuelos(ocupacion);

        for (int i = 0; i < ocupacion; i++){
            if ((codigoOrigen.equals(ListaVuelos[i].getOrigen().getCodigo())) && (codigoDestino.equals(ListaVuelos[i].getDestino().getCodigo()) && (fecha.coincide(ListaVuelos[i].getSalida())))){
                vuelosBuscados.insertarVuelo(ListaVuelos[i]);
            }
        }
        return vuelosBuscados;
    }

    //Muestra por pantalla los vuelos siguiendo el formato de los ejemplos del enunciado
    public void listarVuelos() {
        for (int i = 0; i < getOcupacion(); i++){
            System.out.println(ListaVuelos[i].toString());
        }
    }

    //Permite seleccionar un vuelo existente a partir de su ID, usando el mensaje pasado como argumento para la solicitud
    //y siguiendo el orden y los textos mostrados en el enunciado, y usando la cadena cancelar para salir devolviendo null
    //La función solicita repetidamente hasta que se introduzca un ID correcto
    public Vuelo seleccionarVuelo(Scanner teclado, String mensaje, String cancelar){
        Vuelo vueloExistente = null;
        boolean vueloTerminado = false;
        do {
            System.out.print(mensaje);
            String pantalla = teclado.nextLine();
            if (pantalla.equals(cancelar)){
                vueloTerminado = true;
            }else{
                vueloExistente = buscarVuelo(pantalla);
                if (vueloExistente == null){
                    System.out.println("ID de vuelo no encontrado");
                }else{
                    vueloTerminado = true;
                }
            }
        }while (!vueloTerminado);
        return vueloExistente;
    }

    //Ha de escribir la lista de vuelos en la ruta y nombre del fichero pasado como parámetro.
    //Si existe el fichero, se sobreescribe, si no existe se crea.
    public boolean escribirVuelosCsv(String fichero){
        PrintWriter printW = null;
        boolean copiado= true;
        try{
            printW = new PrintWriter(fichero);
            for (int i = 0; i < ocupacion; i ++){
                printW.println(ListaVuelos[i].getID() + ";" + ListaVuelos[i].getAvion() + ";" + ListaVuelos[i].getOrigen()
                        + ";" + ListaVuelos[i].getTerminalOrigen() + ";" + ListaVuelos[i].getSalida() + ";" + ListaVuelos[i].getDestino()
                        + ";" + ListaVuelos[i].getTerminalDestino() + ";" + ListaVuelos[i].getLlegada() + ";" + ListaVuelos[i].getPrecio());
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
    //Genera una lista de vuelos a partir del fichero CSV, usando los límites especificados como argumentos para la capacidad
    //de la lista
    public static ListaVuelos leerVuelosCsv(String fichero, int capacidad, ListaAeropuertos aeropuertos, ListaAviones aviones){
        ListaVuelos ListaVuelosCsv = new ListaVuelos(capacidad);
        Scanner teclado = null;
        String entrada;
        try {
            teclado = new Scanner(new FileReader(fichero));
            String ID, matricula, codigoO, codigoD;
            Aeropuerto origen, destino;
            Fecha salida, llegada;
            int torigen, tdestino;
            double precio;
            Avion AvionUso;
            Vuelo actual;
            do {
                entrada = teclado.nextLine();
                String [] matrizVuelo = entrada.split(";");
                ID = matrizVuelo[0];
                matricula = matrizVuelo[1];
                codigoO = matrizVuelo[2];
                torigen = Integer.parseInt(matrizVuelo[3]);
                salida = Fecha.fromString(matrizVuelo[4]);
                codigoD = matrizVuelo[5];
                tdestino = Integer.parseInt(matrizVuelo[6]);
                llegada = Fecha.fromString(matrizVuelo[7]);
                precio = Double.parseDouble(matrizVuelo[8]);
                AvionUso = aviones.buscarAvion(matricula);
                origen = aeropuertos.buscarAeropuerto(codigoO);
                destino = aeropuertos.buscarAeropuerto(codigoD);
                actual = new Vuelo(ID, AvionUso, origen, torigen, salida, destino, tdestino, llegada, precio);
                ListaVuelosCsv.insertarVuelo(actual);
            } while (teclado.hasNextLine());
        }catch (FileNotFoundException e){
            System.out.println("El fichero " + fichero + " no se ha encontrado");
        }finally{
            if (teclado != null){
                teclado.close();
            }
        }
        return ListaVuelosCsv;
    }
}