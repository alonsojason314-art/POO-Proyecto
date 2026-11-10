package gui;

import control.Cancion;
import control.DatosCosmos;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.*;

public class EliminarCancion extends JDialog {

    private static final Color FONDO = Color.BLACK;
    private static final Color ROSA = new Color(0xD6, 0x24, 0x9F);
    private static final Color ROSA_HOVER = new Color(0xE8, 0x4F, 0xB8);
    private static final Color CAMPO = new Color(0x1A, 0x1A, 0x1A);

    private JTextField txtBusqueda;
    private JComboBox<String> comboCanciones;
    private JTextArea areaDetalle;
    private JLabel lblPortada;
    private Cancion cancionSeleccionada;

    public EliminarCancion(Frame parent) {
        super(parent, "Cosmos - Eliminar Canción", true);
        setSize(540, 480);
        setLocationRelativeTo(parent);
        getContentPane().setBackground(FONDO);
        setLayout(new BorderLayout(12, 12));
        inicializarComponentes();
        recargarCombo();
    }

    public EliminarCancion() {
        this(null);
    }

    private void inicializarComponentes() {
        JPanel norte = new JPanel(new GridLayout(2, 1, 8, 8));
        norte.setBackground(FONDO);
        norte.setBorder(BorderFactory.createEmptyBorder(15, 20, 5, 20));

        // Búsqueda por clave o título
        JPanel filaBuscar = new JPanel(new BorderLayout(8, 0));
        filaBuscar.setBackground(FONDO);

        txtBusqueda = new JTextField();
        txtBusqueda.setBackground(CAMPO);
        txtBusqueda.setForeground(Color.WHITE);
        txtBusqueda.setCaretColor(Color.WHITE);
        txtBusqueda.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ROSA, 1),
                BorderFactory.createEmptyBorder(4, 8, 4, 8)));

        JButton btnBuscar = crearBoton("Buscar Clave / Título");
        btnBuscar.addActionListener(e -> buscarPorTexto());

        filaBuscar.add(txtBusqueda, BorderLayout.CENTER);
        filaBuscar.add(btnBuscar, BorderLayout.EAST);

        // Selector rápido
        comboCanciones = new JComboBox<>();
        comboCanciones.setBackground(CAMPO);
        comboCanciones.setForeground(Color.WHITE);
        comboCanciones.addActionListener(e -> seleccionarDesdeCombo());

        norte.add(filaBuscar);
        norte.add(comboCanciones);
        add(norte, BorderLayout.NORTH);

<<<<<<< Updated upstream
        //  Vista previa de la canción a eliminar
=======
        // Centro: Vista previa de la canción a eliminar
>>>>>>> Stashed changes
        JPanel centro = new JPanel(new BorderLayout(15, 0));
        centro.setBackground(CAMPO);
        centro.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(5, 20, 5, 20),
                BorderFactory.createLineBorder(ROSA, 1)));

        lblPortada = new JLabel();
        lblPortada.setPreferredSize(new Dimension(130, 130));
        lblPortada.setHorizontalAlignment(SwingConstants.CENTER);
        centro.add(lblPortada, BorderLayout.WEST);

        areaDetalle = new JTextArea();
        areaDetalle.setEditable(false);
        areaDetalle.setBackground(CAMPO);
        areaDetalle.setForeground(Color.WHITE);
        areaDetalle.setFont(new Font("SansSerif", Font.PLAIN, 13));
        areaDetalle.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
        centro.add(areaDetalle, BorderLayout.CENTER);

        add(centro, BorderLayout.CENTER);

<<<<<<< Updated upstream
        //  Botón Eliminar
=======
        // Sur: Botón Eliminar
>>>>>>> Stashed changes
        JPanel sur = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 12));
        sur.setBackground(FONDO);

        JButton btnEliminar = crearBoton("Eliminar Canción");
        btnEliminar.setPreferredSize(new Dimension(180, 40));
        btnEliminar.addActionListener(e -> confirmarEliminacion());
        sur.add(btnEliminar);

        add(sur, BorderLayout.SOUTH);
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

    private void recargarCombo() {
        comboCanciones.removeAllItems();
        for (Cancion c : DatosCosmos.CANCIONES) {
            comboCanciones.addItem(c.getClave() + " - " + c.getTitulo() + " (" + c.getArtista() + ")");
        }
        if (!DatosCosmos.CANCIONES.isEmpty()) {
            comboCanciones.setSelectedIndex(0);
            mostrarCancion(DatosCosmos.CANCIONES.get(0));
        } else {
            cancionSeleccionada = null;
            areaDetalle.setText("No hay canciones registradas.");
            lblPortada.setIcon(null);
        }
<<<<<<< Updated upstream
=======
    }

    private void seleccionarDesdeCombo() {
        int idx = comboCanciones.getSelectedIndex();
        if (idx >= 0 && idx < DatosCosmos.CANCIONES.size()) {
            mostrarCancion(DatosCosmos.CANCIONES.get(idx));
        }
    }

    private void buscarPorTexto() {
        String q = txtBusqueda.getText().trim().toLowerCase();
        if (q.isEmpty()) return;

        for (int i = 0; i < DatosCosmos.CANCIONES.size(); i++) {
            Cancion c = DatosCosmos.CANCIONES.get(i);
            if (c.getClave().toLowerCase().equals(q) || c.getTitulo().toLowerCase().contains(q)) {
                comboCanciones.setSelectedIndex(i);
                mostrarCancion(c);
                return;
            }
        }
        JOptionPane.showMessageDialog(this, "No se encontró ninguna canción con: " + q, "Sin resultados", JOptionPane.WARNING_MESSAGE);
    }

    private void mostrarCancion(Cancion c) {
        this.cancionSeleccionada = c;
        areaDetalle.setText(
                "CLAVE: " + c.getClave() + "\n" +
                "TÍTULO: " + c.getTitulo() + "\n" +
                "ARTISTA: " + c.getArtista() + "\n" +
                "ÁLBUM: " + c.getAlbum() + "\n" +
                "GÉNERO: " + c.getGenero() + "\n" +
                "DURACIÓN: " + c.getDuracion()
        );
        lblPortada.setIcon(DatosCosmos.cargarIcono(c.getRutaImagen(), 110, 110));
    }

    private void confirmarEliminacion() {
        if (cancionSeleccionada == null) {
            JOptionPane.showMessageDialog(this, "Selecciona una canción para eliminar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int resp = JOptionPane.showConfirmDialog(this,
                "¿Seguro que deseas eliminar '" + cancionSeleccionada.getTitulo() + "'?",
                "Confirmar eliminación", JOptionPane.YES_NO_OPTION);

        if (resp == JOptionPane.YES_OPTION) {
            DatosCosmos.CANCIONES.remove(cancionSeleccionada);
            for (List<Cancion> lista : DatosCosmos.PLAYLISTS.values()) {
                lista.remove(cancionSeleccionada);
            }
            JOptionPane.showMessageDialog(this, "Canción eliminada correctamente.", "Eliminada", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        }
>>>>>>> Stashed changes
    }

    private void seleccionarDesdeCombo() {
        int idx = comboCanciones.getSelectedIndex();
        if (idx >= 0 && idx < DatosCosmos.CANCIONES.size()) {
            mostrarCancion(DatosCosmos.CANCIONES.get(idx));
        }
    }

    private void buscarPorTexto() {
        String q = txtBusqueda.getText().trim().toLowerCase();
        if (q.isEmpty()) return;

        for (int i = 0; i < DatosCosmos.CANCIONES.size(); i++) {
            Cancion c = DatosCosmos.CANCIONES.get(i);
            if (c.getClave().toLowerCase().equals(q) || c.getTitulo().toLowerCase().contains(q)) {
                comboCanciones.setSelectedIndex(i);
                mostrarCancion(c);
                return;
            }
        }
        JOptionPane.showMessageDialog(this, "No se encontró ninguna canción con: " + q, "Sin resultados", JOptionPane.WARNING_MESSAGE);
    }

    private void mostrarCancion(Cancion c) {
        this.cancionSeleccionada = c;
        areaDetalle.setText(
                "CLAVE: " + c.getClave() + "\n" +
                "TÍTULO: " + c.getTitulo() + "\n" +
                "ARTISTA: " + c.getArtista() + "\n" +
                "ÁLBUM: " + c.getAlbum() + "\n" +
                "GÉNERO: " + c.getGenero() + "\n" +
                "DURACIÓN: " + c.getDuracion()
        );
        lblPortada.setIcon(DatosCosmos.cargarIcono(c.getRutaImagen(), 110, 110));
    }

    private void confirmarEliminacion() {
        if (cancionSeleccionada == null) {
            JOptionPane.showMessageDialog(this, "Selecciona una canción para eliminar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int resp = JOptionPane.showConfirmDialog(this,
                "¿Seguro que deseas eliminar '" + cancionSeleccionada.getTitulo() + "'?",
                "Confirmar eliminación", JOptionPane.YES_NO_OPTION);

        if (resp == JOptionPane.YES_OPTION) {
            DatosCosmos.CANCIONES.remove(cancionSeleccionada);
            for (List<Cancion> lista : DatosCosmos.PLAYLISTS.values()) {
                lista.remove(cancionSeleccionada);
            }
            JOptionPane.showMessageDialog(this, "Canción eliminada correctamente.", "Eliminada", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        }
    }
}
