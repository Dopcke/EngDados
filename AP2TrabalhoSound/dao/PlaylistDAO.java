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
            // Recupera o ID da categoria a partir do nome
            String sqlCategoria = "SELECT id_categoria FROM categoria WHERE nome = ?";
            try (PreparedStatement pstmCategoria = connection.prepareStatement(sqlCategoria)) {
                pstmCategoria.setString(1, playlist.getCategoriaPermitida().getNome());
                pstmCategoria.execute();
                try (ResultSet rstCategoria = pstmCategoria.getResultSet()) {
                    if (rstCategoria.next()) {
                        int idCategoria = rstCategoria.getInt("id_categoria");

                        // Agora, com o ID da categoria, é possível inserir a música
                        String sqlMusica = "INSERT INTO playlist (titulo, data_criacao, fk_categoria) VALUES (?, ?, ?)";
                        try (PreparedStatement pstm = connection.prepareStatement(sqlMusica, Statement.RETURN_GENERATED_KEYS)) {
                            pstm.setString(1, playlist.getTitulo());
                            pstm.setString(2, playlist.getDataCriacao());
                            pstm.setInt(3, idCategoria); // Utiliza o ID da categoria

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


    // Método auxiliar para associar músicas à playlist
    private void associateMusicsToPlaylist(Playlist playlist) {
        try {
            String sql = "INSERT INTO musica_playlist (fk_musica, fk_playlist) VALUES ((SELECT id_musica FROM musica WHERE titulo = ?), ?)";
    
            try (PreparedStatement pstm = connection.prepareStatement(sql)) {
                for (Musica musica : playlist.getMusicas()) {
                    pstm.setString(1, musica.getTitulo());
                    pstm.setInt(2, playlist.getIdPlaylist());
                    pstm.addBatch();
                }
                pstm.executeBatch();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    

    // Método auxiliar para recuperar as músicas associadas à playlist
    private ArrayList<Musica> getMusicsFromPlaylist(String tituloPlaylist) {
        ArrayList<Musica> musicas = new ArrayList<>();

        try {
            String sql = "SELECT musica_titulo FROM musica_playlist WHERE playlist.titulo = ?";

            try (PreparedStatement pstm = connection.prepareStatement(sql)) {
                pstm.setString(1, tituloPlaylist);
                pstm.execute();

                try (ResultSet rst = pstm.getResultSet()) {
                    while (rst.next()) {
                        String tituloMusica = rst.getString("musica_titulo");
                        // Recupera a informação completa da música a partir do título
                        MusicaDAO musicaDAO = new MusicaDAO(connection);
                        Musica musica = musicaDAO.selectByTitulo(tituloMusica);
                        musicas.add(musica);
                    }
                }
            }
            return musicas;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Método auxiliar para desassociar músicas de uma playlist
    private void dissociateMusicsFromPlaylist(Playlist playlist) {
        try {
            String sql = "DELETE FROM musica_playlist WHERE playlist.titulo = ?";

            try (PreparedStatement pstm = connection.prepareStatement(sql)) {
                pstm.setString(1, playlist.getTitulo());
                pstm.execute();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}