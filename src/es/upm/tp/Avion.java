package es.upm.tp;

/**
 * Description of the class
 *
 * @author      Cesar Jimenez Laguna
 * @author      Iñaki Raos Iturria
 * @version     1.0
 */

public class Avion {
    /**
     * Atributo que contiene la marca del avion
     */
    private String marca;

    /**
     * Atributo que contiene el modelo de avion
     */
    private String modelo;

    /**
     * Atributo que contiene la matricula del avion
     */
    private String matricula;

    /**
     * Atributo que contiene las columnas del avion
     */
    private int columnas;

    /**
     * Atributo que contiene las filas del avion
     */
    private int filas;

    /**
     * Atributo que contiene el alcance del avion
     */
    private double alcance;

    /**
     * Constructor of the class. Constructor que crea un avion con los parametros (marca, modelo, matricula, columnas, filas y alcance) que recibe
     *
     * @param marca especifica marca del avion
     * @param modelo especifica el modelo del avion
     * @param matricula especifica la matricula del avion
     * @param columnas especifica columnas del avion
     * @param filas especifica las filas del avion
     * @param alcance especifica el alcanze del avion
     */

    public Avion(String marca, String modelo, String matricula, int columnas, int filas, double alcance){
        this.marca = marca;
        this.modelo = modelo;
        this.matricula = matricula;
        this.columnas = columnas;
        this.filas = filas;
        this. alcance = alcance;
    }
    /** Devuelve la marca
     * @return marca
     */
    public String getMarca() {
        return marca;
    }

    /** Devuelve el modelo
     * @return modelo
     */
    public String getModelo() {
    return modelo;
    }

    /** Devuelve la matricula
     * @return matricula
     */
    public String getMatricula() {
        return matricula;
    }

    /** Devuelve las columnas
     * @return columnas
     */
    public int getColumnas() {
        return columnas;
    }

    /** Devuelve las filas
     * @return filas
     */
    public int getFilas() {
        return filas;
    }

    /** Devuelve el alcance
     * @return alcance
     */
    public double getAlcance() {
        return alcance;
    }

    /** Devuelve los datos del un avion
     * @return marca, modelo, matricula, asientos (columnas*filas) y alcance
     */
    // Crea un String con los datos de un avión con el siguiente formato:
    // Boeing 737(EC-LKE): 180 asientos, hasta 5700.0 km
    public String toString() {
        return marca + " " + modelo + "(" + matricula + "): " + columnas * filas + " asientos, hasta " + alcance + " km";
    }

    /** Devuelve los datos de un avion
     * @return marca, modelo y matricula
     */
    // Crea un String con los datos de un avión con el siguiente formato:
    // Boeing 737(EC-LKE)
    public String toStringSimple() {
        return marca + " " + modelo + "(" + matricula + ")";
    }
}