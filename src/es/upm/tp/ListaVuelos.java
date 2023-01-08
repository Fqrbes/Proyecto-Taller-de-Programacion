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
     * Atributo que contiene la capacidad de la Listavuelos
     */
    private int capacidad;

    /**
     * Atributo que contiene la ocupacion de un vuelo dentro de la lista
     */
    private int ocupacion;

    /**
     * Atributo que contiene el array donde estan los vuelos
     */
    private Vuelo[] ListaVuelos;

    /**
     * Constructor que crea un array con la cantidad de vuelos recibidos.
     * @param capacidad especifica la capacidad de la lista que contiene los aviones
     */
    public ListaVuelos(int capacidad){
        this.ocupacion = 0;
        this.capacidad = capacidad;
        ListaVuelos = new Vuelo[capacidad];
    }

    /**
     * Getter del atributo ocupacion
     * @return devuelve la cantidad de vuelos que hay en la listaVuelos como una variable ocupacion
     */
    public int getOcupacion(){
        return ocupacion;
    }

    /**
     * Devuelve verdadero si la lista vuelos esta llena, si no, devuelve falso
     *  @return estaLlena
     */
    public boolean estaLlena(){
        boolean estaLlena = false;
        if (ocupacion == ListaVuelos.length){
            estaLlena = true;
        }
        return estaLlena;
    }

    /**
     * Getter  para conseguir un vuelo
     * @param i variable que toma la posicion del vuelo dentro del array
     * @return Devuelve la posicion (i) de un vuelo dentro del array ListaVuelos
     */
    //Devuelve el objeto vuelo que está en la posición i del array
    public Vuelo getVuelo(int i){
        return ListaVuelos[i];
    }

    /**
     * Inserta un vuelo en el array ListaVuelos
     * @param vuelo vuelo que se quiere insertar en la lista
     * @return devuelve true si se ha insertado el vuelo o false si no se ha añadido
     */
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

    /**
     * Busca un vuelo por medio de su id
     * @param id codigo que identifica a un vuelo
     * @return devuelve el vuelo correspondiente al id introducido por parametro
     */
    //Devuelve el objeto vuelo que tenga el identificador igual al parámetro id
    //Si no lo encuentra, devolverá null
    public Vuelo buscarVuelo (String id){
        Vuelo resultado = null;
        for (int i = 0; i < getOcupacion(); i++){
            if (Objects.equals(ListaVuelos[i].getID(), id)){
                resultado = ListaVuelos[i];
            }
        }
        return resultado;
    }

    /**
     * Busca vuelos por medio del codigo de origen del aeropuerto, codigo de destino del aeropuerto y la fecha de salida de este
     * @param codigoOrigen codigo que depende del aeropuerto de salida de un vuelo
     * @param codigoDestino codigo que depende del aeropuerto de destino de un vuelo
     * @param fecha fecha que esta determinada por la salida de un vuelo especifico
     * @return devuelve el vuelo buscado mediante los parametros introducidos (busca un vuelo en el array) y si este tiene
     * las caracteristicas introducidas lo toma
     */
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

    /**
     * Imprime la lista de los vuelos sigueindo el formato especificado del toString de la clase Vuelo
     */
    //Muestra por pantalla los vuelos siguiendo el formato de los ejemplos del enunciado
    public void listarVuelos() {
        for (int i = 0; i < getOcupacion(); i++){
            System.out.println(ListaVuelos[i].toString());
        }
    }

    /**
     * Selecciona el vuelo, si existe, del ID indicado por parametro. Ademas, comprueba si este esta cancelado o no existe
     * @param teclado el usuario introduce el ID del vuelo que desea
     * @param mensaje mensaje que se muestra por pantalla
     * @param cancelar cancela la busqueda del vuelo
     * @return devuelve el vuelo seleccionado y si cumple los requisitos (que exista y que tenga ID correspondiente)
     */
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
                    System.out.println("ID de vuelo no encontrado.");
                }else{
                    vueloTerminado = true;
                }
            }
        }while (!vueloTerminado);
        return vueloExistente;
    }

    /**
     * Funcion que escribe o sobreescribe en el fichero CSV todos los datos de los vuelos que se realizan. En el incluye toda la informacion
     * como los aeropuertos, la fecha de salida y de llegada, el ID del vuelo y el precio
     * @param fichero fichero en el que se escriben todos los datos
     * @return devuelve true si se ha podido escribir los datos y si no se ha podido devuelve false
     */
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

    /**
     * Funcion que genera una lista con los vuelos que se realizan, tomando los datos del fichero Csv
     * @param fichero fichero donde lee los datos de los vuelos
     * @param capacidad capacidad del array ListaVuelos
     * @param aeropuertos son los aeropuertos de origen y de destino
     * @param aviones son los aviones que realizan los vuelos
     * @return devuelve la lista que ha creado
     */
    //Métodos estáticos
    //Genera una lista de vuelos a partir del fichero CSV, usando los límites especificados como argumentos para la capacidad
    //de la lista
    public static ListaVuelos leerVuelosCsv(String fichero, int capacidad, ListaAeropuertos aeropuertos, ListaAviones aviones){
        ListaVuelos ListaVuelosCsv = new ListaVuelos(capacidad);
        Scanner teclado = null;
        String entrada;
        try {
            teclado = new Scanner(new FileReader(fichero));
            String ID;
            String matricula;
            String codigoOrigen, codigoDestino;
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
                codigoOrigen = matrizVuelo[2];
                torigen = Integer.parseInt(matrizVuelo[3]);
                salida = Fecha.fromString(matrizVuelo[4]);
                codigoDestino = matrizVuelo[5];
                tdestino = Integer.parseInt(matrizVuelo[6]);
                llegada = Fecha.fromString(matrizVuelo[7]);
                precio = Double.parseDouble(matrizVuelo[8]);
                AvionUso = aviones.buscarAvion(matricula);
                origen = aeropuertos.buscarAeropuerto(codigoOrigen);
                destino = aeropuertos.buscarAeropuerto(codigoDestino);
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