package modelo;

import java.util.ArrayList;

public class Playlist {
    private int idPlaylist;
    private String dataCriacao;
    private String titulo;
    private Categoria categoriaPermitida;
    private ArrayList<Musica> musicas;

    public Playlist(int idPlaylist, String dataCriacao, String titulo, Categoria categoriaPermitida, ArrayList<Musica> musicas) {
        this.idPlaylist = idPlaylist;
        this.dataCriacao = dataCriacao;
        this.titulo = titulo;
        this.categoriaPermitida = categoriaPermitida;
        this.musicas = musicas;
    }

    public Playlist(String dataCriacao, String titulo, Categoria categoriaPermitida, ArrayList<Musica> musicas) {
        this.dataCriacao = dataCriacao;
        this.titulo = titulo;
        this.categoriaPermitida = categoriaPermitida;
        this.musicas = musicas;
    }


    public int idPlaylist(){
        return idPlaylist;
    }

    public String getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(String dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Categoria getCategoriaPermitida() {
        return categoriaPermitida;
    }

    public void setCategoriaPermitida(Categoria categoriaPermitida) {
        this.categoriaPermitida = categoriaPermitida;
    }

    public ArrayList<Musica> getMusicas() {
        return musicas;
    }

    public void setMusicas(ArrayList<Musica> musicas) {
        this.musicas = musicas;
    }

    

    @Override
    public String toString() {
        return "Playlist{" +
                "dataCriacao='" + dataCriacao + '\'' +
                ", titulo='" + titulo + '\'' +
                ", categoriaPermitida=" + categoriaPermitida +
                ", musicas=" + musicas +
                '}';
    }
    public static void ConfereCateforia(Musica musica, ArrayList<Musica> musicas, Playlist playlist){
        if (musica.getCategoria() == playlist.getCategoriaPermitida()){
            musicas.add(musica);
        } else{
            System.err.println("Não é do mesmo gênero");
        }
    }
}
