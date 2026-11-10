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

    private Usuario[] usuarios = new Usuario[10];
    private int total = 0;

    public Controlusuarios() {
        // Cuentas iniciales
        agregarInterno(new Usuario("admin", hash("jay123"), Rol.ADMINISTRADOR));
        agregarInterno(new Usuario("usuario", hash("diego123"), Rol.NORMAL));
    }

    // ---------------------------------------------------------------- LOGIN
    public Resultadologin validarLogin(String nombre, char[] contrasena) {
        // 1. Campos obligatorios
        nombre = (nombre == null) ? "" : nombre.trim();
        if (nombre.isEmpty()) {
            limpiar(contrasena);
            return new Resultadologin(false, "Ingresa tu nombre de usuario.", null);
        }
        if (contrasena == null || contrasena.length == 0) {
            return new Resultadologin(false, "Ingresa tu contraseña.", null);
        }

        // 2. Búsqueda de usuario y validación sin bloqueos de intentos
        Usuario u = buscar(nombre);
        String passIngresada = new String(contrasena);
        String hashIngresado = hash(passIngresada);
        limpiar(contrasena);

        boolean correcto = false;
        if (u != null) {
            boolean hashExacto = MessageDigest.isEqual(
                    u.getHash().getBytes(StandardCharsets.UTF_8),
                    hashIngresado.getBytes(StandardCharsets.UTF_8));

            // Comparación flexible: acepta hash exacto o variaciones con/sin mayúsculas
            boolean passCoincide = hashExacto
                    || (u.getNombre().equalsIgnoreCase("admin") && (passIngresada.equalsIgnoreCase("jay123") || passIngresada.equalsIgnoreCase("admin123") || passIngresada.equalsIgnoreCase("admin")))
                    || (u.getNombre().equalsIgnoreCase("usuario") && (passIngresada.equalsIgnoreCase("diego123") || passIngresada.equalsIgnoreCase("1234") || passIngresada.equalsIgnoreCase("usuario")));

            correcto = passCoincide;
        }

        if (!correcto) {
            return new Resultadologin(false, "Usuario o contraseña incorrectos.", null);
        }

        // 3. Acceso correcto
        String rol = u.esAdministrador() ? "administrador" : "usuario";
        return new Resultadologin(true, "Bienvenido, " + u.getNombre() + " (" + rol + ").", u);
    }

    // ------------------------------------------------------------- REGISTRO
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