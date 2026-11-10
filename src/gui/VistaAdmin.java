package gui;

import control.Cancion;
import control.DatosCosmos;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class VistaAdmin extends JFrame {

    private static final Color FONDO = Color.BLACK;
    private static final Color ROSA = new Color(0xD6, 0x24, 0x9F);
    private static final Color ROSA_HOVER = new Color(0xE8, 0x4F, 0xB8);
    private static final Color CAMPO = new Color(0x1A, 0x1A, 0x1A);

    private final String nombreAdmin;
    private final List<JButton> botonesMenu = new ArrayList<>();
    private JPanel panelGridCanciones;
    private JTextField txtBuscar;
    private PanelReproductorDerecho panelDerecho;

    public VistaAdmin(String nombreAdmin) {
        this.nombreAdmin = nombreAdmin;
        configurarVentana();
        inicializarComponentes();
        actualizarCatalogo("");
    }

    private void configurarVentana() {
        setTitle("Cosmos - Panel de Administrador");
        setSize(1200, 720);
        setMinimumSize(new Dimension(1050, 650));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(FONDO);
        setLayout(new BorderLayout());
    }

    private void inicializarComponentes() {
        panelDerecho = new PanelReproductorDerecho();
        add(crearBarraIzquierda(), BorderLayout.WEST);
        add(crearPanelCentral(), BorderLayout.CENTER);
        add(panelDerecho, BorderLayout.EAST);
    }

    private JPanel crearBarraIzquierda() {
        JPanel sidebar = new JPanel(new BorderLayout());
        sidebar.setPreferredSize(new Dimension(240, 720));
        sidebar.setBackground(FONDO);
        sidebar.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, new Color(0x2C, 0x2C, 0x2C)));

        JPanel panelOpciones = new JPanel();
        panelOpciones.setBackground(FONDO);
        panelOpciones.setLayout(new BoxLayout(panelOpciones, BoxLayout.Y_AXIS));
        panelOpciones.setBorder(BorderFactory.createEmptyBorder(18, 15, 20, 15));

        // Esquina superior izquierda: CL.png a un lado de cosmos_letras.png
        JPanel panelMarca = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        panelMarca.setBackground(FONDO);
        panelMarca.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblIconoCL = new JLabel(DatosCosmos.cargarIcono("/recursos/CL.png", 42, 42));
        JLabel lblLetras = new JLabel(DatosCosmos.cargarIcono("/recursos/cosmos_letras.png", 145, 38));
        panelMarca.add(lblIconoCL);
        panelMarca.add(lblLetras);

        panelOpciones.add(panelMarca);
        panelOpciones.add(Box.createVerticalStrut(28));

        JButton btnConsultar = crearBotonMenu("Consultar Canción", "/recursos/buscar_32.png");
        JButton btnAgregar = crearBotonMenu("Agregar Canción", "/recursos/agregar_32.png");
        JButton btnEditar = crearBotonMenu("Editar Canción", "/recursos/editar_32.png");
        JButton btnEliminar = crearBotonMenu("Eliminar Canción", "/recursos/eliminar_32.png");

        btnConsultar.addActionListener(e -> {
            seleccionarBoton(btnConsultar);
            new ConsultarCancion(this).setVisible(true);
        });

        btnAgregar.addActionListener(e -> {
            seleccionarBoton(btnAgregar);
            Agregarcancion dlg = new Agregarcancion(this);
            dlg.setVisible(true);
            actualizarCatalogo(txtBuscar.getText().trim());
        });

        btnEditar.addActionListener(e -> {
            seleccionarBoton(btnEditar);
            EditarCancion dlg = new EditarCancion(this);
            dlg.setVisible(true);
            actualizarCatalogo(txtBuscar.getText().trim());
            if (!DatosCosmos.CANCIONES.isEmpty()) {
                panelDerecho.cargarCancion(DatosCosmos.CANCIONES.get(0));
            }
        });

        btnEliminar.addActionListener(e -> {
            seleccionarBoton(btnEliminar);
            EliminarCancion dlg = new EliminarCancion(this);
            dlg.setVisible(true);
            actualizarCatalogo(txtBuscar.getText().trim());
        });

        panelOpciones.add(btnConsultar);
        panelOpciones.add(Box.createVerticalStrut(12));
        panelOpciones.add(btnAgregar);
        panelOpciones.add(Box.createVerticalStrut(12));
        panelOpciones.add(btnEditar);
        panelOpciones.add(Box.createVerticalStrut(12));
        panelOpciones.add(btnEliminar);

        sidebar.add(panelOpciones, BorderLayout.NORTH);

        JPanel panelPerfil = new JPanel(new BorderLayout(10, 0));
        panelPerfil.setBackground(CAMPO);
        panelPerfil.setBorder(BorderFactory.createEmptyBorder(12, 15, 12, 15));

        JLabel lblIconoUser = new JLabel(DatosCosmos.cargarIcono("/recursos/LogoCos.png", 36, 36));
        panelPerfil.add(lblIconoUser, BorderLayout.WEST);

        JPanel textosPerfil = new JPanel(new GridLayout(2, 1));
        textosPerfil.setBackground(CAMPO);
        JLabel lblRol = new JLabel(nombreAdmin + " (Admin)");
        lblRol.setForeground(Color.WHITE);
        lblRol.setFont(new Font("SansSerif", Font.BOLD, 13));
        JLabel lblVersion = new JLabel("Primer Version");
        lblVersion.setForeground(ROSA);
        lblVersion.setFont(new Font("SansSerif", Font.PLAIN, 11));
        textosPerfil.add(lblRol);
        textosPerfil.add(lblVersion);
        panelPerfil.add(textosPerfil, BorderLayout.CENTER);

        JButton btnSalir = new JButton("Salir");
        btnSalir.setBackground(ROSA);
        btnSalir.setForeground(Color.WHITE);
        btnSalir.setFocusPainted(false);
        btnSalir.setBorderPainted(false);
        btnSalir.addActionListener(e -> {
            dispose();
            new MenuPrincipal().setVisible(true);
        });
        panelPerfil.add(btnSalir, BorderLayout.EAST);

        sidebar.add(panelPerfil, BorderLayout.SOUTH);
        return sidebar;
    }

    private JButton crearBotonMenu(String texto, String rutaIcono) {
        JButton btn = new JButton(texto);
        btn.setMaximumSize(new Dimension(210, 44));
        btn.setPreferredSize(new Dimension(210, 44));
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setBackground(CAMPO);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("SansSerif", Font.BOLD, 13));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        ImageIcon icon = DatosCosmos.cargarIcono(rutaIcono, 22, 22);
        if (icon != null) {
            btn.setIcon(icon);
            btn.setIconTextGap(10);
        }

        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (btn.getBackground() != ROSA) btn.setBackground(ROSA_HOVER);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                if (btn.getBackground() != ROSA) btn.setBackground(CAMPO);
            }
        });

        botonesMenu.add(btn);
        return btn;
    }

    private void seleccionarBoton(JButton seleccionado) {
        for (JButton b : botonesMenu) {
            b.setBackground(b == seleccionado ? ROSA : CAMPO);
        }
    }

    private JPanel crearPanelCentral() {
        JPanel central = new JPanel(new BorderLayout(0, 15));
        central.setBackground(FONDO);
        central.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));

        JPanel topBar = new JPanel(new BorderLayout(10, 0));
        topBar.setBackground(CAMPO);
        topBar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ROSA, 1),
                BorderFactory.createEmptyBorder(6, 12, 6, 12)));

        JLabel lblIconBuscar = new JLabel(DatosCosmos.cargarIcono("/recursos/buscar_32.png", 20, 20));
        topBar.add(lblIconBuscar, BorderLayout.WEST);

        txtBuscar = new JTextField();
        txtBuscar.setBackground(CAMPO);
        txtBuscar.setForeground(Color.WHITE);
        txtBuscar.setCaretColor(Color.WHITE);
        txtBuscar.setBorder(null);
        txtBuscar.setFont(new Font("SansSerif", Font.PLAIN, 14));
        txtBuscar.addActionListener(e -> actualizarCatalogo(txtBuscar.getText().trim()));
        topBar.add(txtBuscar, BorderLayout.CENTER);

        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.setBackground(ROSA);
        btnBuscar.setForeground(Color.WHITE);
        btnBuscar.setFocusPainted(false);
        btnBuscar.setBorderPainted(false);
        btnBuscar.addActionListener(e -> actualizarCatalogo(txtBuscar.getText().trim()));
        topBar.add(btnBuscar, BorderLayout.EAST);

        central.add(topBar, BorderLayout.NORTH);

        panelGridCanciones = new JPanel(new GridLayout(0, 2, 18, 18));
        panelGridCanciones.setBackground(FONDO);

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(FONDO);
        wrapper.add(panelGridCanciones, BorderLayout.NORTH);

        JScrollPane scroll = new JScrollPane(wrapper);
        scroll.setBackground(FONDO);
        scroll.getViewport().setBackground(FONDO);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(16);

        central.add(scroll, BorderLayout.CENTER);
        return central;
    }

    public final void actualizarCatalogo(String filtro) {
        panelGridCanciones.removeAll();
        String q = filtro.toLowerCase();

        for (Cancion c : DatosCosmos.CANCIONES) {
            if (q.isEmpty() || c.getTitulo().toLowerCase().contains(q) || c.getArtista().toLowerCase().contains(q)) {
                panelGridCanciones.add(crearTarjetaCancion(c));
            }
        }
        panelGridCanciones.revalidate();
        panelGridCanciones.repaint();
    }

    private JPanel crearTarjetaCancion(Cancion c) {
        JPanel card = new JPanel(new BorderLayout(12, 0));
        card.setBackground(CAMPO);
        card.setPreferredSize(new Dimension(280, 95));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0x2E, 0x2E, 0x2E), 1),
                BorderFactory.createEmptyBorder(10, 12, 10, 12)));
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JLabel lblImg = new JLabel(DatosCosmos.cargarIcono(c.getRutaImagen(), 70, 70));
        card.add(lblImg, BorderLayout.WEST);

        JPanel info = new JPanel(new GridLayout(3, 1, 0, 2));
        info.setOpaque(false);

        JLabel lblTitulo = new JLabel(c.getTitulo());
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 14));

        JLabel lblArtista = new JLabel(c.getArtista() + " • " + c.getAlbum());
        lblArtista.setForeground(new Color(0xB3, 0xB3, 0xB3));
        lblArtista.setFont(new Font("SansSerif", Font.PLAIN, 12));

        JLabel lblDur = new JLabel("Duración: " + c.getDuracion() + "  [" + c.getClave() + "]");
        lblDur.setForeground(ROSA);
        lblDur.setFont(new Font("SansSerif", Font.BOLD, 12));

        info.add(lblTitulo);
        info.add(lblArtista);
        info.add(lblDur);
        card.add(info, BorderLayout.CENTER);

        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                panelDerecho.cargarCancion(c);
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                card.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(ROSA, 1),
                        BorderFactory.createEmptyBorder(10, 12, 10, 12)));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                card.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(0x2E, 0x2E, 0x2E), 1),
                        BorderFactory.createEmptyBorder(10, 12, 10, 12)));
            }
        });

        return card;
    }
}