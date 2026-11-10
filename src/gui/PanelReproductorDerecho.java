package gui;

import control.Cancion;
import control.DatosCosmos;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

public class PanelReproductorDerecho extends JPanel {

    private static final Color FONDO = Color.BLACK;
    private static final Color ROSA = new Color(0xD6, 0x24, 0x9F);
    private static final Color ROSA_HOVER = new Color(0xE8, 0x4F, 0xB8);
    private static final Color CAMPO = new Color(0x1A, 0x1A, 0x1A);

    private JLabel lblPortada;
    private JLabel lblCancion;
    private JLabel lblArtista;
    private JLabel lblDuracion;
    private JTextArea txtLetra;
    private Cancion cancionActual;

    public PanelReproductorDerecho() {
        setPreferredSize(new Dimension(280, 720));
        setBackground(FONDO);
        setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, new Color(0x2C, 0x2C, 0x2C)));
        setLayout(new BorderLayout(0, 12));

        inicializarComponentes();
        if (!DatosCosmos.CANCIONES.isEmpty()) {
            cargarCancion(DatosCosmos.CANCIONES.get(0));
        }
    }

    private void inicializarComponentes() {
        JPanel panelTop = new JPanel();
        panelTop.setBackground(CAMPO);
        panelTop.setLayout(new BoxLayout(panelTop, BoxLayout.Y_AXIS));
        panelTop.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        lblPortada = new JLabel();
        lblPortada.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblPortada.setPreferredSize(new Dimension(125, 125));
        lblPortada.setMaximumSize(new Dimension(125, 125));
        lblPortada.setHorizontalAlignment(SwingConstants.CENTER);
        lblPortada.setBorder(BorderFactory.createLineBorder(ROSA, 1));
        panelTop.add(lblPortada);
        panelTop.add(Box.createVerticalStrut(10));

        lblCancion = new JLabel("<Nombre de la Cancion>");
        lblCancion.setForeground(Color.WHITE);
        lblCancion.setFont(new Font("SansSerif", Font.BOLD, 15));
        lblCancion.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelTop.add(lblCancion);

        lblArtista = new JLabel("<Nombre del Artista>");
        lblArtista.setForeground(new Color(0xB3, 0xB3, 0xB3));
        lblArtista.setFont(new Font("SansSerif", Font.PLAIN, 13));
        lblArtista.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelTop.add(lblArtista);

        lblDuracion = new JLabel("0:00 / 3:50");
        lblDuracion.setForeground(ROSA);
        lblDuracion.setFont(new Font("SansSerif", Font.BOLD, 12));
        lblDuracion.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelTop.add(Box.createVerticalStrut(4));
        panelTop.add(lblDuracion);
        panelTop.add(Box.createVerticalStrut(10));

        JProgressBar barraProgreso = new JProgressBar(0, 100);
        barraProgreso.setValue(35);
        barraProgreso.setPreferredSize(new Dimension(210, 5));
        barraProgreso.setMaximumSize(new Dimension(210, 5));
        barraProgreso.setBackground(FONDO);
        barraProgreso.setForeground(ROSA);
        barraProgreso.setBorderPainted(false);
        panelTop.add(barraProgreso);
        panelTop.add(Box.createVerticalStrut(10));

        // Iconos más pequeños (22x22 px) y con espaciado compacto
        JPanel panelControles = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 0));
        panelControles.setBackground(CAMPO);

        JLabel btnAnterior = crearIconoControl("/recursos/anterior_48.png", "⏮");
        JLabel btnPlay = crearIconoControl("/recursos/play_48.png", "▶");
        JLabel btnPausa = crearIconoControl("/recursos/pausa_48.png", "⏸");
        JLabel btnSiguiente = crearIconoControl("/recursos/siguiente_48.png", "⏭");

        panelControles.add(btnAnterior);
        panelControles.add(btnPlay);
        panelControles.add(btnPausa);
        panelControles.add(btnSiguiente);
        panelTop.add(panelControles);

        add(panelTop, BorderLayout.NORTH);

        JPanel panelCentro = new JPanel(new BorderLayout(0, 8));
        panelCentro.setBackground(CAMPO);
        panelCentro.setBorder(BorderFactory.createEmptyBorder(12, 15, 12, 15));

        JLabel lblTituloLetra = new JLabel("<Letra de la Cancion>", SwingConstants.CENTER);
        lblTituloLetra.setForeground(Color.WHITE);
        lblTituloLetra.setFont(new Font("SansSerif", Font.BOLD, 14));
        panelCentro.add(lblTituloLetra, BorderLayout.NORTH);

        txtLetra = new JTextArea();
        txtLetra.setEditable(false);
        txtLetra.setLineWrap(true);
        txtLetra.setWrapStyleWord(true);
        txtLetra.setBackground(CAMPO);
        txtLetra.setForeground(new Color(0xE0, 0xE0, 0xE0));
        txtLetra.setFont(new Font("SansSerif", Font.PLAIN, 14));
        txtLetra.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        JScrollPane scrollLetra = new JScrollPane(txtLetra);
        scrollLetra.setBorder(BorderFactory.createLineBorder(new Color(0x33, 0x33, 0x33)));
        scrollLetra.getVerticalScrollBar().setUnitIncrement(12);
        panelCentro.add(scrollLetra, BorderLayout.CENTER);

        add(panelCentro, BorderLayout.CENTER);

        JPanel panelSur = new JPanel(new BorderLayout());
        panelSur.setBackground(FONDO);
        panelSur.setBorder(BorderFactory.createEmptyBorder(5, 20, 18, 20));

        JButton btnImprimir = new JButton("Imprime Letra");
        btnImprimir.setPreferredSize(new Dimension(220, 40));
        btnImprimir.setBackground(ROSA);
        btnImprimir.setForeground(Color.WHITE);
        btnImprimir.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnImprimir.setFocusPainted(false);
        btnImprimir.setBorderPainted(false);
        btnImprimir.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnImprimir.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) { btnImprimir.setBackground(ROSA_HOVER); }
            @Override
            public void mouseExited(MouseEvent e) { btnImprimir.setBackground(ROSA); }
        });

        btnImprimir.addActionListener(e -> DatosCosmos.imprimirLetraCancion(this, cancionActual));
        panelSur.add(btnImprimir, BorderLayout.CENTER);

        add(panelSur, BorderLayout.SOUTH);
    }

    private JLabel crearIconoControl(String ruta, String textoFallback) {
        JLabel lbl = new JLabel();
        ImageIcon icon = DatosCosmos.cargarIcono(ruta, 22, 22); // Reducido de 32 a 22 px
        if (icon != null) {
            lbl.setIcon(icon);
        } else {
            lbl.setText(textoFallback);
            lbl.setForeground(Color.WHITE);
            lbl.setFont(new Font("SansSerif", Font.BOLD, 14));
        }
        lbl.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return lbl;
    }

    public final void cargarCancion(Cancion c) {
        if (c == null) return;
        this.cancionActual = c;
        lblCancion.setText(c.getTitulo());
        lblArtista.setText(c.getArtista());
        lblDuracion.setText("0:00 / " + c.getDuracion());
        txtLetra.setText(c.getLetra());
        txtLetra.setCaretPosition(0);
        lblPortada.setIcon(DatosCosmos.cargarIcono(c.getRutaImagen(), 120, 120));
    }
}