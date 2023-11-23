package modelo;

public class Autor {

    private int idAutor;
    private String nomeOriginal;
    private String nomeArtistico;
    private String cpf;

    public Autor(int idAutor, String cpf, String nomeOriginal, String nomeArtistico) {
        this.idAutor = idAutor;
        this.cpf = cpf;
        this.nomeOriginal = nomeOriginal;
        this.nomeArtistico = nomeArtistico;
    }

    public int getIdAutor() {
        return idAutor;
    }

    public void setIdAutor(int idAutor) {
        this.idAutor = idAutor;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNomeOriginal() {
        return nomeOriginal;
    }

    public void setNomeOriginal(String nomeOriginal) {
        this.nomeOriginal = nomeOriginal;
    }

    public String getNomeArtistico() {
        return nomeArtistico;
    }

    public void setNomeArtistico(String nomeArtistico) {
        this.nomeArtistico = nomeArtistico;
    }

    @Override
    public String toString() {
        return "{ 'autor': {'id_autor': '" + this.idAutor + "','nome_original': '" + this.nomeOriginal + "', 'nome_artistico': '" + this.nomeArtistico + "', 'cpf': '" + this.cpf + "'}}";
    }
}
