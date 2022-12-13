package es.upm.tp;

import java.sql.SQLOutput;
import java.util.Scanner;

/**
 * Description of the class
 *
 * @author      Cesar Gimenez Laguna
 * @author      Iñaki Ramos Iturria
 * @version     1.0
 */

public class Utilidades {
    // Solicita un número repetidamente hasta que se introduzca uno correcto (dentro de los límites)
    public static int leerNumero(Scanner teclado, String mensaje, int minimo, int maximo) {
        int num;
        do {
            System.out.print(mensaje);
            num = Integer.parseInt(teclado.nextLine());
        } while (num < minimo || num > maximo);
        return num;
    }

    public static long leerNumero(Scanner teclado, String mensaje, long minimo, long maximo) {
        long num;
        do {
            System.out.print(mensaje);
            num = Long.parseLong(teclado.nextLine());
        } while (num < minimo || num > maximo);
        return num;
    }

    public static double leerNumero(Scanner teclado, String mensaje, double minimo, double maximo) {
        double num;
        do {
            System.out.print(mensaje);
            num = Double.parseDouble(teclado.nextLine().replace(',','.'));
        } while (num < minimo || num > maximo);
        return num;
    }

    public static char leerLetra(Scanner teclado, String mensaje, char minimo, char maximo) {
        char character;
        do {
            System.out.print(mensaje);
            character = teclado.nextLine().charAt(0);
        } while (character < minimo || character > maximo);
        return character;
    }

    // Solicita una fecha repetidamente hasta que se introduzca una correcta
    public static Fecha leerFecha(Scanner teclado, String mensaje) {
        int dia, mes, anio;
        System.out.println(mensaje);
        do {
            dia =leerNumero(teclado,"Ingrese día:",1,31);
            mes =leerNumero(teclado,"Ingrese mes:",1,12);
            anio =leerNumero(teclado,"Ingrese año:",1900,3000);
            if (Fecha.comprobarFecha(dia, mes, anio) == false){
                System.out.println("Fecha u hora introducida incorrecta.");
            }
        }while(Fecha.comprobarFecha(dia, mes, anio) == false);
        return new Fecha(dia, mes, anio);
    }

        // Solicita una fecha y hora repetidamente hasta que se introduzcan unas correctas
        public static Fecha leerFechaHora(Scanner teclado, String mensaje) {
            int hora, minuto, segundo, dia, mes, anio;
            System.out.println(mensaje);
            do {
                dia = leerNumero(teclado,"Ingrese día:",1,31);
                mes = leerNumero(teclado,"Ingrese mes:",1,12);
                anio =leerNumero(teclado,"Ingrese año:",1900,3000);
                hora = leerNumero(teclado,"Ingrese hora:",0,23);
                minuto =leerNumero(teclado,"Ingrese minuto:",0,59);
                segundo =leerNumero(teclado,"Ingrese segundo:",0,59);
                if (Fecha.comprobarFecha(dia, mes, anio) == false || Fecha.comprobarHora(hora, minuto, segundo) == false) {
                    System.out.println("Fecha u hora introducida incorrecta.");
                }
            }while (Fecha.comprobarFecha(dia, mes, anio) == false || Fecha.comprobarHora(hora, minuto, segundo) == false);
            return new Fecha(dia, mes, anio, hora, minuto, segundo);
    }
}