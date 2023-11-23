package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import modelo.Autor;

public class AutorDAO {

    private Connection connection;

    public AutorDAO(Connection connection) {
        this.connection = connection;
    }

    public void insert(Autor autor) {
        try {
            String sql = "INSERT INTO Autor (cpf, nome_original, nome_artistico) VALUES (?, ?, ?)";

            try (PreparedStatement pstm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                pstm.setString(1, autor.getCpf());
                pstm.setString(2, autor.getNomeOriginal());
                pstm.setString(3, autor.getNomeArtistico());

                pstm.executeUpdate();

                try (ResultSet rst = pstm.getGeneratedKeys()) {
                    while (rst.next()) {
                        autor.setIdAutor(rst.getInt(1)); 
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Autor selectByCpf(String cpf) {
        try {
            String sql = "SELECT id_autor, nome_original, nome_artistico FROM Autor WHERE cpf = ?";

            try (PreparedStatement pstm = connection.prepareStatement(sql)) {
                pstm.setString(1, cpf);
                pstm.execute();

                try (ResultSet rst = pstm.getResultSet()) {
                    if (rst.next()) {
                        int id = rst.getInt("id_autor");
                        String nomeOriginal = rst.getString("nome_original");
                        String nomeArtistico = rst.getString("nome_artistico");
                        return new Autor(id, cpf, nomeOriginal, nomeArtistico);
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
            String sql = "UPDATE Autor SET nome_original = ?, nome_artistico = ? WHERE cpf = ?";

            try (PreparedStatement pstm = connection.prepareStatement(sql)) {
                pstm.setString(1, autor.getNomeOriginal());
                pstm.setString(2, autor.getNomeArtistico());
                pstm.setString(3, autor.getCpf());

                pstm.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(String cpf) {
        try {
            String sql = "DELETE FROM Autor WHERE cpf = ?";

            try (PreparedStatement pstm = connection.prepareStatement(sql)) {
                pstm.setString(1, cpf);

                pstm.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Autor> selectAll() {
        ArrayList<Autor> autores = new ArrayList<>();

        try {
            String sql = "SELECT id_autor, cpf, nome_original, nome_artistico FROM Autor";

            try (PreparedStatement pstm = connection.prepareStatement(sql)) {
                pstm.execute();
                ResultSet rst = pstm.getResultSet();
                while (rst.next()) {
                    int id = rst.getInt("id_autor");
                    String cpf = rst.getString("cpf");
                    String nomeOriginal = rst.getString("nome_original");
                    String nomeArtistico = rst.getString("nome_artistico");
                    Autor autor = new Autor(id, cpf, nomeOriginal, nomeArtistico);
                    autores.add(autor);
                }
            }
            return autores;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
