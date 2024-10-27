package main.java.com.leARn.model;

public class Respuesta {

    private int id;
    private String descripcion;
    private boolean esCorrecta;
    private Ejercicio ejercicio;

    public Respuesta(String descripcion, boolean esCorrecta, Ejercicio ejercicio) {
        this.descripcion = descripcion;
        this.esCorrecta = esCorrecta;
        this.ejercicio = ejercicio;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean isEsCorrecta() {
        return esCorrecta;
    }

    public void setEsCorrecta(boolean esCorrecta) {
        this.esCorrecta = esCorrecta;
    }

    public Ejercicio getEjercicio() {
        return ejercicio;
    }

    public void setEjercicio(Ejercicio ejercicio) {
        this.ejercicio = ejercicio;
    }
}