package gui;

import control.Cancion;
import control.DatosCosmos;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.List;
import javax.swing.*;

public class VistaUsuario extends JFrame {

    private static final Color FONDO = Color.BLACK;
    private static final Color ROSA = new Color(0xD6, 0x24, 0x9F);
    private static final Color ROSA_HOVER = new Color(0xE8, 0x4F, 0xB8);
    private static final Color CAMPO = new Color(0x1A, 0x1A, 0x1A);

    private final String nombreUsuario;
    private final List<JButton> botonesNav = new ArrayList<>();
    private JPanel contenedorDinamicoScroll;
    private JTextField txtBuscar;
    private PanelReproductorDerecho panelDerecho;
    private String vistaActual = "CANCIONES";

    public VistaUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
        configurarVentana();
        inicializarComponentes();
        mostrarVistaCanciones("");
    }

    private void configurarVentana() {
        setTitle("Cosmos - Reproductor");
        setSize(1220, 720);
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

        JPanel nav = new JPanel();
        nav.setBackground(FONDO);
        nav.setLayout(new BoxLayout(nav, BoxLayout.Y_AXIS));
        nav.setBorder(BorderFactory.createEmptyBorder(20, 15, 20, 15));

        // Esquina superior izquierda: CL.png a un lado de cosmos_letras.png
        JPanel panelMarca = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        panelMarca.setBackground(FONDO);
        panelMarca.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblIconoCL = new JLabel(DatosCosmos.cargarIcono("/recursos/CL.png", 42, 42));
        JLabel lblLetras = new JLabel(DatosCosmos.cargarIcono("/recursos/cosmos_letras.png", 145, 38));
        panelMarca.add(lblIconoCL);
        panelMarca.add(lblLetras);

        nav.add(panelMarca);
        nav.add(Box.createVerticalStrut(28));

        JButton btnCanciones = crearBotonNav("Canciones", null);
        JButton btnArtista = crearBotonNav("Artista", null);
        JButton btnAlbum = crearBotonNav("Album", null);
        JButton btnPlaylist = crearBotonNav("Lista de reproducción", null);
        JButton btnCrearPlaylist = crearBotonNav("Crear Playlist", "/recursos/agregar_32.png");

        btnCanciones.addActionListener(e -> {
            vistaActual = "CANCIONES";
            marcarBotonActivo(btnCanciones);
            mostrarVistaCanciones(txtBuscar.getText().trim());
        });

        btnArtista.addActionListener(e -> {
            vistaActual = "ARTISTA";
            marcarBotonActivo(btnArtista);
            mostrarVistaArtistas(txtBuscar.getText().trim());
        });

        btnAlbum.addActionListener(e -> {
            vistaActual = "ALBUM";
            marcarBotonActivo(btnAlbum);
            mostrarVistaAlbumes(txtBuscar.getText().trim());
        });

        btnPlaylist.addActionListener(e -> {
            vistaActual = "PLAYLIST";
            marcarBotonActivo(btnPlaylist);
            mostrarVistaPlaylists(txtBuscar.getText().trim());
        });

        btnCrearPlaylist.addActionListener(e -> {
            marcarBotonActivo(btnCrearPlaylist);
            CrearPlaylist dlg = new CrearPlaylist(this);
            dlg.setVisible(true);
            vistaActual = "PLAYLIST";
            marcarBotonActivo(btnPlaylist);
            mostrarVistaPlaylists("");
        });

        nav.add(btnCanciones);
        nav.add(Box.createVerticalStrut(10));
        nav.add(btnArtista);
        nav.add(Box.createVerticalStrut(10));
        nav.add(btnAlbum);
        nav.add(Box.createVerticalStrut(10));
        nav.add(btnPlaylist);
        nav.add(Box.createVerticalStrut(22));
        nav.add(btnCrearPlaylist);

        marcarBotonActivo(btnCanciones);
        sidebar.add(nav, BorderLayout.NORTH);

        // Footer Usuario
        JPanel panelPerfil = new JPanel(new BorderLayout(10, 0));
        panelPerfil.setBackground(CAMPO);
        panelPerfil.setBorder(BorderFactory.createEmptyBorder(12, 15, 12, 15));

        JLabel lblIconoUser = new JLabel(DatosCosmos.cargarIcono("/recursos/LogoCos.png", 36, 36));
        panelPerfil.add(lblIconoUser, BorderLayout.WEST);

        JPanel textosPerfil = new JPanel(new GridLayout(2, 1));
        textosPerfil.setBackground(CAMPO);
        JLabel lblUser = new JLabel(nombreUsuario);
        lblUser.setForeground(Color.WHITE);
        lblUser.setFont(new Font("SansSerif", Font.BOLD, 13));
        JLabel lblVer = new JLabel("Primer Version");
        lblVer.setForeground(ROSA);
        lblVer.setFont(new Font("SansSerif", Font.PLAIN, 11));
        textosPerfil.add(lblUser);
        textosPerfil.add(lblVer);
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

    private JButton crearBotonNav(String texto, String rutaIcono) {
        JButton btn = new JButton(texto);
        btn.setMaximumSize(new Dimension(210, 44));
        btn.setPreferredSize(new Dimension(210, 44));
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setBackground(CAMPO);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("SansSerif", Font.BOLD, 13));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(8, 14, 8, 14));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        if (rutaIcono != null) {
            ImageIcon ic = DatosCosmos.cargarIcono(rutaIcono, 22, 22);
            if (ic != null) {
                btn.setIcon(ic);
                btn.setIconTextGap(10);
            }
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

        botonesNav.add(btn);
        return btn;
    }

    private void marcarBotonActivo(JButton activo) {
        for (JButton b : botonesNav) {
            b.setBackground(b == activo ? ROSA : CAMPO);
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
        txtBuscar.addActionListener(e -> ejecutarBusqueda());
        topBar.add(txtBuscar, BorderLayout.CENTER);

        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.setBackground(ROSA);
        btnBuscar.setForeground(Color.WHITE);
        btnBuscar.setFocusPainted(false);
        btnBuscar.setBorderPainted(false);
        btnBuscar.addActionListener(e -> ejecutarBusqueda());
        topBar.add(btnBuscar, BorderLayout.EAST);

        central.add(topBar, BorderLayout.NORTH);

        contenedorDinamicoScroll = new JPanel(new BorderLayout());
        contenedorDinamicoScroll.setBackground(FONDO);

        JScrollPane scrollCentral = new JScrollPane(contenedorDinamicoScroll);
        scrollCentral.setBackground(FONDO);
        scrollCentral.getViewport().setBackground(FONDO);
        scrollCentral.setBorder(null);
        scrollCentral.getVerticalScrollBar().setUnitIncrement(16);

        central.add(scrollCentral, BorderLayout.CENTER);
        return central;
    }

    private void ejecutarBusqueda() {
        String filtro = txtBuscar.getText().trim();
        switch (vistaActual) {
            case "ARTISTA": mostrarVistaArtistas(filtro); break;
            case "ALBUM": mostrarVistaAlbumes(filtro); break;
            case "PLAYLIST": mostrarVistaPlaylists(filtro); break;
            default: mostrarVistaCanciones(filtro); break;
        }
    }

    // PÁGINA 2: Vista del Usuario - Canciones
    private void mostrarVistaCanciones(String filtro) {
        contenedorDinamicoScroll.removeAll();
        JPanel grid = new JPanel(new GridLayout(0, 2, 18, 18));
        grid.setBackground(FONDO);

        String q = filtro.toLowerCase();
        for (Cancion c : DatosCosmos.CANCIONES) {
            if (q.isEmpty() || c.getTitulo().toLowerCase().contains(q) || c.getArtista().toLowerCase().contains(q)) {
                grid.add(crearTarjetaCancion(c));
            }
        }

        contenedorDinamicoScroll.add(grid, BorderLayout.NORTH);
        contenedorDinamicoScroll.revalidate();
        contenedorDinamicoScroll.repaint();
    }

    // PÁGINA 3: Vista del Usuario - Álbumes
    private void mostrarVistaAlbumes(String filtro) {
        contenedorDinamicoScroll.removeAll();
        JPanel grid = new JPanel(new GridLayout(0, 2, 18, 18));
        grid.setBackground(FONDO);

        Map<String, List<Cancion>> porAlbum = new LinkedHashMap<>();
        for (Cancion c : DatosCosmos.CANCIONES) {
            if (filtro.isEmpty() || c.getAlbum().toLowerCase().contains(filtro.toLowerCase())) {
                porAlbum.computeIfAbsent(c.getAlbum(), k -> new ArrayList<>()).add(c);
            }
        }

        for (Map.Entry<String, List<Cancion>> entry : porAlbum.entrySet()) {
            String nombreAlbum = entry.getKey();
            List<Cancion> pistas = entry.getValue();
            Cancion ref = pistas.get(0);

            JPanel card = crearTarjetaAgrupada(nombreAlbum, ref.getArtista(), pistas.size() + " canciones", ref.getRutaImagen());
            card.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    mostrarDetalleColeccion("Album", nombreAlbum, ref.getArtista(), ref.getRutaImagen(), pistas);
                }
            });
            grid.add(card);
        }

        contenedorDinamicoScroll.add(grid, BorderLayout.NORTH);
        contenedorDinamicoScroll.revalidate();
        contenedorDinamicoScroll.repaint();
    }

    // PÁGINA 5 y 6: Vista del Usuario - Artistas
    private void mostrarVistaArtistas(String filtro) {
        contenedorDinamicoScroll.removeAll();
        JPanel grid = new JPanel(new GridLayout(0, 2, 18, 18));
        grid.setBackground(FONDO);

        Map<String, List<Cancion>> porArtista = new LinkedHashMap<>();
        for (Cancion c : DatosCosmos.CANCIONES) {
            if (filtro.isEmpty() || c.getArtista().toLowerCase().contains(filtro.toLowerCase())) {
                porArtista.computeIfAbsent(c.getArtista(), k -> new ArrayList<>()).add(c);
            }
        }

        for (Map.Entry<String, List<Cancion>> entry : porArtista.entrySet()) {
            String artista = entry.getKey();
            List<Cancion> pistas = entry.getValue();
            Cancion ref = pistas.get(0);

            JPanel card = crearTarjetaAgrupada(artista, "Artista verificado", pistas.size() + " pistas", ref.getRutaImagen());
            card.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    mostrarDetalleColeccion("Artista", artista, "Discografía en Cosmos", ref.getRutaImagen(), pistas);
                }
            });
            grid.add(card);
        }

        contenedorDinamicoScroll.add(grid, BorderLayout.NORTH);
        contenedorDinamicoScroll.revalidate();
        contenedorDinamicoScroll.repaint();
    }

// PÁGINA 8: Vista del Usuario - Playlists
    private void mostrarVistaPlaylists(String filtro) {
        contenedorDinamicoScroll.removeAll();
        JPanel grid = new JPanel(new GridLayout(0, 2, 18, 18));
        grid.setBackground(FONDO);

        for (Map.Entry<String, List<Cancion>> entry : DatosCosmos.PLAYLISTS.entrySet()) {
            String nombrePl = entry.getKey();
            if (!filtro.isEmpty() && !nombrePl.toLowerCase().contains(filtro.toLowerCase())) continue;

            List<Cancion> pistas = entry.getValue();

            String rutaImg = pistas.isEmpty() 
                    ? "COVER:" + nombrePl + ":#D6249F:#1A1A1A" 
                    : pistas.get(0).getRutaImagen();

            JPanel card = crearTarjetaAgrupada(nombrePl, "Creada por " + nombreUsuario, pistas.size() + " canciones", rutaImg);
            card.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    mostrarDetalleColeccion("Playlist", nombrePl, nombreUsuario, rutaImg, pistas);
                }
            });
            grid.add(card);
        }

        contenedorDinamicoScroll.add(grid, BorderLayout.NORTH);
        contenedorDinamicoScroll.revalidate();
        contenedorDinamicoScroll.repaint();
    }

    // PÁGINAS 4, 7, 9 y 10: Detalle de Álbum, Artista o Playlist con lista numerada de canciones
    private void mostrarDetalleColeccion(String tipo, String titulo, String subtitulo, String rutaImg, List<Cancion> canciones) {
        contenedorDinamicoScroll.removeAll();

        JPanel panelDetalle = new JPanel(new BorderLayout(0, 20));
        panelDetalle.setBackground(FONDO);

        // Encabezado (Portada + Tipo + Nombre + Subtítulo)
        JPanel header = new JPanel(new BorderLayout(20, 0));
        header.setBackground(CAMPO);
        header.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ROSA, 1),
                BorderFactory.createEmptyBorder(18, 18, 18, 18)));

        JLabel lblCover = new JLabel(DatosCosmos.cargarIcono(rutaImg, 120, 120));
        header.add(lblCover, BorderLayout.WEST);

        JPanel infoHeader = new JPanel();
        infoHeader.setBackground(CAMPO);
        infoHeader.setLayout(new BoxLayout(infoHeader, BoxLayout.Y_AXIS));

        JLabel lblTipo = new JLabel(tipo.toUpperCase());
        lblTipo.setForeground(ROSA);
        lblTipo.setFont(new Font("SansSerif", Font.BOLD, 13));

        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 26));

        JLabel lblSub = new JLabel(subtitulo + " • " + canciones.size() + " canciones");
        lblSub.setForeground(new Color(0xB3, 0xB3, 0xB3));
        lblSub.setFont(new Font("SansSerif", Font.PLAIN, 14));

        infoHeader.add(lblTipo);
        infoHeader.add(Box.createVerticalStrut(6));
        infoHeader.add(lblTitulo);
        infoHeader.add(Box.createVerticalStrut(6));
        infoHeader.add(lblSub);

        header.add(infoHeader, BorderLayout.CENTER);
        panelDetalle.add(header, BorderLayout.NORTH);

        // Lista vertical de pistas (#, Nombre de la Canción, Artista, Duración)
        JPanel listaPistas = new JPanel();
        listaPistas.setBackground(FONDO);
        listaPistas.setLayout(new BoxLayout(listaPistas, BoxLayout.Y_AXIS));

        int index = 1;
        for (Cancion c : canciones) {
            JPanel fila = new JPanel(new BorderLayout(15, 0));
            fila.setBackground(CAMPO);
            fila.setMaximumSize(new Dimension(2000, 62));
            fila.setPreferredSize(new Dimension(600, 62));
            fila.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
            fila.setCursor(new Cursor(Cursor.HAND_CURSOR));

            JPanel izq = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
            izq.setOpaque(false);

            JLabel lblNum = new JLabel(String.valueOf(index++));
            lblNum.setForeground(ROSA);
            lblNum.setFont(new Font("SansSerif", Font.BOLD, 15));

            JLabel lblMini = new JLabel(DatosCosmos.cargarIcono(c.getRutaImagen(), 40, 40));

            JPanel textos = new JPanel(new GridLayout(2, 1));
            textos.setOpaque(false);
            JLabel lblNom = new JLabel(c.getTitulo());
            lblNom.setForeground(Color.WHITE);
            lblNom.setFont(new Font("SansSerif", Font.BOLD, 14));
            JLabel lblArt = new JLabel(c.getArtista());
            lblArt.setForeground(new Color(0xB3, 0xB3, 0xB3));
            lblArt.setFont(new Font("SansSerif", Font.PLAIN, 12));
            textos.add(lblNom);
            textos.add(lblArt);

            izq.add(lblNum);
            izq.add(lblMini);
            izq.add(textos);

            JLabel lblDur = new JLabel(c.getDuracion());
            lblDur.setForeground(ROSA);
            lblDur.setFont(new Font("SansSerif", Font.BOLD, 13));

            fila.add(izq, BorderLayout.WEST);
            fila.add(lblDur, BorderLayout.EAST);

            fila.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) { panelDerecho.cargarCancion(c); }
                @Override
                public void mouseEntered(MouseEvent e) { fila.setBackground(new Color(0x2A, 0x2A, 0x2A)); }
                @Override
                public void mouseExited(MouseEvent e) { fila.setBackground(CAMPO); }
            });

            listaPistas.add(fila);
            listaPistas.add(Box.createVerticalStrut(8));
        }

        panelDetalle.add(listaPistas, BorderLayout.CENTER);
        contenedorDinamicoScroll.add(panelDetalle, BorderLayout.NORTH);
        contenedorDinamicoScroll.revalidate();
        contenedorDinamicoScroll.repaint();
    }

    private JPanel crearTarjetaCancion(Cancion c) {
        JPanel card = crearTarjetaAgrupada(c.getTitulo(), c.getArtista(), "Duración: " + c.getDuracion(), c.getRutaImagen());
        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                panelDerecho.cargarCancion(c);
            }
        });
        return card;
    }

    private JPanel crearTarjetaAgrupada(String linea1, String linea2, String linea3, String rutaImagen) {
        JPanel card = new JPanel(new BorderLayout(12, 0));
        card.setBackground(CAMPO);
        card.setPreferredSize(new Dimension(280, 95));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0x2E, 0x2E, 0x2E), 1),
                BorderFactory.createEmptyBorder(10, 12, 10, 12)));
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JLabel lblImg = new JLabel(DatosCosmos.cargarIcono(rutaImagen, 70, 70));
        card.add(lblImg, BorderLayout.WEST);

        JPanel info = new JPanel(new GridLayout(3, 1, 0, 2));
        info.setOpaque(false);

        JLabel l1 = new JLabel(linea1);
        l1.setForeground(Color.WHITE);
        l1.setFont(new Font("SansSerif", Font.BOLD, 14));

        JLabel l2 = new JLabel(linea2);
        l2.setForeground(new Color(0xB3, 0xB3, 0xB3));
        l2.setFont(new Font("SansSerif", Font.PLAIN, 12));

        JLabel l3 = new JLabel(linea3);
        l3.setForeground(ROSA);
        l3.setFont(new Font("SansSerif", Font.BOLD, 12));

        info.add(l1);
        info.add(l2);
        info.add(l3);
        card.add(info, BorderLayout.CENTER);

        card.addMouseListener(new MouseAdapter() {
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