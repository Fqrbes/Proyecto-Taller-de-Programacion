package es.upm.tp;
import java.io.*;
import java.util.Objects;
import java.util.Scanner;
import java.util.Scanner;

/**
 * Description of the class
 *
 * @author      Cesar Jimenez Laguna
 * @author      Iñaki Ramos Iturria
 * @version     1.0
 */

    public class ListaAeropuertos {
        //Atributos privados, todos.

    /**
     * Atributo que devuelve la capacidad del aeropuerto
     */
    private int capacidad;

    /**
     * Atributo que contiene la ocupacion del aeropuerto
     */
    private int ocupacion;

    /**
     * Atributo que contiene el vector donde estan los aeropuertos
     */
    private Aeropuerto[] listaAeropuertos;

    /**
     * Constructor of the class. Constructor que crea un vector con la cantidad de aeropuertos recibidos
     *
     * @param capacidad especifica la capacidad del aeropuerto
     */
    public ListaAeropuertos(int capacidad){
        this.capacidad = capacidad;
        listaAeropuertos = new Aeropuerto[capacidad];
    }

    /** Devuelve la cantidad de aeropuertos que hay en la listaAeropuertos
     * @return especifica la ocupacion
     */
    public int getOcupacion(){ //Cantidad de aeropuertos que tiene
        return ocupacion;
    }

    /** Devuelve verdadero si la lista aeropuertos esta llena, si no, devuelve falso
     * @return especifica verdadero o falso segun como este listaAeropuertos
     */
    public boolean estaLlena(){
        boolean estaLlena;
        if (ocupacion == listaAeropuertos.length){
            estaLlena = true;
        }else{
            estaLlena = false;
        }
        return estaLlena;

        //capacidad == ocupacion;
    }

    /** Devuelve el aeropuerto que se encuentre en la posicion recibida por el parametro
     * @param i especifica la posicion del aeropuerto
     * @return devuelve el aeropuerto pedido por parametro
     */
    public Aeropuerto getAeropuerto(int i){
        return listaAeropuertos[i];
    }


    /** Inserta un aeropuerto en el array ListaAeropuertos
     * @param aeropuerto Es el aeropuerto que se quiere insertar en la lista
     * @return devuelve true si se ha insertado el aeropuerto o false si no se ha añadido
     */
    public boolean insertarAeropuerto(Aeropuerto aeropuerto){ //añadir a la lista el aeropuerto, si esta no esta llena.
        boolean insertar = false;
        if (!estaLlena()){
          listaAeropuertos[ocupacion] = aeropuerto;
          ocupacion ++;
          insertar = true;
        }
        else {
            insertar = false;
        }
        return insertar;
    }

    /** Devuelve el aeropuerto que coincide, con el pedido por parametros
     * @param codigo especifica que aeropuerto se esta considerando y comparando para ver si existe
     * @return devuelve el aeropuerto que coincide con el codigo pedido por parametro
     */
    public Aeropuerto buscarAeropuerto(String codigo){ //Comparar el codigo del aeropuerto
        Aeropuerto resultado = null;
        for (int i = 0; i < ocupacion; i ++){
            if (Objects.equals(listaAeropuertos[i].getCodigo(), codigo)){
                resultado = listaAeropuertos[i];
            }
        }
        return resultado;
    }

    /** Selecciona el aeropuerto existente que coincide con el codigo solicitado
     * @param teclado codigo que introduce el usuario
     * @param mensaje mensaje que se muestra por pantalla
     * @return devuelve el aeropuerto, si este existe
     */
    // Permite seleccionar un aeropuerto existente a partir de su código, usando el mensaje pasado como argumento para la solicitud
    // y siguiendo el orden y los textos mostrados en el enunciado
    // La función solicita repetidamente el código hasta que se introduzca uno correcto
    public Aeropuerto seleccionarAeropuerto(Scanner teclado, String mensaje){
            String codigoIATA;
            do {
                System.out.print(mensaje);
                codigoIATA = teclado.nextLine();
                //aeropuerto = buscarAeropuerto(codigoIATA);
                if (buscarAeropuerto(codigoIATA) == null){
                    System.out.println("Código de aeropuerto no encontrado.");
                }
            }while (buscarAeropuerto(codigoIATA) == null);
            return buscarAeropuerto(codigoIATA);
        }

    /** Crea una lista con todos los aeropuertos y sus datos, que se van introduciendo
     * @param nombre nombre del fichero donde se guarda la lista de aeropuerto
     * @return devuelve la lista con la informacion de los aeropuertos
     */
    // Genera un fichero CSV con la lista de aeropuertos, sobreescribiendolo
    public boolean escribirAeropuertosCsv(String nombre){
        PrintWriter printW = null;
        boolean copiado= true;
        try{
            printW = new PrintWriter(nombre);
            for (int i = 0; i < ocupacion; i ++){
                printW.println(listaAeropuertos[i].getNombre() + ";" + listaAeropuertos[i].getCodigo() + ";"
                        + listaAeropuertos[i].getLatitud() + ";" + listaAeropuertos[i].getLongitud() + ";" + listaAeropuertos[i].getTerminales());
            }
        } catch (FileNotFoundException e) {
            System.out.println("El fichero " + nombre + " no encontrado");
            copiado = false;
        }finally{
            if (printW != null){
                printW.close();
            }
        }
        return copiado;
    }

    /** Crea una lista con la funcion escribirAeropuertosCsv, tomando los datos del fichero Csv
     * @param fichero fichero donde se guardan las listas de los aeropuertos seleccionados
     * @param capacidad es la capacidad del aeropuerto al que se hace referencia
     * @return devuelve la listaImportada desde el fichero CSV
     */
    //Métodos estáticos
    //Genera una lista de aeropuertos a partir del fichero CSV, usando el argumento como   
    //capacidad máxima de la lista
    public static ListaAeropuertos leerAeropuertosCsv(String fichero, int capacidad){
        ListaAeropuertos ListaAeropuertosCsv = new ListaAeropuertos(capacidad);
        Scanner teclado = null;
        String entrada;
        try{
            teclado = new Scanner(new FileReader(fichero));
            String codigo, nombre;
            double latitud, longitud;
            int terminales;
            do{
                entrada = teclado.nextLine();
                String [] matrizAeropuerto = entrada.split(";");
                nombre = matrizAeropuerto[0];
                codigo = matrizAeropuerto[1];
                latitud = Double.parseDouble(matrizAeropuerto[2]);
                longitud = Double.parseDouble(matrizAeropuerto[3]);
                terminales = Integer.parseInt(matrizAeropuerto[4]);
                ListaAeropuertosCsv.insertarAeropuerto(new Aeropuerto(nombre, codigo, latitud, longitud, terminales));
            }while(teclado.hasNext());
        } catch (FileNotFoundException e) {
            System.out.println("El fichero " + fichero + " no se ha encontrado");
        }finally{
            if (teclado != null){
                teclado.close();
            }
        }
        return ListaAeropuertosCsv;
    }
}