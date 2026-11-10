package gui;

import control.Cancion;
import control.DatosCosmos;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class EditarCancion extends JDialog {

    private static final Color FONDO = Color.BLACK;
    private static final Color ROSA = new Color(0xD6, 0x24, 0x9F);
    private static final Color ROSA_HOVER = new Color(0xE8, 0x4F, 0xB8);
    private static final Color CAMPO = new Color(0x1A, 0x1A, 0x1A);

    private JTextField txtBuscarClave;
    private JComboBox<String> comboCanciones;
    private JTextField txtTitulo, txtArtista, txtAlbum, txtAnio, txtDuracion;
    private JComboBox<String> comboGenero;
    private JTextArea txtLetra;
    private JLabel lblPreviewImg, lblRutaImg;
    private String rutaImagenSeleccionada = "";
    private Cancion cancionEncontrada;

    public EditarCancion(Frame parent) {
        super(parent, "Cosmos - Editar Canción", true);
        setSize(600, 700);
        setLocationRelativeTo(parent);
        getContentPane().setBackground(FONDO);
        setLayout(new BorderLayout(10, 10));
        inicializarComponentes();
        cargarListaCanciones();
    }

    private void inicializarComponentes() {
        JPanel panelBusqueda = new JPanel(new GridLayout(2, 1, 6, 6));
        panelBusqueda.setBackground(FONDO);
        panelBusqueda.setBorder(BorderFactory.createEmptyBorder(15, 20, 5, 20));

        JPanel filaClave = new JPanel(new BorderLayout(8, 0));
        filaClave.setBackground(FONDO);
        txtBuscarClave = crearCampo();
        JButton btnCargar = crearBoton("Buscar Clave");
        btnCargar.addActionListener(e -> cargarPorClave());
        filaClave.add(txtBuscarClave, BorderLayout.CENTER);
        filaClave.add(btnCargar, BorderLayout.EAST);

        comboCanciones = new JComboBox<>();
        comboCanciones.setBackground(CAMPO);
        comboCanciones.setForeground(Color.WHITE);
        comboCanciones.addActionListener(e -> seleccionarDeCombo());

        panelBusqueda.add(filaClave);
        panelBusqueda.add(comboCanciones);
        add(panelBusqueda, BorderLayout.NORTH);

        JPanel panelCentral = new JPanel(new BorderLayout(15, 10));
        panelCentral.setBackground(FONDO);
        panelCentral.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));

        JPanel formCampos = new JPanel(new GridLayout(6, 2, 8, 8));
        formCampos.setBackground(FONDO);

        txtTitulo = agregarFila(formCampos, "Título:");
        txtArtista = agregarFila(formCampos, "Artista:");
        txtAlbum = agregarFila(formCampos, "Álbum:");

        JLabel lblGen = new JLabel("Género:");
        lblGen.setForeground(Color.WHITE);
        formCampos.add(lblGen);

        comboGenero = new JComboBox<>(DatosCosmos.GENEROS);
        comboGenero.setBackground(CAMPO);
        comboGenero.setForeground(Color.WHITE);
        comboGenero.setBorder(BorderFactory.createLineBorder(ROSA, 1));
        formCampos.add(comboGenero);

        txtAnio = agregarFila(formCampos, "Año (4 dígitos):");
        txtDuracion = agregarFila(formCampos, "Duración (mm:ss):");

        panelCentral.add(formCampos, BorderLayout.CENTER);

        JPanel panelImg = new JPanel();
        panelImg.setBackground(CAMPO);
        panelImg.setPreferredSize(new Dimension(170, 210));
        panelImg.setLayout(new BoxLayout(panelImg, BoxLayout.Y_AXIS));
        panelImg.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ROSA, 1),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        lblPreviewImg = new JLabel();
        lblPreviewImg.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblPreviewImg.setPreferredSize(new Dimension(110, 110));
        lblPreviewImg.setMaximumSize(new Dimension(110, 110));
        lblPreviewImg.setHorizontalAlignment(SwingConstants.CENTER);
        lblPreviewImg.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        panelImg.add(lblPreviewImg);
        panelImg.add(Box.createVerticalStrut(8));

        lblRutaImg = new JLabel("Portada actual", SwingConstants.CENTER);
        lblRutaImg.setForeground(Color.LIGHT_GRAY);
        lblRutaImg.setFont(new Font("SansSerif", Font.PLAIN, 10));
        lblRutaImg.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelImg.add(lblRutaImg);
        panelImg.add(Box.createVerticalStrut(8));

        JButton btnCambiarImg = crearBoton("Cambiar Portada");
        btnCambiarImg.setFont(new Font("SansSerif", Font.BOLD, 11));
        btnCambiarImg.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnCambiarImg.addActionListener(e -> seleccionarImagen());
        panelImg.add(btnCambiarImg);

        panelCentral.add(panelImg, BorderLayout.EAST);
        add(panelCentral, BorderLayout.CENTER);

        JPanel sur = new JPanel(new BorderLayout(8, 8));
        sur.setBackground(FONDO);
        sur.setBorder(BorderFactory.createEmptyBorder(5, 20, 15, 20));

        txtLetra = new JTextArea(4, 20);
        txtLetra.setBackground(CAMPO);
        txtLetra.setForeground(Color.WHITE);
        txtLetra.setCaretColor(Color.WHITE);
        txtLetra.setLineWrap(true);
        txtLetra.setWrapStyleWord(true);

        JScrollPane scrollLetra = new JScrollPane(txtLetra);
        scrollLetra.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(ROSA), "Letra de la Canción", 0, 0, null, Color.WHITE));
        sur.add(scrollLetra, BorderLayout.CENTER);

        JButton btnGuardar = crearBoton("Guardar Cambios");
        btnGuardar.setPreferredSize(new Dimension(200, 40));
        btnGuardar.addActionListener(e -> guardarCambios());
        sur.add(btnGuardar, BorderLayout.SOUTH);

        add(sur, BorderLayout.SOUTH);
    }

    private void cargarListaCanciones() {
        comboCanciones.removeAllItems();
        for (Cancion c : DatosCosmos.CANCIONES) {
            comboCanciones.addItem(c.getClave() + " - " + c.getTitulo());
        }
        if (!DatosCosmos.CANCIONES.isEmpty()) {
            comboCanciones.setSelectedIndex(0);
            mostrarDatosCancion(DatosCosmos.CANCIONES.get(0));
        }
    }

    private void seleccionarDeCombo() {
        int idx = comboCanciones.getSelectedIndex();
        if (idx >= 0 && idx < DatosCosmos.CANCIONES.size()) {
            mostrarDatosCancion(DatosCosmos.CANCIONES.get(idx));
        }
    }

    private JTextField agregarFila(JPanel panel, String etiqueta) {
        JLabel lbl = new JLabel(etiqueta);
        lbl.setForeground(Color.WHITE);
        panel.add(lbl);
        JTextField txt = crearCampo();
        panel.add(txt);
        return txt;
    }

    private JTextField crearCampo() {
        JTextField txt = new JTextField();
        txt.setBackground(CAMPO);
        txt.setForeground(Color.WHITE);
        txt.setCaretColor(Color.WHITE);
        txt.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ROSA, 1),
                BorderFactory.createEmptyBorder(4, 8, 4, 8)));
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
        FileNameExtensionFilter filtro = new FileNameExtensionFilter("Imágenes (*.jpg, *.png, *.jpeg)", "jpg", "png", "jpeg");
        chooser.setFileFilter(filtro);

        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File arch = chooser.getSelectedFile();
            rutaImagenSeleccionada = arch.getAbsolutePath();
            lblRutaImg.setText(arch.getName());
            lblPreviewImg.setIcon(DatosCosmos.cargarIcono(rutaImagenSeleccionada, 110, 110));
        }
    }

    private void cargarPorClave() {
        String q = txtBuscarClave.getText().trim();
        for (int i = 0; i < DatosCosmos.CANCIONES.size(); i++) {
            Cancion c = DatosCosmos.CANCIONES.get(i);
            if (c.getClave().equalsIgnoreCase(q) || c.getTitulo().equalsIgnoreCase(q)) {
                comboCanciones.setSelectedIndex(i);
                mostrarDatosCancion(c);
                return;
            }
        }
        JOptionPane.showMessageDialog(this, "No se encontró la canción: " + q, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void mostrarDatosCancion(Cancion c) {
        cancionEncontrada = c;
        txtBuscarClave.setText(c.getClave());
        txtTitulo.setText(c.getTitulo());
        txtArtista.setText(c.getArtista());
        txtAlbum.setText(c.getAlbum());
        comboGenero.setSelectedItem(c.getGenero());
        txtAnio.setText(c.getAnio());
        txtDuracion.setText(c.getDuracion());
        txtLetra.setText(c.getLetra());

        rutaImagenSeleccionada = c.getRutaImagen();
        lblRutaImg.setText(rutaImagenSeleccionada.startsWith("COVER:") ? "Portada Cosmos" : new File(rutaImagenSeleccionada).getName());
        lblPreviewImg.setIcon(DatosCosmos.cargarIcono(rutaImagenSeleccionada, 110, 110));
    }

    private void guardarCambios() {
        if (cancionEncontrada == null) {
            JOptionPane.showMessageDialog(this, "Primero selecciona una canción válida.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String titulo = txtTitulo.getText().trim();
        String artista = txtArtista.getText().trim();
        String album = txtAlbum.getText().trim();
        String genero = (String) comboGenero.getSelectedItem();
        String anio = txtAnio.getText().trim();
        String duracion = txtDuracion.getText().trim();

        if (titulo.isEmpty() || artista.isEmpty() || album.isEmpty() || anio.isEmpty() || duracion.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos deben estar llenos.", "Campos vacíos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!DatosCosmos.esAnioValido(anio)) {
            JOptionPane.showMessageDialog(this, "El Año debe ser un número de 4 dígitos entre 1900 y 2026.", "Año inválido", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!DatosCosmos.esDuracionValida(duracion)) {
            JOptionPane.showMessageDialog(this, "La Duración debe tener formato mm:ss (ej. 03:45).", "Duración inválida", JOptionPane.ERROR_MESSAGE);
            return;
        }

        cancionEncontrada.setTitulo(titulo);
        cancionEncontrada.setArtista(artista);
        cancionEncontrada.setAlbum(album);
        cancionEncontrada.setGenero(genero);
        cancionEncontrada.setAnio(anio);
        cancionEncontrada.setDuracion(duracion);
        cancionEncontrada.setLetra(txtLetra.getText());
        cancionEncontrada.setRutaImagen(rutaImagenSeleccionada);

        DatosCosmos.ordenarYGuardar(); // Reordena alfabéticamente y guarda cambios en disco

        JOptionPane.showMessageDialog(this, "Canción actualizada y guardada exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        dispose();
    }
}