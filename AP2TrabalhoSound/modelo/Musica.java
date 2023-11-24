package modelo;

import java.util.ArrayList;

public class Musica {
    private int idMusica;
    private String titulo;
    private String letra;
    private String dataLancamento;
    private Categoria categoria;
    private int duracao;
    private ArrayList<Autor> autores;

    public Musica(int idMusica, String titulo, String letra, String dataLancamento, Categoria categoria, int duracao, ArrayList<Autor> autores) {
        this.idMusica = idMusica;
        this.titulo = titulo;
        this.letra = letra;
        this.dataLancamento = dataLancamento;
        this.categoria = categoria;
        this.duracao = duracao;
        this.autores = autores;
    }

    public Musica(String titulo, String letra, String dataLancamento, Categoria categoria, int duracao, ArrayList<Autor> autores) {
        this.titulo = titulo;
        this.letra = letra;
        this.dataLancamento = dataLancamento;
        this.categoria = categoria;
        this.duracao = duracao;
        this.autores = autores;
    }

    public int getIdMusica(){
        return idMusica;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getLetra() {
        return letra;
    }

    public void setLetra(String letra) {
        this.letra = letra;
    }

    public String getDataLancamento() {
        return dataLancamento;
    }

    public void setDataLancamento(String dataLancamento) {
        this.dataLancamento = dataLancamento;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public int getDuracao() {
        return duracao;
    }

    public void setDuracao(int duracao) {
        this.duracao = duracao;
    }

    public ArrayList<Autor> getAutores() {
        return autores;
    }

    public void setAutores(ArrayList<Autor> autores) {
        this.autores = autores;
    }

    @Override
    public String toString() {
        return "Musica{" +
                "titulo='" + titulo + '\'' +
                ", letra='" + letra + '\'' +
                ", dataLancamento='" + dataLancamento + '\'' +
                ", categoria=" + categoria +
                ", duracao=" + duracao +
                ", autores=" + autores +
                '}';
    }
}
