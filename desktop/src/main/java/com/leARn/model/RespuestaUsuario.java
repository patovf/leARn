package main.java.com.leARn.model;

public class RespuestaUsuario {
    private int id;
    private Respuesta respuesta;
    private Usuario usuario;

    public RespuestaUsuario(Respuesta respuesta, Usuario usuario) {
        this.respuesta = respuesta;
        this.usuario = usuario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Respuesta getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(Respuesta respuesta) {
        this.respuesta = respuesta;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
