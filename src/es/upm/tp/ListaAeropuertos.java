package es.upm.tp;
import java.io.*;
import java.util.Objects;
import java.util.Scanner;
import java.util.Scanner;

/**
 * ListaAeropuertos es una clase que encapsula las variables enteras usadas para definir los aeropuertos,
 * así como también contiene funciones bara buscar, seleccionar e insertar aeropuertos en el array de nombre Listaaeropuertos
 * Tambien escribe un fichero.csv con los datos de cada aeropuerto
 *
 * @author      Cesar Jimenez Laguna
 * @author      Iñaki Ramos Iturria
 * @version     1.0
 */

public class ListaAeropuertos {

    /**
     * Atributo que devuelve la capacidad de la ListaAeropuerto
     */
    private int capacidad;

    /**
     * Atributo que contiene la ocupacion del aeropuerto dentro de la lista
     */
    private int ocupacion;

    /**
     * Atributo que contiene el array donde se guardan los aeropuertos
     */
    private Aeropuerto[] listaAeropuertos;

    /**
     * Constructor que crea un array con la cantidad de aeropuertos recibidos
     * @param capacidad especifica la capacidad del aeropuerto
     */
    public ListaAeropuertos(int capacidad){
        this.capacidad = capacidad;
        listaAeropuertos = new Aeropuerto[capacidad];
    }

    /**
     * Getter del atributo ocupacion
     * @return Devuelve la cantidad de aeropuertos que hay en la listaAeropuertos como una variable ocupacion
     */
    public int getOcupacion(){ //Cantidad de aeropuertos que tiene
        return ocupacion;
    }

    /**
     * Devuelve verdadero si la listaAeropuertos esta llena, si no, devuelve falso
     * @return estaLlena
     */
    public boolean estaLlena(){
        boolean estaLlena;
        if (ocupacion == listaAeropuertos.length){
            estaLlena = true;
        }else{
            estaLlena = false;
        }
        return estaLlena;
    }

    /**
     * Getter para conseguir el aeropuerto
     * @param i especifica la posicion del aeropuerto dentro del array
     * @return Devuelve el aeropuerto que se encuentre en la posicion recibida por el parametro
     */
    public Aeropuerto getAeropuerto(int i){
        return listaAeropuertos[i];
    }

    /**
     * Inserta un aeropuerto en el array ListaAeropuertos
     * @param aeropuerto es el aeropuerto que se quiere insertar en la lista
     * @return devuelve true si se ha insertado el aeropuerto o false si no se ha añadido correctamente
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

    /**
     * Devuelve el aeropuerto que coincide con el pedido por los parametros
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

    /**
     * Selecciona el aeropuerto existente que coincide con el codigo solicitado
     * @param teclado codigo IATA que introduce el usuario
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

    /**
     * Escribe en un fichero los aeropuertos con sus caracteristicas
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

    /**
     * Crea una lista con la funcion escribirAeropuertosCsv, tomando los datos del fichero Csv
     * @param fichero fichero donde se guardan la lista de los aeropuertos seleccionados
     * @param capacidad es la capacidad de la lista que contiene los aeropuertos
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
            String codigo;
            String nombre;
            double latitud;
            double longitud;
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