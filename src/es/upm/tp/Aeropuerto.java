package es.upm.tp;

/**
 * Description of the class
 *
 * @author      Cesar Jimenez Laguna
 * @author      Iñaki Ramos Iturria
 * @version     1.0
 */

public class Aeropuerto {
    // Atributos privados, todos.

    /**
     * Atributo que contiene el nombre del aeropuerto
     */
    private String nombre;

    /**
     * Atributo que contiene el codigo IATA del aeropuerto
     */
    private String codigo;

    /**
     * Atributo que contiene la latitud del aeropuerto
     */
    private double latitud;

    /**
     * Atributo que contiene la longitud del aeropuerto
     */
    private double longitud;

    /**
     * Atributo que contiene las terminales del aeropuerto
     */
    private int terminales;

    /**
     * Constructor of the class. Constructor que crea un aeropuerto con los datos que recibe
     *
     * @param nombre especifica el nombre del aeropuerto
     * @param codigo especifica el codigo IATA del aeropuerto
     * @param latitud especifica la latitud del aeropuerto
     * @param longitud especifica la longitud del aeropuerto
     * @param terminales especifica las terminales del aeropuerto
     */

    public Aeropuerto(String nombre, String codigo, double latitud, double longitud, int terminales){
        this.nombre = nombre;
        this.codigo = codigo;
        this.latitud = latitud;
        this.longitud = longitud;
        this.terminales = terminales;
    }

    /** Devuelve el nombre del aeropuerto
     * @return nombre
     */
    public String getNombre() {
        return nombre;
    }

    /** Devuelve el codigo IATA del aeropuerto
     * @return codigo
     */
    public String getCodigo() {
        return codigo;
    }

    /** Devuelve la latitud del aeropuerto
     * @return latitud
     */
    public double getLatitud() {
        return latitud;
    }

    /** Devuelve la longitud del aeropuerto
     * @return longitud
     */
    public double getLongitud() {
        return longitud;
    }

    /** devuelve las terminales del aeropuerto
     * @return terminales
     */
    public int getTerminales() {
        return terminales;
    }

    /** Devuelve la distancia entre dos aeropuertos
     * @param destino eropuerto de destio, al que llega el avion
     * @return distancia entre el aeropuerto de salida y de destino
     */
    // Calcula la distancia entre el aeropuerto que recibe el mensaje y el aeropuerto "destino" siguiendo la fórmula del enunciado
    public double distancia(Aeropuerto destino){
        double lat_A = this.latitud;
        double lat_B = destino.latitud;
        double long_A = this.longitud;
        double long_B = destino.longitud;
        double distancia = Math.acos(Math.sin(Math.toRadians(lat_A)) * Math.sin(Math.toRadians(lat_B)) + Math.cos(Math.toRadians(lat_A))
                * Math.cos(Math.toRadians(lat_B)) * Math.cos(Math.toRadians(long_A) - Math.toRadians(long_B))) * 6378;
        return distancia;
    }

    /** Devuelve los datos del aeropuerto (nombre, codigo, latitud, longitud y numero de terminales)
     * @return nombre, codigo, latitud, longitud y numero de terminales del aeropuerto
     */
    // Crea un String con los datos de un aeropuerto con el siguiente formato:
    // Adolfo Suarez Madrid - Barajas(MAD), en (40.4927751 40.4927751), con 4 terminales
    public String toString(){
        return nombre + "(" + codigo + "), en (" + latitud + " " + longitud + "), con " + terminales + " terminales";
    }

    /** Devuelve el nombre del aeropuerto y el codigo IATA
     * @return nombre y codigo IATA del aeropuerto
     */
    // Crea un String con los datos de un aeropuerto con el siguiente formato:
    // Adolfo Suarez Madrid - Barajas(MAD)
    public String toStringSimple(){
        return nombre + "(" + codigo + ")";
    }
}