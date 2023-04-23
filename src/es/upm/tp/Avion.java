package es.upm.tp;

/**
 * Avion es una clase que encapsula las variables enteras usadas para definir un avion concreto.
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
     * Atributo que contiene las columnas de asientos del avion
     */
    private int columnas;

    /**
     * Atributo que contiene las filas de asientos del avion
     */
    private int filas;

    /**
     * Atributo que contiene el alcance del avion
     */
    private double alcance;

    /**
     * Constructor que crea un avion con los parametros recibidos (marca, modelo, matricula, columnas, filas y alcance)
     * @param marca especifica la marca del avion
     * @param modelo especifica el modelo del avion
     * @param matricula especifica la matricula del avion
     * @param columnas especifica columnas que tiene el avion
     * @param filas especifica las filas que tiene el avion
     * @param alcance especifica el alcance maximo del avion
     */
    public Avion(String marca, String modelo, String matricula, int columnas, int filas, double alcance){
        this.marca = marca;
        this.modelo = modelo;
        this.matricula = matricula;
        this.columnas = columnas;
        this.filas = filas;
        this. alcance = alcance;
    }

    /**
     * Getter del atributo marca
     * @return Devuelve la marca del avion
     */
    public String getMarca() {
        return marca;
    }

    /**
     * Getter del atributo modelo
     * @return Devuelve el modelo del avion
     */
    public String getModelo() {
        return modelo;
    }

    /**
     * Getter del atributo matricula
     * @return Devuelve la matricula del avion
     */
    public String getMatricula() {
        return matricula;
    }

    /**
     * Getter del atributo columnas
     * @return Devuelve las columnas de los asientos del avion
     */
    public int getColumnas() {
        return columnas;
    }

    /**
     * Getter del atributo filas
     * @return Devuelve las filas de los asientos del avion
     */
    public int getFilas() {
        return filas;
    }

    /**
     * Getter del atributo alcance
     * @return Devuelve el alcance maximo del avion
     */
    public double getAlcance() {
        return alcance;
    }

    /**
     * Devuelve los datos del un avion en un formato especifico, ("(Boeing 737(EC-LKE): 180 asientos, hasta 5700.0 km)")
     * @return marca, modelo, matricula, asientos (columnas*filas) y alcance maximo
     */
    // Crea un String con los datos de un avión con el siguiente formato:
    // Boeing 737(EC-LKE): 180 asientos, hasta 5700.0 km
    public String toString() {
        return marca + " " + modelo + "(" + matricula + "): " + columnas * filas + " asientos, hasta " + alcance + " km";
    }

    /**
     * Devuelve los datos de un avion en un formato especifico, ("(Boeing 737(EC-LKE))")
     * @return marca, modelo y matricula
     */
    // Crea un String con los datos de un avión con el siguiente formato:
    // Boeing 737(EC-LKE)
    public String toStringSimple() {
        return marca + " " + modelo + "(" + matricula + ")";
    }
}