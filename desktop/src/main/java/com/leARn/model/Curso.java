package main.java.com.leARn.model;

public class Curso {

    private int id;
    private String nombre;
    private String descripcion;
    private boolean esPublico;
    private Institucion institucion;
    private int institucionId;
    private String codigo;

    public Curso(int id, String nombre, String descripcion, boolean esPublico, int institucionId, String codigo, Institucion institucion) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.esPublico = esPublico;
        this.institucionId = institucionId;
        this.codigo = codigo;
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

    public int getInstitucionId() {
        return institucionId;
    }

    public void setInstitucionId(int institucionId) {
        this.institucionId = institucionId;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String toString() {
        return this.nombre;
    }
}