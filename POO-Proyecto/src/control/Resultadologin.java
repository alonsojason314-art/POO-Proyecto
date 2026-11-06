package control;

/** Resultado de validar un inicio de sesión: la GUI solo muestra el mensaje y abre la ventana según el rol. */
public class Resultadologin {

    private final boolean exitoso;
    private final String mensaje;
    private final Usuario usuario;

    public Resultadologin(boolean exitoso, String mensaje, Usuario usuario) {
        this.exitoso = exitoso;
        this.mensaje = mensaje;
        this.usuario = usuario;
    }

    public boolean isExitoso() { return exitoso; }
    public String getMensaje() { return mensaje; }
    /** Usuario autenticado, o null si el acceso fue rechazado. */
    public Usuario getUsuario() { return usuario; }
}