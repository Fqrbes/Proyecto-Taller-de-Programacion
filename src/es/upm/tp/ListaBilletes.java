package es.upm.tp;

import java.io.*;
import java.sql.SQLOutput;
import java.util.Objects;
import java.util.Scanner;

/**
 * Description of the class
 *
 * @author
 * @author
 * @version     1.0
 */
public class ListaBilletes {

    private int capacidad;

    private int ocupacion;

    private Billete[] ListaBilletes;

    /**
     * Constructor of the class
     *
     * @param capacidad
     */
    public ListaBilletes(int capacidad){
        this.ocupacion = 0;
        this.capacidad = capacidad;
        ListaBilletes = new Billete[capacidad];
    }

    public int getOcupacion(){
        return ocupacion;
    }

    public boolean estaLlena(){
        boolean estaLlena = false;
        if (ocupacion == ListaBilletes.length){
            estaLlena = true;
        }
        return estaLlena;
    }

    public Billete getBillete(int i){
        return ListaBilletes[i];
    }

    public boolean insertarBillete (Billete billete){
        boolean insertar = false;
        if (!estaLlena()){
            ListaBilletes[ocupacion] = billete;
            ocupacion++;
            insertar = true;
        }
        return insertar;
    }

    public Billete buscarBillete (String localizador){
        Billete resultado = null;
        for (int i = 0; i < ocupacion; i++){
            if (ListaBilletes[i].getLocalizador().equals(localizador)){
                resultado = ListaBilletes[i];
            }
        }
        return resultado;
    }

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

    // Muestra por pantalla los billetes de la lista
    public void listarBilletes(){
        for (int i = 0; i < ocupacion ; i++){
            System.out.println(ListaBilletes[i].toString());
        }
    }

    // Permite seleccionar un billete existente a partir de su localizador, usando el mensaje pasado como argumento para la solicitud
    // y siguiendo el orden y los textos mostrados en el enunciado
    // La función solicita repetidamente hasta que se introduzca un localizador correcto
    public Billete seleccionarBillete(Scanner teclado, String mensaje) {
        Billete billeteSeleccionado = null;
        do {
            System.out.println(mensaje);
            String localizador = teclado.nextLine();
            billeteSeleccionado = buscarBillete(localizador);
            if (billeteSeleccionado == null){
                System.out.println("Localizador no encontrado.");
            }
        } while (billeteSeleccionado == null);
        return billeteSeleccionado;
    }

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