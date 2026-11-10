package gui;

import control.Controlusuarios;
import control.DatosCosmos;
import control.Resultadologin;
import control.Usuario;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

public class MenuPrincipal extends JFrame {

    private static final Color FONDO = Color.BLACK;
    private static final Color ROSA = new Color(0xD6, 0x24, 0x9F);
    private static final Color ROSA_HOVER = new Color(0xE8, 0x4F, 0xB8);
    private static final Color CAMPO = new Color(0x1A, 0x1A, 0x1A);

    private JTextField txtUsuario;
    private JPasswordField txtPassword;
    private final Controlusuarios controlUsuarios;

    public MenuPrincipal() {
        this.controlUsuarios = new Controlusuarios();
        configurarVentana();
        inicializarComponentes();
    }

    private void configurarVentana() {
        setTitle("Cosmos - Iniciar Sesión");
        setSize(460, 620);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(FONDO);
        setLayout(null);
    }

    private void inicializarComponentes() {
        // Icono CL.png en grande arriba
        JLabel lblLogo = new JLabel();
        lblLogo.setBounds(130, 20, 200, 170);
        lblLogo.setHorizontalAlignment(SwingConstants.CENTER);
        ImageIcon logo = DatosCosmos.cargarIcono("/recursos/CL.png", 170, 160);
        if (logo != null) lblLogo.setIcon(logo);
        add(lblLogo);

        // Debajo cosmos_letras.png
        JLabel lblLetras = new JLabel();
        lblLetras.setBounds(100, 195, 260, 60);
        lblLetras.setHorizontalAlignment(SwingConstants.CENTER);
        ImageIcon letras = DatosCosmos.cargarIcono("/recursos/cosmos_letras.png", 230, 52);
        if (letras != null) {
            lblLetras.setIcon(letras);
        } else {
            lblLetras.setText("COSMOS");
            lblLetras.setForeground(ROSA);
            lblLetras.setFont(new Font("SansSerif", Font.BOLD, 28));
        }
        add(lblLetras);

        JLabel lblUser = new JLabel("Usuario:");
        lblUser.setForeground(Color.WHITE);
        lblUser.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblUser.setBounds(80, 275, 300, 25);
        add(lblUser);

        txtUsuario = new JTextField();
        txtUsuario.setBounds(80, 305, 300, 38);
        txtUsuario.setBackground(CAMPO);
        txtUsuario.setForeground(Color.WHITE);
        txtUsuario.setCaretColor(Color.WHITE);
        txtUsuario.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ROSA, 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        add(txtUsuario);

        JLabel lblPass = new JLabel("Contraseña:");
        lblPass.setForeground(Color.WHITE);
        lblPass.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblPass.setBounds(80, 360, 300, 25);
        add(lblPass);

        txtPassword = new JPasswordField();
        txtPassword.setBounds(80, 390, 300, 38);
        txtPassword.setBackground(CAMPO);
        txtPassword.setForeground(Color.WHITE);
        txtPassword.setCaretColor(Color.WHITE);
        txtPassword.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ROSA, 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        add(txtPassword);

        JButton btnIngresar = new JButton("Iniciar Sesión");
        btnIngresar.setBounds(80, 460, 300, 42);
        btnIngresar.setBackground(ROSA);
        btnIngresar.setForeground(Color.WHITE);
        btnIngresar.setFont(new Font("SansSerif", Font.BOLD, 15));
        btnIngresar.setFocusPainted(false);
        btnIngresar.setBorderPainted(false);
        btnIngresar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnIngresar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) { btnIngresar.setBackground(ROSA_HOVER); }
            @Override
            public void mouseExited(MouseEvent e) { btnIngresar.setBackground(ROSA); }
        });

        btnIngresar.addActionListener(e -> autenticar());
        txtPassword.addActionListener(e -> autenticar());
        add(btnIngresar);
    }

    private void autenticar() {
        String user = txtUsuario.getText().trim();
        char[] pass = txtPassword.getPassword();

        if (user.isEmpty() || pass.length == 0) {
            JOptionPane.showMessageDialog(this, "Por favor, llena todos los campos.", "Campos vacíos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            Resultadologin res = controlUsuarios.validarLogin(user, pass);
            if (res != null && res.isExitoso()) {
                dispose();
                Usuario u = res.getUsuario();
                String nombreMostrar = (u != null) ? u.getNombre() : user;

                if (u != null && u.esAdministrador()) {
                    new VistaAdmin(nombreMostrar).setVisible(true);
                } else {
                    new VistaUsuario(nombreMostrar).setVisible(true);
                }
                return;
            } else if (res != null) {
                JOptionPane.showMessageDialog(this, res.getMensaje(), "Acceso denegado", JOptionPane.WARNING_MESSAGE);
                return;
            }
        } catch (Exception ignored) {}

        JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos.", "Error de acceso", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MenuPrincipal().setVisible(true));
    }
}