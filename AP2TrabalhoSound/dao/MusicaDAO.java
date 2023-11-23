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
            // Recupera o ID da categoria a partir do nome
            String sqlCategoria = "SELECT id_categoria FROM categoria WHERE nome = ?";
            try (PreparedStatement pstmCategoria = connection.prepareStatement(sqlCategoria)) {
                pstmCategoria.setString(1, musica.getCategoria().getNome());
                pstmCategoria.execute();
                try (ResultSet rstCategoria = pstmCategoria.getResultSet()) {
                    if (rstCategoria.next()) {
                        int idCategoria = rstCategoria.getInt("id_categoria");

                        // Agora, com o ID da categoria, é possível inserir a música
                        String sqlMusica = "INSERT INTO musica (titulo, letra, data_lancamento, fk_categoria, duracao) VALUES (?, ?, ?, ?, ?)";
                        try (PreparedStatement pstm = connection.prepareStatement(sqlMusica, Statement.RETURN_GENERATED_KEYS)) {
                            pstm.setString(1, musica.getTitulo());
                            pstm.setString(2, musica.getLetra());
                            pstm.setString(3, musica.getDataLancamento());
                            pstm.setInt(4, idCategoria); // Utiliza o ID da categoria
                            pstm.setInt(5, musica.getDuracao());

                            pstm.execute();

                            try (ResultSet rst = pstm.getGeneratedKeys()) {
                                while (rst.next()) {
                                    // Pode ser omitido se não estiver usando auto-incremento
                                }
                            }
                        }
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public Musica selectByTitulo(String titulo) {
        try {
            String sql = "SELECT id_musica, letra, data_lancamento, fk_categoria, duracao FROM musica WHERE titulo = ?";

            try (PreparedStatement pstm = connection.prepareStatement(sql)) {
                pstm.setString(1, titulo);
                pstm.execute();

                try (ResultSet rst = pstm.getResultSet()) {
                    if (rst.next()) {
                        String letra = rst.getString("letra");
                        String dataLancamento = rst.getString("data_lancamento");
                        String categoriaNome = rst.getString("fk_categoria");
                        int duracao = rst.getInt("duracao");
                        int idMusica = rst.getInt("id_musica");
                        Categoria categoria = new Categoria(categoriaNome);
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
            String sql = "UPDATE musica SET letra = ?, data_lancamento = ?, fk_categoria = ?, duracao = ? WHERE titulo = ?";

            try (PreparedStatement pstm = connection.prepareStatement(sql)) {
                pstm.setString(1, musica.getLetra());
                pstm.setString(2, musica.getDataLancamento());
                pstm.setString(3, musica.getCategoria().getNome());
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
            String sql = "DELETE FROM musica WHERE titulo = ?";

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
            String sql = "SELECT id_musica, titulo, letra, data_lancamento, fk_categoria, duracao FROM musica";

            try (PreparedStatement pstm = connection.prepareStatement(sql)) {
                pstm.execute();
                ResultSet rst = pstm.getResultSet();
                while (rst.next()) {
                    String titulo = rst.getString("titulo");
                    String letra = rst.getString("letra");
                    String dataLancamento = rst.getString("data_lancamento");
                    String categoriaNome = rst.getString("fk_categoria");
                    int duracao = rst.getInt("duracao");
                    int idMusica = rst.getInt("id_musica");
                    Categoria categoria = new Categoria(categoriaNome);
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