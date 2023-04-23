package es.upm.tp;

import java.io.*;
import java.util.Scanner;

/**
 * ListaBilletes es una clase que encapsula las variables enteras usadas para definir los billetes,
 * así como también contiene funciones bara buscar, seleccionar e insertar billetes en el array de nombre listaBilletes
 * Tambien escribe un fichero.csv con los datos de cada billete
 *
 * @author      Cesar Jimenez Laguna
 * @author      Iñaki Ramos Iturria
 * @version     1.0
 */
public class ListaBilletes {

    /**
     * Atributo que contiene la capacidad de la ListaBilletes
     */
    private int capacidad;

    /**
     * Atributo que contiene la ocupacion de un billete dentro de la ListaBilletes
     */
    private int ocupacion;

    /**
     * Atributo que contiene el array donde estan los billetes
     */
    private Billete[] ListaBilletes;

    /**
     * Constructor que crea un array con la cantidad de billetes recibidos.
     * @param capacidad especifica la capacidad de la lista que contiene los billetes
     */
    public ListaBilletes(int capacidad){
        this.ocupacion = 0;
        this.capacidad = capacidad;
        ListaBilletes = new Billete[capacidad];
    }

    /**
     * Getter del atributo ocupacion
     * @return devuelve la cantidad de billetes que hay en la listaBilletes como una variable ocupacion
     */
    public int getOcupacion(){
        return ocupacion;
    }

    /**
     * Devuelve verdadero si la lista billetes esta llena, si no, devuelve falso
     *  @return estaLlena
     */
    public boolean estaLlena(){
        boolean estaLlena = false;
        if (capacidad == getOcupacion()){
            estaLlena = true;
        }
        return estaLlena;
        //return capacidad == getOcupacion();
        /*
        if (ocupacion == ListaBilletes.length){
            estaLlena = true;
        }
        return estaLlena;
         */
    }

    /**
     * Getter  para conseguir un vuelo
     * @param i variable que toma la posicion del billete dentro del array
     * @return Devuelve la posicion (i) de un vuelo dentro del array ListaVuelos
     */
    public Billete getBillete(int i){
        return ListaBilletes[i];
    }

    /**
     * Funcion que inserta el billete pasado por parametro al array listaBilletes
     * @param billete Billete que si quiere insertar
     * @return Devuelve True si se ha podido insertar false si no se ha podido
     */
    public boolean insertarBillete (Billete billete){
        boolean insertar = false;
        if (!estaLlena()){
            ListaBilletes[ocupacion] = billete;
            ocupacion++;
            insertar = true;
        }
        return insertar;
    }

    /**
     * Funcion que busca el billete que coincide con el localizador pasado por parametro
     * @param localizador Localizador que es unico de cada billete
     * @return Devuelve el billete que se ha buscado
     */
    public Billete buscarBillete (String localizador){
        Billete resultado = null;
        for (int i = 0; i < ocupacion; i++){
            if (ListaBilletes[i].getLocalizador().equals(localizador)){
                resultado = ListaBilletes[i];
            }
        }
        return resultado;
    }

    /**
     * Cancion que busca el billete que corresponde con la fila y columna pasada por parametros
     * @param idVuelo ID del vuelo que corresponde con el billete
     * @param fila Fila del billete en los asientos del avion
     * @param columna Columna del billete en los asientos del avion
     * @return Devuelve el billete que coincide con los datos pasado por parametro
     */
    public Billete buscarBillete (String idVuelo, int fila, int columna){
        Billete resultado = null;
        for (int i = 0; i < ocupacion; i++){
            if (ListaBilletes[i].getVuelo().getID().equals(idVuelo)){
                if ((ListaBilletes[i].getFila() == fila) && (ListaBilletes[i].getColumna() == columna)){
                    resultado = ListaBilletes[i];
                }
            }
        }
        return resultado;
    }

    /**
     * Funcion que elimina el billete que corresponde con el localizador pasado por parametro
     * @param localizador Localizador que es unico de cada billete
     * @return Devuelve True si se ha podido eliminar y false si no se ha podido
     */
    public boolean eliminarBillete (String localizador){
        boolean eliminado = true;
        for (int i = 0; i < ocupacion; i++){
            if (ListaBilletes[i].getLocalizador().equals(localizador)){
                for (int j = i; j < ocupacion-1; j ++){
                    ListaBilletes[i] = ListaBilletes[i+1];
                }
            }
        }
        ocupacion--;
        return eliminado;
    }

    /**
     * Funcion que muestra por pantalla los billetes de la lista
     */
    // Muestra por pantalla los billetes de la lista
    public void listarBilletes(){
        for (int i = 0; i < ocupacion ; i++){
            System.out.println(ListaBilletes[i].toString());
        }
    }

    /**
     * Funcion que selecciona un billete mediante el localizador que aporta el usuario
     * @param teclado Teclado por donde el usuario escribe la informacion pedida
     * @param mensaje Mensaje que se le muestra al usuario de lo que debe aportar
     * @return Devuelve el billete seleccionado si coincide con el localizador que dice el usuario
     */
    // Permite seleccionar un billete existente a partir de su localizador, usando el mensaje pasado como argumento para la solicitud
    // y siguiendo el orden y los textos mostrados en el enunciado
    // La función solicita repetidamente hasta que se introduzca un localizador correcto
    public Billete seleccionarBillete(Scanner teclado, String mensaje) {
        Billete billeteSeleccionado = null;
        do {
            System.out.print(mensaje);
            String localizador = teclado.nextLine();
            billeteSeleccionado = buscarBillete(localizador);
            if (billeteSeleccionado == null){
                System.out.println("Localizador no encontrado.");
            }
        } while (billeteSeleccionado == null);
        return billeteSeleccionado;
    }

    /**
     * Funcion que añade los billetes al final del archivo CSV
     * @param fichero Fichero en el que ese sobre escriben los datos de los billetes
     * @return Devuelve true si te ha podido escribir la informacion y false si no se ha podido
     */
    // Añade los billetes al final de un fichero CSV, sin sobreescribirlo
    public boolean aniadirBilletesCsv(String fichero){
        FileWriter Filewriter = null;
        boolean billeteAñadido = true;
        try{
            Filewriter = new FileWriter(fichero, true);
            for (int i = 0; i < ocupacion; i ++){
                Billete billeteActual = ListaBilletes[i];
                Filewriter.write(billeteActual.getLocalizador() + ";" + billeteActual.getVuelo().getID() + ";" + billeteActual.getPasajero().getDNI() +
                        ";" + billeteActual.getTipo().name() + ";" + billeteActual.getFila() + ";" + billeteActual.getColumna() + ";" + billeteActual.getPrecio());
                if (i != ocupacion-1){
                    Filewriter.write("\n");
                }
            }
        } catch (FileNotFoundException excepcion) {
            System.out.println("El fichero " + fichero + " no encontrado");
            billeteAñadido = false;
        } catch (IOException excepcion2) {
            System.out.println("Error de escritura en " + fichero + ";");
            billeteAñadido = false;
        } finally {
            if (Filewriter == null){
                try {
                    Filewriter.close();
                } catch (IOException excepcion3){
                    System.out.println("Error de cierre del fichero " + fichero + ".");
                    billeteAñadido = false;
                }
            }
        }
        return billeteAñadido;
    }

    // Métodos estáticos

    /**
     * Funcion que lee los billetes del fichero CSV y los añade a la lista de sus respectivos vuelos y pasajeros
     * @param ficheroBilletes Fichero donde se encuentran los billetes
     * @param vuelos Vuelo actual
     * @param pasajeros Pasajeros del vuelo
     */
    // Lee los billetes del fichero CSV y los añade a las lista de sus respectivos Vuelos y Pasajeros
    public static void leerBilletesCsv(String ficheroBilletes, ListaVuelos vuelos, ListaPasajeros pasajeros) {
        Scanner teclado = null;
        String entrada;
        boolean leerLogrado = true;
        String localizarBillete;
        String idVuelo;
        String dni;
        String tipo;
        int filas;
        int columnas;
        double precio;
        Pasajero pasajeroActual;
        Vuelo vueloActual;
        try {
            teclado = new Scanner(new FileReader(ficheroBilletes));
            do {
                entrada = teclado.nextLine();
                String[] arrayCsvBilletes = entrada.split(";");
                localizarBillete = arrayCsvBilletes[0];
                idVuelo = arrayCsvBilletes[1];
                dni = arrayCsvBilletes[2];
                tipo = arrayCsvBilletes[3];
                filas = Integer.parseInt(arrayCsvBilletes[4]);
                columnas = Integer.parseInt(arrayCsvBilletes[5]);
                precio = Double.parseDouble(arrayCsvBilletes[6]);
                vueloActual = vuelos.buscarVuelo(idVuelo);
                pasajeroActual = pasajeros.buscarPasajeroDNI(dni);
                Billete billete = new Billete(localizarBillete, vueloActual,pasajeroActual,Billete.TIPO.valueOf(tipo),filas,columnas,precio);
                pasajeroActual.aniadirBillete(billete);
                vueloActual.ocuparAsiento(billete);
            }while(teclado.hasNext());
        } catch (FileNotFoundException e) {
            System.out.println("El fichero " + ficheroBilletes + " no se ha encontrado.");
        }finally {
            if (teclado != null){
                teclado.close();
            }
        }
    }
}