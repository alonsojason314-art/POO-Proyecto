package gui;

import control.Cancion;
import control.DatosCosmos;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Agregarcancion extends JDialog {

    private static final Color FONDO = Color.BLACK;
    private static final Color ROSA = new Color(0xD6, 0x24, 0x9F);
    private static final Color ROSA_HOVER = new Color(0xE8, 0x4F, 0xB8);
    private static final Color CAMPO = new Color(0x1A, 0x1A, 0x1A);

    private JTextField txtClave, txtTitulo, txtArtista, txtAlbum, txtGenero, txtAnio, txtDuracion;
    private JTextArea txtLetra;
    private JLabel lblPreviewImg, lblRutaImg;
    private String rutaImagenSeleccionada = "";

    public Agregarcancion(Frame parent) {
        super(parent, "Cosmos - Agregar Canción", true);
        setSize(600, 660);
        setLocationRelativeTo(parent);
        getContentPane().setBackground(FONDO);
        setLayout(new BorderLayout(12, 12));
        inicializarComponentes();
    }

    public Agregarcancion() {
        this(null);
    }

    private void inicializarComponentes() {
        JLabel lblHeader = new JLabel("Agregar Nueva Canción", SwingConstants.LEFT);
        lblHeader.setForeground(ROSA);
        lblHeader.setFont(new Font("SansSerif", Font.BOLD, 20));
        lblHeader.setBorder(BorderFactory.createEmptyBorder(15, 20, 5, 20));
        add(lblHeader, BorderLayout.NORTH);

        JPanel panelCentral = new JPanel(new BorderLayout(15, 10));
        panelCentral.setBackground(FONDO);
        panelCentral.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));

        JPanel formCampos = new JPanel(new GridLayout(7, 2, 8, 10));
        formCampos.setBackground(FONDO);

        txtClave = agregarFila(formCampos, "Clave (ej. C07):");
        txtTitulo = agregarFila(formCampos, "Título:");
        txtArtista = agregarFila(formCampos, "Artista:");
        txtAlbum = agregarFila(formCampos, "Álbum:");
        txtGenero = agregarFila(formCampos, "Género:");
        txtAnio = agregarFila(formCampos, "Año:");
        txtDuracion = agregarFila(formCampos, "Duración (mm:ss):");

        panelCentral.add(formCampos, BorderLayout.CENTER);

        // Panel derecho para seleccionar Portada
        JPanel panelImg = new JPanel();
        panelImg.setBackground(CAMPO);
        panelImg.setPreferredSize(new Dimension(170, 220));
        panelImg.setLayout(new BoxLayout(panelImg, BoxLayout.Y_AXIS));
        panelImg.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ROSA, 1),
                BorderFactory.createEmptyBorder(12, 10, 12, 10)));

        lblPreviewImg = new JLabel();
        lblPreviewImg.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblPreviewImg.setPreferredSize(new Dimension(110, 110));
        lblPreviewImg.setMaximumSize(new Dimension(110, 110));
        lblPreviewImg.setHorizontalAlignment(SwingConstants.CENTER);
        lblPreviewImg.setIcon(DatosCosmos.generarPortadaMusical("♪", "#D6249F", "#1A1A1A", 110, 110));
        panelImg.add(lblPreviewImg);
        panelImg.add(Box.createVerticalStrut(10));

        lblRutaImg = new JLabel("Portada automática", SwingConstants.CENTER);
        lblRutaImg.setForeground(Color.LIGHT_GRAY);
        lblRutaImg.setFont(new Font("SansSerif", Font.PLAIN, 11));
        lblRutaImg.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelImg.add(lblRutaImg);
        panelImg.add(Box.createVerticalStrut(10));

        JButton btnSubirImg = crearBoton("Subir Portada");
        btnSubirImg.setFont(new Font("SansSerif", Font.BOLD, 12));
        btnSubirImg.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnSubirImg.addActionListener(e -> seleccionarImagen());
        panelImg.add(btnSubirImg);

        panelCentral.add(panelImg, BorderLayout.EAST);
        add(panelCentral, BorderLayout.CENTER);

        // Panel inferior: Letra y botones
        JPanel sur = new JPanel(new BorderLayout(8, 10));
        sur.setBackground(FONDO);
        sur.setBorder(BorderFactory.createEmptyBorder(5, 20, 18, 20));

        txtLetra = new JTextArea(5, 20);
        txtLetra.setBackground(CAMPO);
        txtLetra.setForeground(Color.WHITE);
        txtLetra.setCaretColor(Color.WHITE);
        txtLetra.setLineWrap(true);
        txtLetra.setWrapStyleWord(true);

        JScrollPane scrollLetra = new JScrollPane(txtLetra);
        scrollLetra.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(ROSA), "Letra de la Canción", 0, 0, null, Color.WHITE));
        sur.add(scrollLetra, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        panelBotones.setBackground(FONDO);

        JButton btnLimpiar = new JButton("Limpiar");
        btnLimpiar.setPreferredSize(new Dimension(120, 38));
        btnLimpiar.setBackground(CAMPO);
        btnLimpiar.setForeground(Color.WHITE);
        btnLimpiar.setFocusPainted(false);
        btnLimpiar.setBorder(BorderFactory.createLineBorder(ROSA, 1));
        btnLimpiar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLimpiar.addActionListener(e -> limpiar());

        JButton btnGuardar = crearBoton("Agregar Canción");
        btnGuardar.setPreferredSize(new Dimension(160, 38));
        btnGuardar.addActionListener(e -> guardar());

        panelBotones.add(btnLimpiar);
        panelBotones.add(btnGuardar);
        sur.add(panelBotones, BorderLayout.SOUTH);

        add(sur, BorderLayout.SOUTH);
    }

    private JTextField agregarFila(JPanel panel, String etiqueta) {
        JLabel lbl = new JLabel(etiqueta);
        lbl.setForeground(Color.WHITE);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 13));
        panel.add(lbl);

        JTextField txt = new JTextField();
        txt.setBackground(CAMPO);
        txt.setForeground(Color.WHITE);
        txt.setCaretColor(Color.WHITE);
        txt.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ROSA, 1),
                BorderFactory.createEmptyBorder(4, 8, 4, 8)));
        panel.add(txt);
        return txt;
    }

    private JButton crearBoton(String texto) {
        JButton btn = new JButton(texto);
        btn.setBackground(ROSA);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("SansSerif", Font.BOLD, 13));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) { btn.setBackground(ROSA_HOVER); }
            @Override
            public void mouseExited(MouseEvent e) { btn.setBackground(ROSA); }
        });
        return btn;
    }

    private void seleccionarImagen() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileNameExtensionFilter("Imágenes (*.png, *.jpg, *.jpeg)", "png", "jpg", "jpeg"));
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File archivo = chooser.getSelectedFile();
            rutaImagenSeleccionada = archivo.getAbsolutePath();
            lblRutaImg.setText(archivo.getName());
            lblPreviewImg.setIcon(DatosCosmos.cargarIcono(rutaImagenSeleccionada, 110, 110));
        }
    }

    private void limpiar() {
        txtClave.setText("");
        txtTitulo.setText("");
        txtArtista.setText("");
        txtAlbum.setText("");
        txtGenero.setText("");
        txtAnio.setText("");
        txtDuracion.setText("");
        txtLetra.setText("");
        rutaImagenSeleccionada = "";
        lblRutaImg.setText("Portada automática");
        lblPreviewImg.setIcon(DatosCosmos.generarPortadaMusical("♪", "#D6249F", "#1A1A1A", 110, 110));
    }

    private void guardar() {
        String clave = txtClave.getText().trim();
        String titulo = txtTitulo.getText().trim();
        String artista = txtArtista.getText().trim();
        String album = txtAlbum.getText().trim();
        String genero = txtGenero.getText().trim();
        String anio = txtAnio.getText().trim();
        String duracion = txtDuracion.getText().trim();
        String letra = txtLetra.getText().trim();

        if (clave.isEmpty() || titulo.isEmpty() || artista.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor completa al menos Clave, Título y Artista.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        for (Cancion c : DatosCosmos.CANCIONES) {
            if (c.getClave().equalsIgnoreCase(clave)) {
                JOptionPane.showMessageDialog(this, "Ya existe una canción con la clave " + clave, "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        if (album.isEmpty()) album = "Sencillo";
        if (genero.isEmpty()) genero = "Pop";
        if (anio.isEmpty()) anio = "2026";
        if (duracion.isEmpty()) duracion = "3:30";
        if (letra.isEmpty()) letra = "Letra no disponible.";

        String rutaFinal = rutaImagenSeleccionada.isEmpty()
                ? "COVER:" + titulo + ":#D6249F:#1A103C"
                : rutaImagenSeleccionada;

        Cancion nueva = new Cancion(clave, titulo, genero, artista, anio, album, duracion, rutaFinal, letra);
        DatosCosmos.CANCIONES.add(nueva);

        JOptionPane.showMessageDialog(this, "Canción '" + titulo + "' agregada correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        dispose();
    }
}