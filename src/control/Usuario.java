package control;

/** Cuenta de acceso al sistema. La contraseña se guarda como hash, nunca en texto plano. */
public class Usuario {

    private final String nombre;
    private final String hash;
    private final Rol rol;

    public Usuario(String nombre, String hash, Rol rol) {
        this.nombre = nombre;
        this.hash = hash;
        this.rol = rol;
    }

    public String getNombre() { return nombre; }
    public String getHash() { return hash; }
    public Rol getRol() { return rol; }

    public boolean esAdministrador() { return rol == Rol.ADMINISTRADOR; }
}