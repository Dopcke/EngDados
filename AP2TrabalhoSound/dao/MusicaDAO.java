package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import modelo.Categoria;
import modelo.Musica;

public class MusicaDAO {

    private Connection connection;

    public MusicaDAO(Connection connection) {
        this.connection = connection;
    }

    public void insert(Musica musica) {
        try {
            String sql = "INSERT INTO Musica (titulo, letra, data_lancamento, fk_categoria, duracao) VALUES (?, ?, ?, ?, ?)";

            try (PreparedStatement pstm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                pstm.setString(1, musica.getTitulo());
                pstm.setString(2, musica.getLetra());
                pstm.setString(3, musica.getDataLancamento());
                pstm.setInt(4, musica.getCategoria().getIdCategoria());
                pstm.setInt(5, musica.getDuracao());

                pstm.execute();

                try (ResultSet rst = pstm.getGeneratedKeys()) {
                    while (rst.next()) {
                        musica.setIdMusica(rst.getInt(1));
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Musica selectByTitulo(String titulo) {
        try {
            String sql = "SELECT m.letra, m.data_lancamento, m.duracao, c.id_categoria, c.nome " +
                         "FROM Musica m " +
                         "JOIN Categoria c ON m.fk_categoria = c.id_categoria " +
                         "WHERE m.titulo = ?";

            try (PreparedStatement pstm = connection.prepareStatement(sql)) {
                pstm.setString(1, titulo);
                pstm.execute();

                try (ResultSet rst = pstm.getResultSet()) {
                    if (rst.next()) {
                        int idMusica = rst.getInt("idMusica");
                        String letra = rst.getString("letra");
                        String dataLancamento = rst.getString("data_lancamento");
                        int duracao = rst.getInt("duracao");
                        int categoriaId = rst.getInt("id_categoria");
                        String categoriaNome = rst.getString("nome");
                        Categoria categoria = new Categoria(categoriaId, categoriaNome);
                        return new Musica(idMusica, titulo, letra, dataLancamento, categoria, duracao, new ArrayList<>());
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    public void update(Musica musica) {
        try {
            String sql = "UPDATE Musica SET letra = ?, data_lancamento = ?, fk_categoria = ?, duracao = ? WHERE titulo = ?";

            try (PreparedStatement pstm = connection.prepareStatement(sql)) {
                pstm.setString(1, musica.getLetra());
                pstm.setString(2, musica.getDataLancamento());
                pstm.setInt(3, musica.getCategoria().getIdCategoria());
                pstm.setInt(4, musica.getDuracao());
                pstm.setString(5, musica.getTitulo());

                pstm.execute();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(String titulo) {
        try {
            String sql = "DELETE FROM Musica WHERE titulo = ?";

            try (PreparedStatement pstm = connection.prepareStatement(sql)) {
                pstm.setString(1, titulo);

                pstm.execute();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Musica> selectAll() {
        ArrayList<Musica> musicas = new ArrayList<>();

        try {
            String sql = "SELECT m.titulo, m.letra, m.data_lancamento, m.duracao, c.id_categoria, c.nome " +
                         "FROM Musica m " +
                         "JOIN Categoria c ON m.fk_categoria = c.id_categoria";

            try (PreparedStatement pstm = connection.prepareStatement(sql)) {
                pstm.execute();
                ResultSet rst = pstm.getResultSet();
                while (rst.next()) {
                    int idMusica = rst.getInt("id_musica");
                    String titulo = rst.getString("titulo");
                    String letra = rst.getString("letra");
                    String dataLancamento = rst.getString("data_lancamento");
                    int duracao = rst.getInt("duracao");
                    int categoriaId = rst.getInt("id_categoria");
                    String categoriaNome = rst.getString("nome");
                    Categoria categoria = new Categoria(categoriaId, categoriaNome);
                    Musica musica = new Musica(idMusica, titulo, letra, dataLancamento, categoria, duracao, new ArrayList<>());
                    musicas.add(musica);
                }
            }
            return musicas;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
