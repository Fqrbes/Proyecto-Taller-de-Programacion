package es.upm.tp;

import java.util.Scanner;

/**
 * Description of the class
 *
 * @author      Cesar Jimenez Laguna
 * @author      Iñaki Ramos Iturria
 * @version     1.0
 */
public class Pasajero {

    private String nombre;

    private String apellidos;

    private long numeroDNI;

    private char letraDNI;

    private String email;

    private int maxBilletes;

    /**
     * Constructor of the class
     *
     * @param nombre
     * @param apellidos
     * @param numeroDNI
     * @param letraDNI
     * @param email
     */
    public Pasajero(String nombre, String apellidos, long numeroDNI, char letraDNI, String email, int maxBilletes){
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.numeroDNI = numeroDNI;
        this.letraDNI = letraDNI;
        this.email = email;
        this.maxBilletes = maxBilletes;
    }

    public String getNombre(){
        return nombre;
    }

    public String getApellidos(){
        return apellidos;
    }

    public long getNumeroDNI(){
        return numeroDNI;
    }

    public char getLetraDNI(){
        return letraDNI;
    }

    // Ejemplo: 00123456S
    public String getDNI(){
        return String.valueOf(numeroDNI) + letraDNI;
    }

    public String getEmail(){
        return email;
    }

    // Texto que debe generar: Víctor Rodríguez Fernández, 00123456S, victor.rfernandez@upm.es
    public String toString(){
        return nombre + " " + apellidos +", " + getDNI() +", " + email;
        }

    //public int numBilletesComprado();

    //public boolean maxBilletesAlcanzado();

    //public Billete getBillete(int i);

    //public boolean aniadirBillete(Billete billete);

    //public Billete buscarBillete(String localizador);

    // comentario; Elimina el billete de la lista de billetes del pasajero
    //public boolean cancelarBillete(String localizador);

    //public void listarBilletes();

    // comentario; Encapsula la funcionalidad seleccionarBillete de ListaBilletes
    //public Billete seleccionarBillete(Scanner teclado, String mensaje);


    //Métodos estáticos

    // Crea un nuevo pasajero no repetido, pidiendo por teclado los datos necesarios al usuario en el orden 
    // y con los textos indicados en los ejemplos de ejecución del enunciado
    // La función solicita repetidamente los parametros hasta que sean correctos
    public static Pasajero altaPasajero(Scanner teclado, ListaPasajeros pasajeros, int maxBilletes) {
        Pasajero nuevoPasajero = null;
        System.out.print("Ingrese nombre:");
        String nombre = teclado.nextLine();
        System.out.print("Ingrese apellidos:");
        String apellidos = teclado.nextLine();
        long numero;
        char letra;
        boolean existeDni, existeEmail;
        String email;

        do {
            existeDni = false;
            numero = Utilidades.leerNumero(teclado, "Ingrese número de DNI:", 00000000L, 99999999L);
            letra = Utilidades.leerLetra(teclado, "Ingrese letra de DNI:", 'A','Z');
            if (pasajeros.buscarPasajeroDNI(Long.toString(numero) + letra) != null){
                existeDni = true;
                System.out.println("DNI ya existe.");
            }
            if (!correctoDNI(numero, letra)){
                System.out.println("DNI incorrecto.");
            }
        } while ((!correctoDNI(numero, letra)) || (existeDni));

        do {
            existeEmail = false;
            System.out.print("Ingrese email:");
            email = teclado.nextLine();
            if (pasajeros.buscarPasajeroEmail(email) != null){
                existeEmail = true;
                System.out.println("Email ya existe.");
            }
        }while(!correctoEmail(email) || (existeEmail));

        return nuevoPasajero = new Pasajero(nombre, apellidos, numero, letra, email, maxBilletes);
    }

    // Correcto: 00123456 S, incorrectos: 123456789 A, 12345678 0, 12345678 A
    public static boolean correctoDNI(long numero, char letra){
        boolean correcto = false;
        char [] letraDNI = {'T','R','W','A','G','M','Y','F','P','D','X','B','N','J','Z','S','Q','V','H','L','C','K','E'};
        int numLetra = (int) numero%23;
        if (letra == letraDNI[numLetra]){
            correcto = true;
        }
        return correcto;
    }

    // Correcto: cristian.ramirez@upm.es, incorrecto: cristian.ramirez@gmail.com, cristian-23@upm.es, cristian.@upm.es
    public static boolean correctoEmail(String email){
        String[] todoEmail = email.split("@");
        String primeraParte = todoEmail[0];
        String segundaParte = todoEmail[1];
        int longitud = primeraParte.length();
        boolean correcto = true;
        if (segundaParte.equals("alumnos.upm.es") || segundaParte.equals("upm.es")){
            if (primeraParte.charAt(0) == '.' || primeraParte.charAt(longitud-1) == '.'){
                System.out.println("Email incorrecto.");
                correcto = false;
            }
            else {
                for (int i = 0; i < longitud; i++){
                    if ((primeraParte.charAt(i) < 65 || primeraParte.charAt(i) > 90) && (primeraParte.charAt(i) < 97 || primeraParte.charAt(i) > 122) && primeraParte.charAt(i) != '.'){
                        System.out.println("Email incorrecto.");
                        correcto = false;
                    }
                }
            }
        }
        else {
            System.out.println("Email incorrecto.");
            correcto = false;
        }
        return correcto;
    }
}