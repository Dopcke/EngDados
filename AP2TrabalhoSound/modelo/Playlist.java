package modelo;

import java.util.ArrayList;

public class Playlist {
    private String dataCriacao;
    private String titulo;
    private Categoria categoriaPermitida;
    private ArrayList<Musica> musicas;

    public Playlist(String dataCriacao, String titulo, Categoria categoriaPermitida, ArrayList<Musica> musicas) {
        this.dataCriacao = dataCriacao;
        this.titulo = titulo;
        this.categoriaPermitida = categoriaPermitida;
        this.musicas = musicas;
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
}
