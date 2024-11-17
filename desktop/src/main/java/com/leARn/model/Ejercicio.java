package main.java.com.leARn.model;

public class Ejercicio {

    private int id;
    private String nombre;
    private String descripcion;
    private Modulo modulo;
    private TipoDeEjercicio tipoDeEjercicio;
    private Dificultad dificultad;

    public Ejercicio(int id, String nombre, String descripcion, Modulo modulo, String tipoDeEjercicio, String dificultad) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.modulo = modulo;
        this.tipoDeEjercicio = TipoDeEjercicio.valueOf(tipoDeEjercicio);
        this.dificultad = Dificultad.valueOf(dificultad);
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

    public TipoDeEjercicio getTipoDeEjercicio() {
        return tipoDeEjercicio;
    }

    public void setTipoDeEjercicio(TipoDeEjercicio tipoDeEjercicio) {
        this.tipoDeEjercicio = tipoDeEjercicio;
    }

    public Dificultad getDificultad() {
        return dificultad;
    }

    public void setDificultad(Dificultad dificultad) {
        this.dificultad = dificultad;
    }

    public Modulo getModulo() {
        return modulo;
    }

    public void setModulo(Modulo modulo) {
        this.modulo = modulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTipoEjercicioDisplayName() {
        return tipoDeEjercicio != null ? tipoDeEjercicio.toString() : "";
    }

    public String getModuloNombre() {
        return modulo != null ? modulo.getNombre() : "";
    }

    public String toString() {
        return this.nombre;
    }
}