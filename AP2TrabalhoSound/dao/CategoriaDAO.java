package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import modelo.Categoria;

public class CategoriaDAO {

    private Connection connection;

    public CategoriaDAO(Connection connection) {
        this.connection = connection;
    }

    public void insert(Categoria categoria) {
        try {
            String sql = "INSERT INTO Categoria (nome) VALUES (?)";

            try (PreparedStatement pstm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                pstm.setString(1, categoria.getNome());

                pstm.executeUpdate();

                try (ResultSet rst = pstm.getGeneratedKeys()) {
                    while (rst.next()) {
                        categoria.setIdCategoria(rst.getInt(1)); 
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Categoria selectByNome(String nome) {
        try {
            String sql = "SELECT id_categoria, nome FROM Categoria WHERE nome = ?";

            try (PreparedStatement pstm = connection.prepareStatement(sql)) {
                pstm.setString(1, nome);
                pstm.execute();

                try (ResultSet rst = pstm.getResultSet()) {
                    if (rst.next()) {
                        int id = rst.getInt("id_categoria");
                        return new Categoria(id, nome);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    public void update(Categoria categoria) {
        try {
            String sql = "UPDATE Categoria SET nome = ? WHERE id_categoria = ?";

            try (PreparedStatement pstm = connection.prepareStatement(sql)) {
                pstm.setString(1, categoria.getNome());
                pstm.setInt(2, categoria.getIdCategoria());

                pstm.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(int id) {
        try {
            String sql = "DELETE FROM Categoria WHERE id_categoria = ?";

            try (PreparedStatement pstm = connection.prepareStatement(sql)) {
                pstm.setInt(1, id);

                pstm.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Categoria> selectAll() {
        ArrayList<Categoria> categorias = new ArrayList<>();

        try {
            String sql = "SELECT id_categoria, nome FROM Categoria";

            try (PreparedStatement pstm = connection.prepareStatement(sql)) {
                pstm.execute();
                ResultSet rst = pstm.getResultSet();
                while (rst.next()) {
                    int id = rst.getInt("id_categoria");
                    String nome = rst.getString("nome");
                    Categoria categoria = new Categoria(id, nome);
                    categorias.add(categoria);
                }
            }
            return categorias;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
