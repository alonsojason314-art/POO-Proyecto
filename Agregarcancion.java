package adm;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;

public final class Agregarcancion extends JFrame {

    JLabel lTitulo, lRecents, lCancion, lArtista, lAlbum, lGenero, lRutaImagen;
    JButton bAvatar, bPlus, bCerrar, bLista, bSubirImagen, nuevo, agregar;
    JTextField tCancion, tArtista, tAlbum, tGenero;
    JTextArea tInformacion;
    JScrollPane scrollInformacion;
    String rutaImagenSeleccionada = "";

    
    Color colorFondo = new Color(20, 20, 20);
    Color colorRosa = new Color(230, 0, 115);
    Color colorGrisPanel = new Color(30, 30, 30);
    Color colorGrisCampo = new Color(40, 40, 40);
    Color colorBorde = new Color(70, 70, 70);

    public Agregarcancion() {
        setTitle("Agregar canción");
        setSize(650, 470);
        setLayout(null);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        getContentPane().setBackground(colorFondo);
        
        componentes();
        setVisible(true);
    }

    public void componentes() {
        
        
        bAvatar = new JButton("👤");
        bAvatar.setBounds(20, 15, 35, 35);
        bAvatar.setBackground(colorGrisCampo);
        bAvatar.setForeground(colorRosa);
        bAvatar.setFocusPainted(false);
        bAvatar.setBorder(BorderFactory.createLineBorder(colorBorde));

        lTitulo = new JLabel("Agregar canción");
        lTitulo.setBounds(70, 15, 200, 35);
        lTitulo.setForeground(colorRosa);
        lTitulo.setFont(new Font("Arial", Font.BOLD, 18));

        bPlus = new JButton("+");
        bPlus.setBounds(575, 15, 40, 35);
        bPlus.setBackground(colorRosa);
        bPlus.setForeground(Color.WHITE);
        bPlus.setFocusPainted(false);
        bPlus.setBorder(BorderFactory.createLineBorder(colorRosa));

       
        bCerrar = new JButton("✕");
        bCerrar.setBounds(20, 65, 35, 35);
        bCerrar.setBackground(colorGrisCampo);
        bCerrar.setForeground(colorRosa);
        bCerrar.setFocusPainted(false);
        bCerrar.setBorder(BorderFactory.createLineBorder(colorBorde));

        
        lRecents = new JLabel("↕ Recents");
        lRecents.setBounds(20, 115, 100, 25);
        lRecents.setForeground(colorRosa);
        lRecents.setFont(new Font("Arial", Font.PLAIN, 13));

        bLista = new JButton("☰");
        bLista.setBounds(575, 115, 40, 25);
        bLista.setBackground(colorFondo);
        bLista.setForeground(colorRosa);
        bLista.setFocusPainted(false);
        bLista.setBorder(null);

     
        lCancion = new JLabel("Canción:");
        lCancion.setBounds(20, 150, 75, 25);
        lCancion.setForeground(colorRosa);

        tCancion = new JTextField();
        tCancion.setBounds(100, 150, 160, 25);
        tCancion.setBackground(colorGrisCampo);
        tCancion.setForeground(Color.WHITE);
        tCancion.setCaretColor(Color.WHITE);
        tCancion.setBorder(BorderFactory.createLineBorder(colorRosa)); 

        lArtista = new JLabel("Artista:");
        lArtista.setBounds(20, 185, 75, 25);
        lArtista.setForeground(colorRosa);

        tArtista = new JTextField();
        tArtista.setBounds(100, 185, 160, 25);
        tArtista.setBackground(colorGrisCampo);
        tArtista.setForeground(Color.WHITE);
        tArtista.setCaretColor(Color.WHITE);
        tArtista.setBorder(BorderFactory.createLineBorder(colorBorde));

        lAlbum = new JLabel("Álbum:");
        lAlbum.setBounds(20, 220, 75, 25);
        lAlbum.setForeground(colorRosa);

        tAlbum = new JTextField();
        tAlbum.setBounds(100, 220, 160, 25);
        tAlbum.setBackground(colorGrisCampo);
        tAlbum.setForeground(Color.WHITE);
        tAlbum.setCaretColor(Color.WHITE);
        tAlbum.setBorder(BorderFactory.createLineBorder(colorBorde));

        lGenero = new JLabel("Género:");
        lGenero.setBounds(20, 255, 75, 25);
        lGenero.setForeground(colorRosa);

        tGenero = new JTextField();
        tGenero.setBounds(100, 255, 160, 25);
        tGenero.setBackground(colorGrisCampo);
        tGenero.setForeground(Color.WHITE);
        tGenero.setCaretColor(Color.WHITE);
        tGenero.setBorder(BorderFactory.createLineBorder(colorBorde));

        
        bSubirImagen = new JButton("Agregar imagen");
        bSubirImagen.setBounds(20, 295, 240, 30);
        bSubirImagen.setBackground(colorGrisCampo);
        bSubirImagen.setForeground(colorRosa);
        bSubirImagen.setFocusPainted(false);
        bSubirImagen.setBorder(BorderFactory.createLineBorder(colorRosa));

        lRutaImagen = new JLabel("Ninguna imagen seleccionada");
        lRutaImagen.setBounds(20, 330, 240, 20);
        lRutaImagen.setForeground(Color.LIGHT_GRAY);
        lRutaImagen.setFont(new Font("Arial", Font.PLAIN, 11));

        
        tInformacion = new JTextArea();
        tInformacion.setBackground(colorGrisPanel);
        tInformacion.setForeground(Color.WHITE);
        tInformacion.setCaretColor(Color.WHITE);
        tInformacion.setEditable(false);

        scrollInformacion = new JScrollPane(tInformacion);
        scrollInformacion.setBounds(280, 150, 335, 200);
        scrollInformacion.setBorder(BorderFactory.createLineBorder(colorBorde));

        
        nuevo = new JButton("Nuevo");
        nuevo.setBounds(405, 370, 100, 30);
        nuevo.setBackground(colorGrisCampo);
        nuevo.setForeground(Color.WHITE);
        nuevo.setFocusPainted(false);
        nuevo.setBorder(BorderFactory.createLineBorder(colorBorde));

        agregar = new JButton("Agregar");
        agregar.setBounds(515, 370, 100, 30);
        agregar.setBackground(colorRosa);
        agregar.setForeground(Color.WHITE);
        agregar.setFocusPainted(false);
        agregar.setBorder(BorderFactory.createLineBorder(colorRosa));

        
        add(bAvatar);
        add(lTitulo);
        add(bPlus);
        add(bCerrar);
        add(lRecents);
        add(bLista);
        
        add(lCancion);
        add(tCancion);
        add(lArtista);
        add(tArtista);
        add(lAlbum);
        add(tAlbum);
        add(lGenero);
        add(tGenero);
        
        add(bSubirImagen);
        add(lRutaImagen);
        
        add(scrollInformacion);
        
        add(nuevo);
        add(agregar);

        
        bSubirImagen.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            FileNameExtensionFilter filtro = new FileNameExtensionFilter("Archivos de Imagen (*.jpg, *.png, *.jpeg)", "jpg", "png", "jpeg");
            fileChooser.setFileFilter(filtro);
            
            int resultado = fileChooser.showOpenDialog(this);
            if (resultado == JFileChooser.APPROVE_OPTION) {
                File archivo = fileChooser.getSelectedFile();
                rutaImagenSeleccionada = archivo.getName();
                lRutaImagen.setText("Img: " + rutaImagenSeleccionada);
            }
        });

        
        agregar.addActionListener(e -> {
            String song = tCancion.getText();
            String artist = tArtista.getText();
            String album = tAlbum.getText();
            String genre = tGenero.getText();
            String imgInfo = rutaImagenSeleccionada.isEmpty() ? "Sin imagen" : rutaImagenSeleccionada;

            if (!song.isEmpty() && !artist.isEmpty()) {
                tInformacion.append("• " + song + " | " + artist + " | " + album + " | " + genre + " | [" + imgInfo + "]\n");
                tCancion.setText("");
                tArtista.setText("");
                tAlbum.setText("");
                tGenero.setText("");
                lRutaImagen.setText("Ninguna imagen seleccionada");
                rutaImagenSeleccionada = "";
            } else {
                JOptionPane.showMessageDialog(this, "Por favor completa al menos la canción y el artista.");
            }
        });

        
        nuevo.addActionListener(e -> {
            tCancion.setText("");
            tArtista.setText("");
            tAlbum.setText("");
            tGenero.setText("");
            lRutaImagen.setText("Ninguna imagen seleccionada");
            rutaImagenSeleccionada = "";
            tCancion.requestFocus();
        });
        
        bCerrar.addActionListener(e -> System.exit(0));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Agregarcancion());
    }
}