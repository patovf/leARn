package main.java.com.leARn.model;

public class Modulo {

    private int id;
    private String nombre;
    private String descripcion;
    private Curso curso;

    public Modulo(String nombre, String descripcion, Curso curso) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.curso = curso;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCursoNombre() {
        return curso != null ? curso.getNombre() : "";
    }
}