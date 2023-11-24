package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import modelo.Categoria;
import modelo.Musica;
import modelo.Playlist;


public class PlaylistDAO {

    private Connection connection;

    public PlaylistDAO(Connection connection) {
        this.connection = connection;
    }


    public void insert(Playlist playlist) {
        try {
            String sqlCategoria = "SELECT id_categoria FROM categoria WHERE nome = ?";
            try (PreparedStatement pstmCategoria = connection.prepareStatement(sqlCategoria)) {
                pstmCategoria.setString(1, playlist.getCategoriaPermitida().getNome());
                pstmCategoria.execute();
                try (ResultSet rstCategoria = pstmCategoria.getResultSet()) {
                    if (rstCategoria.next()) {
                        int idCategoria = rstCategoria.getInt("id_categoria");

                        String sqlMusica = "INSERT INTO playlist (titulo, data_criacao, fk_categoria) VALUES (?, ?, ?)";
                        try (PreparedStatement pstm = connection.prepareStatement(sqlMusica, Statement.RETURN_GENERATED_KEYS)) {
                            pstm.setString(1, playlist.getTitulo());
                            pstm.setString(2, playlist.getDataCriacao());
                            pstm.setInt(3, idCategoria); 

                            pstm.execute();


                            try (ResultSet rst = pstm.getGeneratedKeys()) {
                                while (rst.next()) {
                                    
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
    

    public Playlist selectByTitulo(String titulo) {
        try {
            String sql = "SELECT id_playlist, data_criacao, fk_categoria, nome FROM playlist, categoria WHERE titulo = ?";

            try (PreparedStatement pstm = connection.prepareStatement(sql)) {
                pstm.setString(1, titulo);
                pstm.execute();

                try (ResultSet rst = pstm.getResultSet()) {
                    if (rst.next()) {
                        int idPlaylist = rst.getInt("id_playlist");
                        String dataCriacao = rst.getString("data_criacao");
                        int idCategoria = rst.getInt("fk_categoria");
                        String categoriaNome = rst.getString("nome");
                        Categoria categoria = new Categoria(idCategoria, categoriaNome);
                        return new Playlist(idPlaylist, titulo, dataCriacao, categoria, new ArrayList<>());
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    public void update(Playlist playlist) {
        try {
            String sql = "UPDATE playlist SET titulo = ?, data_criacao = ?, fk_categoria = ? WHERE id_playlist = ?";

            try (PreparedStatement pstm = connection.prepareStatement(sql)) {
                pstm.setString(1, playlist.getTitulo());
                pstm.setString(2, playlist.getDataCriacao());
                pstm.setInt(3, playlist.getCategoriaPermitida().getIdCategoria());
                pstm.setInt(4, playlist.getIdPlaylist());

                pstm.execute();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(String titulo) {
        try {
            String sql = "DELETE FROM playlist WHERE titulo = ?";

            try (PreparedStatement pstm = connection.prepareStatement(sql)) {
                pstm.setString(1, titulo);

                pstm.execute();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public ArrayList<Playlist> selectAll() {
        ArrayList<Playlist> musicas = new ArrayList<>();

        try {
            String sql = "SELECT id_playlist, titulo, data_criacao, fk_categoria, nome FROM playlist, categoria WHERE id_categoria = fk_categoria";

            try (PreparedStatement pstm = connection.prepareStatement(sql)) {
                pstm.execute();
                ResultSet rst = pstm.getResultSet();
                while (rst.next()) {
                    int idPlaylist = rst.getInt("id_playlist");
                    String titulo = rst.getString("titulo");
                    String dataCriacao = rst.getString("data_criacao");
                    int idCategoria = rst.getInt("fk_categoria");
                    String categoriaNome = rst.getString("nome");
                    Categoria categoria = new Categoria(idCategoria, categoriaNome);
                    Playlist musica = new Playlist(idPlaylist, titulo, dataCriacao, categoria, new ArrayList<>());
                    musicas.add(musica);
                }
            }
            return musicas;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}