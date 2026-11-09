package gui;

import javax.swing.*;
import java.awt.*;

public final class EliminarCancion extends JFrame {

    JLabel lTitulo, lRecents, lCancion;
    JButton bAvatar, bPuntos, bLista, eliminar;
    JTextField tCancion;
    JTextArea tInformacion;
    JScrollPane scrollInformacion;

    // Colores del tema oscuro con acentos rosa
    Color colorFondo = new Color(20, 20, 20);
    Color colorRosa = new Color(230, 0, 115);
    Color colorGrisPanel = new Color(30, 30, 30);
    Color colorGrisCampo = new Color(40, 40, 40);
    Color colorBorde = new Color(70, 70, 70);

    public EliminarCancion() {
        setTitle("Eliminar canción");
        setSize(650, 420);
        setLayout(null);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        getContentPane().setBackground(colorFondo);
        
        componentes();
        setVisible(true);
    }

    public void componentes() {
        
        // --- Barra Superior (Avatar circular con borde rosa y título) ---
        bAvatar = new JButton("🐱") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(colorGrisCampo);
                g2.fillOval(0, 0, getWidth(), getHeight());
                g2.setColor(colorRosa);
                g2.setStroke(new BasicStroke(2));
                g2.drawOval(1, 1, getWidth() - 2, getHeight() - 2);
                super.paintComponent(g2);
                g2.dispose();
            }
        };
        bAvatar.setBounds(20, 15, 45, 45);
        bAvatar.setContentAreaFilled(false);
        bAvatar.setForeground(Color.WHITE);
        bAvatar.setFocusPainted(false);
        bAvatar.setBorder(null);

        lTitulo = new JLabel("Eliminar canción");
        lTitulo.setBounds(80, 20, 250, 35);
        lTitulo.setForeground(colorRosa);
        lTitulo.setFont(new Font("Arial", Font.BOLD, 22));

        // --- Botones de la esquina superior derecha ---
        bPuntos = new JButton("⋮");
        bPuntos.setBounds(525, 115, 30, 25);
        bPuntos.setBackground(colorFondo);
        bPuntos.setForeground(colorRosa);
        bPuntos.setFont(new Font("Arial", Font.BOLD, 16));
        bPuntos.setFocusPainted(false);
        bPuntos.setBorder(null);

        bLista = new JButton("☰");
        bLista.setBounds(575, 115, 40, 25);
        bLista.setBackground(colorFondo);
        bLista.setForeground(colorRosa);
        bLista.setFont(new Font("Arial", Font.PLAIN, 16));
        bLista.setFocusPainted(false);
        bLista.setBorder(null);

        // --- Recents ---
        lRecents = new JLabel("↕ Recents");
        lRecents.setBounds(20, 115, 100, 25);
        lRecents.setForeground(colorRosa);
        lRecents.setFont(new Font("Arial", Font.PLAIN, 13));

        // --- Campo de Entrada para la canción ---
        lCancion = new JLabel("Canción:");
        lCancion.setBounds(20, 165, 75, 25);
        lCancion.setForeground(colorRosa);

        tCancion = new JTextField();
        tCancion.setBounds(100, 165, 160, 25);
        tCancion.setBackground(colorGrisCampo);
        tCancion.setForeground(Color.WHITE);
        tCancion.setCaretColor(Color.WHITE);
        tCancion.setBorder(BorderFactory.createLineBorder(colorRosa));

        // --- Área de Información / Listado ---
        tInformacion = new JTextArea();
        tInformacion.setBackground(colorGrisPanel);
        tInformacion.setForeground(Color.WHITE);
        tInformacion.setCaretColor(Color.WHITE);
        tInformacion.setEditable(false);

        scrollInformacion = new JScrollPane(tInformacion);
        scrollInformacion.setBounds(280, 165, 335, 185);
        scrollInformacion.setBorder(BorderFactory.createLineBorder(colorBorde));

        // --- Botón de Acción inferior (Solo Eliminar) ---
        eliminar = new JButton("Eliminar");
        eliminar.setBounds(515, 365, 100, 30);
        eliminar.setBackground(colorRosa);
        eliminar.setForeground(Color.WHITE);
        eliminar.setFocusPainted(false);
        eliminar.setBorder(BorderFactory.createLineBorder(colorRosa));

        // --- Agregar Componentes a la Ventana ---
        add(bAvatar);
        add(lTitulo);
        add(bPuntos);
        add(bLista);
        add(lRecents);
        
        add(lCancion);
        add(tCancion);
        
        add(scrollInformacion);
        add(eliminar);

        // --- Funcionalidad del botón Eliminar ---
        eliminar.addActionListener(e -> {
            String song = tCancion.getText();

            if (!song.isEmpty()) {
                tInformacion.append("[ELIMINADA] • " + song + "\n");
                tCancion.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Por favor ingrese el nombre de la canción a eliminar.");
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new EliminarCancion());
    }
}