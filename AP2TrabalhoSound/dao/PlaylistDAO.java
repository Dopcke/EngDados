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


    

    public Playlist selectByTitulo(String titulo) {
        try {
            String sql = "SELECT id_categoria, data_criacao, categoria FROM playlist WHERE titulo = ?";

            try (PreparedStatement pstm = connection.prepareStatement(sql)) {
                pstm.setString(1, titulo);
                pstm.execute();

                try (ResultSet rst = pstm.getResultSet()) {
                    if (rst.next()) {
                        String dataCriacao = rst.getString("data_criacao");
                        String categoriaNome = rst.getString("categoria");
                        int idCategoria = rst.getInt("id_categoria");
                        Categoria categoria = new Categoria(categoriaNome);

                        // Recupera as músicas associadas à playlist
                        ArrayList<Musica> musicas = getMusicsFromPlaylist(titulo);

                        return new Playlist(idCategoria, dataCriacao, titulo, categoria, musicas);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    public void insert(Playlist playlist) {
        try {
            String sql = "INSERT INTO playlist (data_criacao, titulo, fk_categoria) VALUES (?, ?, ?)";
    
            try (PreparedStatement pstm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                pstm.setString(1, playlist.getDataCriacao());
                pstm.setString(2, playlist.getTitulo());
                pstm.setInt(3, playlist.getCategoriaPermitida().getIdCategoria()); // Supondo que Categoria tenha um método getId() que retorna o ID
    
                pstm.execute();
    
                try (ResultSet rst = pstm.getGeneratedKeys()) {
                    while (rst.next()) {
                        // Pode ser omitido se não estiver usando auto-incremento
                    }
                }
    
                // Associa as músicas à playlist
                associateMusicsToPlaylist(playlist);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(String titulo) {
        try {
            // Desassocia as músicas antes de excluir a playlist
            dissociateMusicsFromPlaylist(selectByTitulo(titulo));

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
        ArrayList<Playlist> playlists = new ArrayList<>();

        try {
            String sql = "SELECT id_playlist, data_criacao, titulo, categoria FROM playlist";

            try (PreparedStatement pstm = connection.prepareStatement(sql)) {
                pstm.execute();
                ResultSet rst = pstm.getResultSet();
                while (rst.next()) {
                    String dataCriacao = rst.getString("data_criacao");
                    String titulo = rst.getString("titulo");
                    String categoriaNome = rst.getString("categoria");
                    Categoria categoria = new Categoria(categoriaNome);
                    int idPlaylist= rst.getInt("id_playlist");

                    // Recupera as músicas associadas à playlist
                    ArrayList<Musica> musicas = getMusicsFromPlaylist(titulo);

                    Playlist playlist = new Playlist(idPlaylist, dataCriacao, titulo, categoria, musicas);
                    playlists.add(playlist);
                }
            }
            return playlists;
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
                    pstm.setInt(2, playlist.idPlaylist()); 
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