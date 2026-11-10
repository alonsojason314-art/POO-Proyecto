package control;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.awt.print.*;
import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class DatosCosmos {

    public static final List<Cancion> CANCIONES = new ArrayList<>();
    public static final Map<String, List<Cancion>> PLAYLISTS = new LinkedHashMap<>();

    static {
        String letraDemo = "Si no encuentras\nmotivos\nPara seguir conmigo\n"
                + "¿Para qué continuar?\nEs mejor terminar\ncomo amigos\n"
                + "Que ser como\nenemigos\nEsperando atacar";

        // Usamos rutas "COVER:..." para que cada canción/álbum/artista tenga su propia portada única
        CANCIONES.add(new Cancion("C01", "Starboy", "Pop/R&B", "The Weeknd", "2016", "Starboy", "3:50", "COVER:Starboy:#D6249F:#1A103C", letraDemo));
        CANCIONES.add(new Cancion("C02", "Blinding Lights", "Synthwave", "The Weeknd", "2020", "After Hours", "3:20", "COVER:After Hours:#FF2A54:#2B0918", letraDemo));
        CANCIONES.add(new Cancion("C03", "Without Me", "Hip Hop", "Eminem", "2002", "The Eminem Show", "4:50", "COVER:Eminem:#00B4D8:#03045E", letraDemo));
        CANCIONES.add(new Cancion("C04", "Stay With Me", "City Pop", "Miki Matsubara", "1979", "Pocket Park", "5:12", "COVER:Pocket Park:#9D4EDD:#240046", letraDemo));
        CANCIONES.add(new Cancion("C05", "Do I Wanna Know?", "Indie Rock", "Arctic Monkeys", "2013", "AM", "4:32", "COVER:AM:#F77F00:#1F1300", letraDemo));
        CANCIONES.add(new Cancion("C06", "Instant Crush", "Electronic", "Daft Punk", "2013", "Random Access Memories", "5:37", "COVER:RAM:#06D6A0:#073B4C", letraDemo));

        List<Cancion> favs = new ArrayList<>();
        favs.add(CANCIONES.get(0));
        favs.add(CANCIONES.get(3));
        PLAYLISTS.put("Favoritas Cosmos", favs);

        List<Cancion> synth = new ArrayList<>();
        synth.add(CANCIONES.get(1));
        synth.add(CANCIONES.get(5));
        PLAYLISTS.put("Noche Synthwave", synth);
    }

    /**
     * Carga un icono de /recursos, un archivo del disco duro, o genera una carátula musical única.
     */
    public static ImageIcon cargarIcono(String ruta, int ancho, int alto) {
        if (ruta == null || ruta.trim().isEmpty()) {
            return generarPortadaMusical("Cosmos", "#D6249F", "#1A1A1A", ancho, alto);
        }
        try {
            if (ruta.startsWith("COVER:")) {
                String[] partes = ruta.split(":");
                String texto = partes.length > 1 ? partes[1] : "Music";
                String c1 = partes.length > 2 ? partes[2] : "#D6249F";
                String c2 = partes.length > 3 ? partes[3] : "#1A1A1A";
                return generarPortadaMusical(texto, c1, c2, ancho, alto);
            }

            Image img = null;
            File archivo = new File(ruta);
            if (archivo.exists() && archivo.isFile()) {
                img = new ImageIcon(archivo.getAbsolutePath()).getImage();
            } else {
                java.net.URL url = DatosCosmos.class.getResource(ruta);
                if (url != null) {
                    img = new ImageIcon(url).getImage();
                }
            }

            if (img != null) {
                Image escalada = img.getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
                return new ImageIcon(escalada);
            }
        } catch (Exception ignored) {}
        return generarPortadaMusical("Music", "#D6249F", "#1A1A1A", ancho, alto);
    }

    public static ImageIcon generarPortadaMusical(String titulo, String hex1, String hex2, int ancho, int alto) {
        BufferedImage bi = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = bi.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        Color col1, col2;
        try { col1 = Color.decode(hex1); } catch (Exception e) { col1 = new Color(0xD6, 0x24, 0x9F); }
        try { col2 = Color.decode(hex2); } catch (Exception e) { col2 = new Color(0x1A, 0x1A, 0x1A); }

        GradientPaint gp = new GradientPaint(0, 0, col1, ancho, alto, col2);
        g2.setPaint(gp);
        g2.fill(new RoundRectangle2D.Double(0, 0, ancho, alto, 14, 14));

        // Disco decorativo semitransparente
        int r = (int) (Math.min(ancho, alto) * 0.65);
        int cx = (ancho - r) / 2;
        int cy = (alto - r) / 2;
        g2.setColor(new Color(0, 0, 0, 95));
        g2.fillOval(cx, cy, r, r);
        g2.setColor(new Color(255, 255, 255, 45));
        g2.drawOval(cx, cy, r, r);

        // Iniciales o nombre corto
        String iniciales = titulo.trim().isEmpty() ? "♪" : titulo.trim().substring(0, Math.min(2, titulo.trim().length())).toUpperCase();
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("SansSerif", Font.BOLD, Math.max(12, ancho / 4)));
        FontMetrics fm = g2.getFontMetrics();
        int tx = (ancho - fm.stringWidth(iniciales)) / 2;
        int ty = (alto - fm.getHeight()) / 2 + fm.getAscent();
        g2.drawString(iniciales, tx, ty);

        g2.dispose();
        return new ImageIcon(bi);
    }

    public static void imprimirLetraCancion(Component parent, Cancion cancion) {
        if (cancion == null) {
            JOptionPane.showMessageDialog(parent, "Selecciona una canción primero.", "Imprimir Letra", JOptionPane.WARNING_MESSAGE);
            return;
        }

        PrinterJob job = PrinterJob.getPrinterJob();
        job.setJobName("Letra - " + cancion.getTitulo());

        job.setPrintable((Graphics g, PageFormat pf, int pageIndex) -> {
            if (pageIndex > 0) return Printable.NO_SUCH_PAGE;

            Graphics2D g2d = (Graphics2D) g;
            g2d.translate(pf.getImageableX(), pf.getImageableY());

            int y = 40;
            ImageIcon icono = cargarIcono(cancion.getRutaImagen(), 60, 60);
            if (icono != null) {
                g2d.drawImage(icono.getImage(), 40, y - 20, 60, 60, null);
            }

            g2d.setFont(new Font("SansSerif", Font.BOLD, 16));
            g2d.drawString("Canción: " + cancion.getTitulo(), 115, y);
            g2d.setFont(new Font("SansSerif", Font.PLAIN, 12));
            g2d.drawString("Artista: " + cancion.getArtista() + " | Álbum: " + cancion.getAlbum(), 115, y + 20);
            g2d.drawString("Duración: " + cancion.getDuracion(), 115, y + 38);

            y += 70;
            g2d.drawLine(40, y, (int) pf.getImageableWidth() - 40, y);
            y += 30;

            g2d.setFont(new Font("SansSerif", Font.PLAIN, 12));
            for (String linea : cancion.getLetra().split("\n")) {
                g2d.drawString(linea, 40, y);
                y += 18;
            }
            return Printable.PAGE_EXISTS;
        });

        if (job.printDialog()) {
            try {
                job.print();
                JOptionPane.showMessageDialog(parent, "Letra enviada a impresión / PDF correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } catch (PrinterException ex) {
                JOptionPane.showMessageDialog(parent, "Error al imprimir: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}