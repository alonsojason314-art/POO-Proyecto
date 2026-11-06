package control;

/** Información de una canción. La clave es única y no se puede modificar. */
public class Cancion {

    private final String clave;
    private String titulo;
    private String genero;
    private String artista;
    private int anio;
    private String album;
    private String duracion;      // formato m:ss  (ej. 3:46)
    private String rutaImagen;    // carátula
    private String rutaPdf;       // letra en PDF

    public Cancion(String clave, String titulo, String genero, String artista, int anio,
                   String album, String duracion, String rutaImagen, String rutaPdf) {
        this.clave = clave;
        this.titulo = titulo;
        this.genero = genero;
        this.artista = artista;
        this.anio = anio;
        this.album = album;
        this.duracion = duracion;
        this.rutaImagen = rutaImagen;
        this.rutaPdf = rutaPdf;
    }

    public String getClave() { return clave; }
    public String getTitulo() { return titulo; }
    public String getGenero() { return genero; }
    public String getArtista() { return artista; }
    public int getAnio() { return anio; }
    public String getAlbum() { return album; }
    public String getDuracion() { return duracion; }
    public String getRutaImagen() { return rutaImagen; }
    public String getRutaPdf() { return rutaPdf; }

    public void setTitulo(String titulo) { this.titulo = titulo; }
    public void setGenero(String genero) { this.genero = genero; }
    public void setArtista(String artista) { this.artista = artista; }
    public void setAnio(int anio) { this.anio = anio; }
    public void setAlbum(String album) { this.album = album; }
    public void setDuracion(String duracion) { this.duracion = duracion; }
    public void setRutaImagen(String rutaImagen) { this.rutaImagen = rutaImagen; }
    public void setRutaPdf(String rutaPdf) { this.rutaPdf = rutaPdf; }

    @Override
    public String toString() { return titulo + " - " + artista; }
}