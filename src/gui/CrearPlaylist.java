package gui;

import control.Cancion;
import control.DatosCosmos;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class CrearPlaylist extends JDialog {

    private static final Color FONDO = Color.BLACK;
    private static final Color ROSA = new Color(0xD6, 0x24, 0x9F);
    private static final Color CAMPO = new Color(0x1A, 0x1A, 0x1A);

    private JTextField txtNombrePlaylist;
    private final List<JCheckBox> checksCanciones = new ArrayList<>();

    public CrearPlaylist(Frame parent) {
        super(parent, "Cosmos - Crear Nueva Playlist", true);
        setSize(450, 480);
        setLocationRelativeTo(parent);
        getContentPane().setBackground(FONDO);
        setLayout(new BorderLayout(12, 12));
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        JPanel norte = new JPanel(new BorderLayout(6, 6));
        norte.setBackground(FONDO);
        norte.setBorder(BorderFactory.createEmptyBorder(15, 20, 5, 20));

        JLabel lbl = new JLabel("Nombre de la nueva Playlist:");
        lbl.setForeground(Color.WHITE);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 14));
        norte.add(lbl, BorderLayout.NORTH);

        txtNombrePlaylist = new JTextField();
        txtNombrePlaylist.setPreferredSize(new Dimension(200, 36));
        txtNombrePlaylist.setBackground(CAMPO);
        txtNombrePlaylist.setForeground(Color.WHITE);
        txtNombrePlaylist.setCaretColor(Color.WHITE);
        txtNombrePlaylist.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ROSA, 1),
                BorderFactory.createEmptyBorder(4, 10, 4, 10)));
        norte.add(txtNombrePlaylist, BorderLayout.CENTER);

        add(norte, BorderLayout.NORTH);

        JPanel panelChecks = new JPanel();
        panelChecks.setBackground(CAMPO);
        panelChecks.setLayout(new BoxLayout(panelChecks, BoxLayout.Y_AXIS));
        panelChecks.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        for (Cancion c : DatosCosmos.CANCIONES) {
            JCheckBox chk = new JCheckBox(c.getTitulo() + " - " + c.getArtista() + " (" + c.getDuracion() + ")");
            chk.setBackground(CAMPO);
            chk.setForeground(Color.WHITE);
            chk.setFocusPainted(false);
            chk.putClientProperty("cancion", c);
            checksCanciones.add(chk);
            panelChecks.add(chk);
            panelChecks.add(Box.createVerticalStrut(6));
        }

        JScrollPane scroll = new JScrollPane(panelChecks);
        scroll.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(ROSA), "Selecciona las canciones", 0, 0, null, Color.WHITE));
        scroll.getViewport().setBackground(CAMPO);

        JPanel wrapperCentro = new JPanel(new BorderLayout());
        wrapperCentro.setBackground(FONDO);
        wrapperCentro.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
        wrapperCentro.add(scroll, BorderLayout.CENTER);
        add(wrapperCentro, BorderLayout.CENTER);

        JPanel sur = new JPanel(new BorderLayout());
        sur.setBackground(FONDO);
        sur.setBorder(BorderFactory.createEmptyBorder(5, 20, 18, 20));

        JButton btnCrear = new JButton("Crear Playlist");
        btnCrear.setPreferredSize(new Dimension(200, 40));
        btnCrear.setBackground(ROSA);
        btnCrear.setForeground(Color.WHITE);
        btnCrear.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnCrear.setFocusPainted(false);
        btnCrear.setBorderPainted(false);
        btnCrear.addActionListener(e -> guardarPlaylist());
        sur.add(btnCrear, BorderLayout.CENTER);

        add(sur, BorderLayout.SOUTH);
    }

    private void guardarPlaylist() {
        String nombre = txtNombrePlaylist.getText().trim();
        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Escribe un nombre para la Playlist.", "Campo obligatorio", JOptionPane.WARNING_MESSAGE);
            return;
        }

        List<Cancion> seleccionadas = new ArrayList<>();
        for (JCheckBox chk : checksCanciones) {
            if (chk.isSelected()) {
                seleccionadas.add((Cancion) chk.getClientProperty("cancion"));
            }
        }

        if (seleccionadas.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Selecciona al menos una canción.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        DatosCosmos.PLAYLISTS.put(nombre, seleccionadas);
        JOptionPane.showMessageDialog(this, "Playlist '" + nombre + "' creada con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        dispose();
    }
}
