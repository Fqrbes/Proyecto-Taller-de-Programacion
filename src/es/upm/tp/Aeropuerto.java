package es.upm.tp;

/**
 * Aeropuerto es una clase que encapsula las variables enteras usadas para definir un aeropuerto concreto
 *
 * @author      Cesar Jimenez Laguna
 * @author      Iñaki Ramos Iturria
 * @version     1.0
 */

public class Aeropuerto {

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
     * Constructor que crea un aeropuerto con los parametros que recibe
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

    /**
     * Getter del atributo nombre
     * @return Devuelve el nombre del aeropuerto
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Getter del atributo codigo
     * @return Devuelve el codigo IATA del aeropuerto
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * Getter del atributo latitud
     * @return Devuelve la latitud del aeropuerto
     */
    public double getLatitud() {
        return latitud;
    }

    /**
     * Getter del atributo longitud
     * @return Devuelve la longitud del aeropuerto
     */
    public double getLongitud() {
        return longitud;
    }

    /**
     * Getter del atributo terminales
     * @return Devuelve las terminales del aeropuerto
     */
    public int getTerminales() {
        return terminales;
    }

    /**
     * Devuelve la distancia entre dos aeropuertos
     * @param destino aeropuerto de destio al que llega el avion
     * @return distancia entre el aeropuerto de origen y el aeropuerto de destino
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

    /**
     * Devuelve los datos caracteristicos de un aeropuerto en un formato especifico, ("Adolfo Suarez Madrid - Barajas(MAD), en (40.4927751 40.4927751), con 4 terminales)")
     * @return nombre, codigo, latitud, longitud y numero de terminales del aeropuerto
     */
    // Crea un String con los datos de un aeropuerto con el siguiente formato:
    // Adolfo Suarez Madrid - Barajas(MAD), en (40.4927751 40.4927751), con 4 terminales
    public String toString(){
        return nombre + "(" + codigo + "), en (" + latitud + " " + longitud + "), con " + terminales + " terminales";
    }

    /**
     * Devuelve el nombre del aeropuerto y el codigo IATA del mismo, en un formato especifico, ("(Adolfo Suarez Madrid - Barajas(MAD))")
     * @return nombre y codigo IATA
     */
    // Crea un String con los datos de un aeropuerto con el siguiente formato:
    // Adolfo Suarez Madrid - Barajas(MAD)
    public String toStringSimple(){
        return nombre + "(" + codigo + ")";
    }
}