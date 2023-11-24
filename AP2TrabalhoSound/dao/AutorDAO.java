package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import modelo.Autor;
import modelo.Musica;

public class AutorDAO {

    private Connection connection;

    public AutorDAO(Connection connection) {
        this.connection = connection;
    }

    public void insert(Autor autor, Musica musica) {
        try {
            String sql = "INSERT INTO autor (cpf, nome_original, nome_artistico) VALUES (?, ?, ?)";

            try (PreparedStatement pstm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                pstm.setString(1, autor.getCpf());
                pstm.setString(2, autor.getNomeOriginal());
                pstm.setString(3, autor.getNomeArtistico());

                pstm.execute();

                try (ResultSet rst = pstm.getGeneratedKeys()) {
                    while (rst.next()) {
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Autor selectByCpf(String cpf) {
        try {
            String sql = "SELECT * FROM autor WHERE cpf = ?";

            try (PreparedStatement pstm = connection.prepareStatement(sql)) {
                pstm.setString(1, cpf);
                pstm.execute();

                try (ResultSet rst = pstm.getResultSet()) {
                    if (rst.next()) {
                        int idAutor  = rst.getInt("id_autor");
                        String nomeOriginal = rst.getString("nome_original");
                        String nomeArtistico = rst.getString("nome_artistico");
                        return new Autor(idAutor, nomeOriginal, nomeArtistico, cpf);
                    }
                }
            }
            
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    public void update(Autor autor) {
        try {
            String sql = "UPDATE autor SET cpf = ?, nome_original = ?, nome_artistico = ? WHERE id_autor = ?";

            try (PreparedStatement pstm = connection.prepareStatement(sql)) {
                pstm.setString(1, autor.getCpf());
                pstm.setString(2, autor.getNomeOriginal());
                pstm.setString(3, autor.getNomeArtistico());
                pstm.setInt(4, autor.getIdAutor());

                pstm.execute();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    } 


    public void delete(String cpf) {
        try {
            String sql = "DELETE FROM autor WHERE cpf = ?";

            try (PreparedStatement pstm = connection.prepareStatement(sql)) {
                pstm.setString(1, cpf);

                pstm.execute();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Autor> selectAll() {
    ArrayList<Autor> autores = new ArrayList<>();

    try {
        String sql = "SELECT id_autor, cpf, nome_original, nome_artistico FROM autor";

        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.execute();
            ResultSet rst = pstm.getResultSet();
            while (rst.next()) {
                int idAutor = rst.getInt("id_autor");
                String nomeOriginal = rst.getString("nome_original");
                String nomeArtistico = rst.getString("nome_artistico");               
                String cpf = rst.getString("cpf");
                Autor autor = new Autor(idAutor, nomeOriginal, nomeArtistico, cpf);
                autores.add(autor);
            }
        }
        return autores;
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
    }
}
