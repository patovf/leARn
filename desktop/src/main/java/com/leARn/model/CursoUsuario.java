package main.java.com.leARn.model;

public class CursoUsuario {
    private int id;
    private Curso curso;
    private Usuario usuario;

    public CursoUsuario(Curso curso, Usuario usuario) {
        this.curso = curso;
        this.usuario = usuario;
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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
