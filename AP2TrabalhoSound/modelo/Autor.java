package modelo;

public class Autor {

    private int idAutor;
    private String nomeOriginal;
    private String nomeArtistico;
    private String cpf;

    public Autor(int idAutor, String nomeOriginal, String nomeArtistico, String cpf) {
        this.idAutor = idAutor;
        this.nomeOriginal = nomeOriginal;
        this.nomeArtistico = nomeArtistico;
        this.cpf = cpf;
    }
    public Autor(String nomeOriginal, String nomeArtistico, String cpf) {
        this.nomeOriginal = nomeOriginal;
        this.nomeArtistico = nomeArtistico;
        this.cpf = cpf;
    }

    public int getIdAutor(){
        return idAutor;
    }

    public String getCpf(){
        return cpf;
    }

    public void setCpf(String cpf){
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
        return "{'pessoa':{'nome_original': " + this.nomeOriginal + ", 'nome_artistico': '" + this.nomeArtistico + "', 'cpf': '" + this.cpf + "'}}";
    }
}