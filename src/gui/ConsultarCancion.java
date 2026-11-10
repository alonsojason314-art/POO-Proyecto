package gui;

import control.Cancion;
import control.DatosCosmos;
import java.awt.*;
import javax.swing.*;

public class ConsultarCancion extends JDialog {

    private static final Color FONDO = Color.BLACK;
    private static final Color ROSA = new Color(0xD6, 0x24, 0x9F);
    private static final Color CAMPO = new Color(0x1A, 0x1A, 0x1A);

    private JTextField txtClaveBusqueda;
    private JTextArea areaResultado;

    public ConsultarCancion(Frame parent) {
        super(parent, "Cosmos - Consultar Canción", true);
        setSize(480, 460);
        setLocationRelativeTo(parent);
        getContentPane().setBackground(FONDO);
        setLayout(new BorderLayout(15, 15));
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        JPanel top = new JPanel(new BorderLayout(8, 8));
        top.setBackground(FONDO);
        top.setBorder(BorderFactory.createEmptyBorder(15, 20, 5, 20));

        JLabel lbl = new JLabel("Clave o Título de la Canción:");
        lbl.setForeground(Color.WHITE);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 13));
        top.add(lbl, BorderLayout.NORTH);

        txtClaveBusqueda = new JTextField();
        txtClaveBusqueda.setBackground(CAMPO);
        txtClaveBusqueda.setForeground(Color.WHITE);
        txtClaveBusqueda.setCaretColor(Color.WHITE);
        txtClaveBusqueda.setBorder(BorderFactory.createLineBorder(ROSA, 1));
        top.add(txtClaveBusqueda, BorderLayout.CENTER);

        JButton btnBuscar = new JButton("Consultar");
        btnBuscar.setBackground(ROSA);
        btnBuscar.setForeground(Color.WHITE);
        btnBuscar.setFocusPainted(false);
        btnBuscar.setBorderPainted(false);
        btnBuscar.addActionListener(e -> buscar());
        top.add(btnBuscar, BorderLayout.EAST);

        add(top, BorderLayout.NORTH);

        areaResultado = new JTextArea();
        areaResultado.setEditable(false);
        areaResultado.setBackground(CAMPO);
        areaResultado.setForeground(Color.WHITE);
        areaResultado.setFont(new Font("SansSerif", Font.PLAIN, 13));
        areaResultado.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        JScrollPane scroll = new JScrollPane(areaResultado);
        scroll.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));
        scroll.getViewport().setBackground(CAMPO);
        add(scroll, BorderLayout.CENTER);
    }

    private void buscar() {
        String q = txtClaveBusqueda.getText().trim().toLowerCase();
        if (q.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingresa una clave o título para consultar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        for (Cancion c : DatosCosmos.CANCIONES) {
            if (c.getClave().toLowerCase().equals(q) || c.getTitulo().toLowerCase().contains(q)) {
                areaResultado.setText(
                        "CLAVE: " + c.getClave() + "\n" +
                        "TÍTULO: " + c.getTitulo() + "\n" +
                        "ARTISTA: " + c.getArtista() + "\n" +
                        "ÁLBUM: " + c.getAlbum() + "\n" +
                        "GÉNERO: " + c.getGenero() + "\n" +
                        "AÑO: " + c.getAnio() + "\n" +
                        "DURACIÓN: " + c.getDuracion() + "\n\n" +
                        "--- LETRA ---\n" + c.getLetra()
                );
                return;
            }
        }
        areaResultado.setText("No se encontró ninguna canción con esa clave o título.");
    }
}
