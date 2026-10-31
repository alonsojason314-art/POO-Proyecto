package gui;

import javax.swing.*;
import java.awt.*;

public class MenuPrincipal extends JFrame {

    // Colores de la interfaz
    private static final Color FONDO = Color.BLACK;
    private static final Color ROSA = new Color(0xD6, 0x24, 0x9F);       // mismo color que las letras
    private static final Color ROSA_HOVER = new Color(0xE8, 0x4F, 0xB8);
    private static final Color CAMPO = new Color(0x1A, 0x1A, 0x1A);

    private static final Font FUENTE = new Font("Segoe UI", Font.PLAIN, 14);
    private static final Font FUENTE_BOTON = new Font("Segoe UI", Font.BOLD, 14);

    JLabel usu;
    JTextField Tusu;
    JPasswordField Ctsu;

    public MenuPrincipal() {

        setTitle("Inicio de Sesión");
        setSize(650, 520);
        setLayout(null);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(FONDO);

        componentes();

        setVisible(true);
    }

    public void componentes() {

        // LOGO (flor), centrado arriba
        ImageIcon iconoLogo = cargarImagen("/recursos/CL.png", 110);
        JLabel imgLogo = new JLabel(iconoLogo);
        imgLogo.setBounds(270, 20, iconoLogo.getIconWidth(), iconoLogo.getIconHeight());
        add(imgLogo);

        // NOMBRE "COSMOS" (proporción original, sin deformarse)
        ImageIcon iconoLetras = cargarImagen("/recursos/cosmos_letras.png", 220);
        JLabel imgLetras = new JLabel(iconoLetras);
        imgLetras.setBounds(215, 130, iconoLetras.getIconWidth(), iconoLetras.getIconHeight());
        add(imgLetras);

        // NOMBRE DE USUARIO
        usu = new JLabel("Nombre Usuario");
        usu.setForeground(Color.WHITE);
        usu.setFont(FUENTE);
        usu.setBounds(175, 225, 300, 22);
        add(usu);

        Tusu = new JTextField();
        estilizarCampo(Tusu);
        Tusu.setBounds(175, 250, 300, 36);
        add(Tusu);

        // CONTRASEÑA
        JLabel contra = new JLabel("Contraseña");
        contra.setForeground(Color.WHITE);
        contra.setFont(FUENTE);
        contra.setBounds(175, 300, 300, 22);
        add(contra);

        Ctsu = new JPasswordField();
        estilizarCampo(Ctsu);
        Ctsu.setBounds(175, 325, 300, 36);
        add(Ctsu);

        // BOTON ACCEDER
        JButton acceder = crearBoton("Acceder");
        acceder.setBounds(175, 395, 300, 40);
        add(acceder);

        // Enter = Acceder
        getRootPane().setDefaultButton(acceder);
    }

    // Carga una imagen y la escala al ancho indicado conservando su proporción
    private ImageIcon cargarImagen(String ruta, int ancho) {
        ImageIcon original = new ImageIcon(getClass().getResource(ruta));
        int alto = original.getIconHeight() * ancho / original.getIconWidth();
        Image img = original.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }

    // Estilo oscuro con borde rosa para JTextField y JPasswordField
    private void estilizarCampo(JTextField campo) {
        campo.setBackground(CAMPO);
        campo.setForeground(Color.WHITE);
        campo.setCaretColor(ROSA);
        campo.setFont(FUENTE);
        campo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ROSA, 1),
                BorderFactory.createEmptyBorder(0, 10, 0, 10)));
    }

    // Botón rosa con esquinas redondeadas y efecto al pasar el mouse / presionar
    private JButton crearBoton(String texto) {
        JButton boton = new JButton(texto) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (getModel().isPressed()) {
                    g2.setColor(ROSA.darker());
                } else if (getModel().isRollover()) {
                    g2.setColor(ROSA_HOVER);
                } else {
                    g2.setColor(ROSA);
                }
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        boton.setFont(FUENTE_BOTON);
        boton.setForeground(Color.WHITE);
        boton.setContentAreaFilled(false);
        boton.setBorderPainted(false);
        boton.setFocusPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return boton;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MenuPrincipal::new);
    }
}