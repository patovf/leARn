package main.java.com.leARn.model;

public class Curso {

    private int id;
    private String nombre;
    private String descripcion;
    private boolean esPublico;
    private Institucion institucion;

    public Curso(String nombre, String descripcion, boolean esPublico, Institucion institucion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.esPublico = esPublico;
        this.institucion = institucion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Institucion getInstitucion() {
        return institucion;
    }

    public void setInstitucion(Institucion institucion) {
        this.institucion = institucion;
    }

    public boolean isEsPublico() {
        return esPublico;
    }

    public void setEsPublico(boolean esPublico) {
        this.esPublico = esPublico;
    }

    public String getInstitucionNombre() {
        return institucion != null ? institucion.getNombre() : "";
    }

    public String getCursoEsPublico() {
        return esPublico ? "Si" : "No";
    }
}