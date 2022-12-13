package es.upm.tp;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Objects;
import java.util.Scanner;

/**
 * Description of the class
 *
 * @author      Cesar Gimenez Laguna
 * @author      Iñaki Ramos Iturria
 * @version     1.0
 */
public class ListaPasajeros {

    private int capacidad;

    private int ocupacion;

    private Pasajero[] listaPasajeros;

    /**
     * Constructor of the class
     *
     * @param capacidad
     */
    public ListaPasajeros(int capacidad){
        listaPasajeros = new Pasajero[capacidad];
    }

    public int getOcupacion(){
        return ocupacion;
    }

    public boolean estaLlena(){
        boolean estaLlena = false;
        if (ocupacion == listaPasajeros.length){
            estaLlena = true;
        }
        return estaLlena;
    }

    public Pasajero getPasajero(int i){
        return listaPasajeros[i];
    }

    public boolean insertarPasajero(Pasajero pasajero){
        boolean insertar = false;
        if (estaLlena() == false){
            listaPasajeros[ocupacion] = pasajero;
            ocupacion ++;
            insertar = true;
        }
        return insertar;
    }

    public Pasajero buscarPasajeroDNI(String dni){
        Pasajero resultado = null;
        boolean encontrado = false; //IMPORTANTE
        for (int i = 0; i < ocupacion && !encontrado; i++){
            if (listaPasajeros[i].getDNI().equalsIgnoreCase(dni)){
                resultado = listaPasajeros[i];
                encontrado = true;
            }
        }
        return resultado;
    }

    public Pasajero buscarPasajeroEmail(String email){
        Pasajero resultado = null;
        for (int i = 0; i < ocupacion; i ++){
            if (Objects.equals(listaPasajeros[i].getEmail(), email)){
                resultado = listaPasajeros[i];
            }
        }
        return resultado;
    }

    // Permite seleccionar un pasajero existente a partir de su DNI, usando el mensaje pasado como argumento para la solicitud
    // y siguiendo el orden y los textos mostrados en el enunciado
    // La función solicita repetidamente hasta que se introduzca un DNI correcto
    public Pasajero seleccionarPasajero(Scanner teclado, String mensaje){
        String DNI;
        do {
            System.out.print(mensaje);
            DNI = teclado.nextLine();
            if (buscarPasajeroDNI(DNI) == null){
                System.out.println("DNI incorrecto.");
            }
        }while (buscarPasajeroDNI(DNI) == null);
        return buscarPasajeroDNI(DNI);
    }

    // Genera un fichero CSV con la lista de pasajeros, sobreescribiendolo
    public boolean escribirPasajerosCsv(String fichero){
        PrintWriter printW = null;
        boolean copiado= true;
        try{
            printW = new PrintWriter(fichero);
            for (int i = 0; i < ocupacion; i ++){
                printW.println(listaPasajeros[i].getNombre() + ";" + listaPasajeros[i].getApellidos() + ";"
                        + listaPasajeros[i].getNumeroDNI() + ";" + listaPasajeros[i].getLetraDNI() + ";" + listaPasajeros[i].getEmail());
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
    // Genera una lista de pasajeros a partir del fichero CSV, usando los límites especificados como argumentos para la capacidad
    // de la lista y el número de billetes máximo por pasajero
    public static ListaPasajeros leerPasajerosCsv(String fichero, int capacidad, int maxBilletesPasajeros){
        ListaPasajeros ListaPasajerosCsv = new ListaPasajeros(capacidad);
        Scanner teclado = null;
        String entrada;
        try{
            teclado = new Scanner(new FileReader(fichero));
            String nombre;
            String appellidos;
            String email;
            char letraDNI;
            long numeroDNI;
            while(teclado.hasNext()){
                entrada = teclado.nextLine();
                String[] matrizPasajeros = entrada.split(";");
                nombre = matrizPasajeros[0];
                appellidos = matrizPasajeros[1];
                numeroDNI = Long.parseLong(matrizPasajeros[2]);
                letraDNI = matrizPasajeros[3].charAt(0);
                email = matrizPasajeros[4];
                Pasajero pasajeroCreado = new Pasajero(nombre, appellidos, numeroDNI, letraDNI, email, maxBilletesPasajeros);
                ListaPasajerosCsv.insertarPasajero(pasajeroCreado);
            }
        }catch (FileNotFoundException e){
            System.out.println("El fichero " + fichero + " no se ha encontrado");
        }finally {
            if (teclado != null){
                teclado.close();
            }
        }
        return ListaPasajerosCsv;
    }
}