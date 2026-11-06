package control;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * Validaciones del login (administrador y usuario normal) y registro de usuarios nuevos.
 * Los usuarios se guardan en un arreglo.
 */
public class Controlusuarios {

    private static final int MAX_INTENTOS = 3;
    private static final long BLOQUEO_MS = 30_000;   // 30 segundos

    private Usuario[] usuarios = new Usuario[10];
    private int total = 0;

    private int intentosFallidos = 0;
    private long bloqueadoHasta = 0;

    public Controlusuarios() {
        // Cuentas iniciales (cámbialas antes de entregar)
        agregarInterno(new Usuario("admin", hash("Jay123"), Rol.ADMINISTRADOR));
        agregarInterno(new Usuario("usuario", hash("Diego123"), Rol.NORMAL));
    }

    // ---------------------------------------------------------------- LOGIN

    public Resultadologin validarLogin(String nombre, char[] contrasena) {

        // 1. Bloqueo temporal por intentos fallidos
        long ahora = System.currentTimeMillis();
        if (ahora < bloqueadoHasta) {
            long seg = (bloqueadoHasta - ahora + 999) / 1000;
            limpiar(contrasena);
            return new Resultadologin(false,
                    "Demasiados intentos fallidos. Intenta de nuevo en " + seg + " segundos.", null);
        }

        // 2. Campos obligatorios (no cuentan como intento fallido)
        nombre = (nombre == null) ? "" : nombre.trim();
        if (nombre.isEmpty()) {
            limpiar(contrasena);
            return new Resultadologin(false, "Ingresa tu nombre de usuario.", null);
        }
        if (contrasena == null || contrasena.length == 0) {
            return new Resultadologin(false, "Ingresa tu contraseña.", null);
        }

        // 3. Credenciales
        Usuario u = buscar(nombre);
        String hashIngresado = hash(new String(contrasena));
        limpiar(contrasena);

        boolean correcto = u != null && MessageDigest.isEqual(
                u.getHash().getBytes(StandardCharsets.UTF_8),
                hashIngresado.getBytes(StandardCharsets.UTF_8));

        if (!correcto) {
            intentosFallidos++;
            if (intentosFallidos >= MAX_INTENTOS) {
                intentosFallidos = 0;
                bloqueadoHasta = System.currentTimeMillis() + BLOQUEO_MS;
                return new Resultadologin(false,
                        "Demasiados intentos fallidos. Acceso bloqueado 30 segundos.", null);
            }
            return new Resultadologin(false,
                    "Usuario o contraseña incorrectos. Intentos restantes: "
                    + (MAX_INTENTOS - intentosFallidos), null);
        }

        // 4. Acceso correcto
        intentosFallidos = 0;
        String rol = u.esAdministrador() ? "administrador" : "usuario";
        return new Resultadologin(true, "Bienvenido, " + u.getNombre() + " (" + rol + ").", u);
    }

    // ------------------------------------------------------------- REGISTRO

    /** Registra un usuario NORMAL (botón "Nuevo"). Devuelve el mensaje de error o null si todo salió bien. */
    public String registrar(String nombre, char[] contrasena, char[] confirmacion) {
        nombre = (nombre == null) ? "" : nombre.trim();

        if (nombre.isEmpty()) return "El nombre de usuario es obligatorio.";
        if (nombre.length() < 4 || nombre.length() > 15)
            return "El nombre de usuario debe tener entre 4 y 15 caracteres.";
        if (!nombre.matches("[A-Za-z0-9_]+"))
            return "El nombre de usuario solo puede tener letras, números y guion bajo (sin espacios).";
        if (buscar(nombre) != null) return "Ese nombre de usuario ya está registrado.";

        if (contrasena == null || contrasena.length == 0) return "La contraseña es obligatoria.";
        if (contrasena.length < 6) return "La contraseña debe tener al menos 6 caracteres.";

        String pass = new String(contrasena);
        if (!pass.matches(".*[A-Za-z].*") || !pass.matches(".*[0-9].*"))
            return "La contraseña debe tener al menos una letra y un número.";
        if (confirmacion == null || !Arrays.equals(contrasena, confirmacion))
            return "Las contraseñas no coinciden.";

        agregarInterno(new Usuario(nombre, hash(pass), Rol.NORMAL));
        limpiar(contrasena);
        limpiar(confirmacion);
        return null;
    }

    // ------------------------------------------------------------- INTERNOS

    private Usuario buscar(String nombre) {
        for (int i = 0; i < total; i++) {
            if (usuarios[i].getNombre().equalsIgnoreCase(nombre)) return usuarios[i];
        }
        return null;
    }

    private void agregarInterno(Usuario u) {
        if (total == usuarios.length) usuarios = Arrays.copyOf(usuarios, total * 2);
        usuarios[total++] = u;
    }

    private static void limpiar(char[] c) {
        if (c != null) Arrays.fill(c, '\0');
    }

    private static String hash(String texto) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] b = md.digest(texto.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte x : b) sb.append(String.format("%02x", x));
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e);
        }
    }
}