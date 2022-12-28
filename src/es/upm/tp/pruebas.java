package es.upm.tp;

import java.util.Random;
import java.util.Scanner;

public class pruebas {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        //Pide crear un constructor
        Avion avion = new Avion("Boeing","737","EC-LKE", 6,30,2000.0);
        Aeropuerto aeropuerto1 = new Aeropuerto("Adolfo Suarez Madrid - Barajas", "MAD", 40.4927751,-3.5778,4);
        Aeropuerto aeropuerto2 = new Aeropuerto("Josep Tarradellas Barcelona-El Prat","BCN",41.289182,2.0746423,2);
        Vuelo vuelo = new Vuelo("PM1112", avion, aeropuerto1, 1, new Fecha(1, 1, 2023, 1, 1, 1), aeropuerto2, 1, new Fecha(2, 2, 2023, 2, 2, 2), 100.0);
        System.out.println(vuelo.toString());
    }
}
/*  // Devuelve una cadena con información completa del vuelo
    //Ejemplo: Vuelo PM0066 de Josep Tarradellas Barcelona-El Prat(BCN) T2 (01/01/2023 08:15:00) a Gran Canaria(LPA) T1 (01/01/2023 11:00:05) en Boeing 747(EC-LKD) por 182,52€, asientos libres: 409
    public String toString() {
        return "Vuelo " + id + " de " + origen.getNombre() + "(" + origen.getCodigo() + ") " + terminalOrigen + " (" + salida + ") a "
                + destino.getNombre() + "(" + destino.getCodigo() + ") " + terminalDestino + " (" + llegada + ") en " + avion.getMarca() + avion.getModelo()
                + "(" + avion.getMatricula() + ") por " + precio + "€, asientos libres: " + numAsientosLibres();
    }

    // Devuelve una cadena con información abreviada del vuelo
    //Ejemplo: Vuelo PM0066 de Josep Tarradellas Barcelona-El Prat(BCN) T2 (01/01/2023 08:15:00) a Gran Canaria(LPA) T1 (01/01/2023 11:00:05)
    public String toStringSimple() {
        return "Vuelo " + id + " de " + origen.getNombre() + "(" + origen.getCodigo() + ") " + terminalOrigen + " (" + salida + ") a "
                + destino.getNombre() + "(" + destino.getCodigo() + ") " + terminalDestino + " (" + llegada + ")";
    }
 */