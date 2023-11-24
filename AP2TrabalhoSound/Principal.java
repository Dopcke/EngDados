import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import dao.AutorDAO;
import dao.CategoriaDAO;
import dao.ConnectionFactory;
import dao.MusicaDAO;
import dao.PlaylistDAO;
import modelo.Autor;
import modelo.Categoria;
import modelo.Musica;
import modelo.Playlist;

public class Principal {

    public static void main(String[] args) {
        ConnectionFactory fabricaDeConexao = new ConnectionFactory();
        Connection connection = fabricaDeConexao.recuperaConexao();

        AutorDAO autorDAO = new AutorDAO(connection);

        Autor autor1 = new Autor("Vitor", "Vitinho", "11223344556");
        autorDAO.insert(autor1);

        Autor autorRecuperado = autorDAO.selectByCpf("11223344556");
        System.out.println("Autor Recuperado: " + autorRecuperado);

        autorRecuperado.setNomeArtistico("Novo Nome Artístico");
        autorDAO.update(autorRecuperado);

        CategoriaDAO categoriaDAO = new CategoriaDAO(connection);

        Categoria categoria1 = new Categoria("Nova Categoria");
        categoriaDAO.insert(categoria1);

        Categoria categoriaRecuperada = categoriaDAO.selectByNome("Nova Categoria");
        System.out.println("Categoria Recuperada: " + categoriaRecuperada);

        MusicaDAO musicaDAO = new MusicaDAO(connection);

        Musica musica1 = new Musica("Título 1", "Letra 1", "2023-01-01", categoriaRecuperada, 180, new ArrayList<>());
        musicaDAO.insert(musica1);

        Musica musicaRecuperada = musicaDAO.selectByTitulo("Título 1");
        System.out.println("Música Recuperada: " + musicaRecuperada);

        // Adicione outros trechos conforme necessário

        PlaylistDAO playlistDAO = new PlaylistDAO(connection);

        Playlist playlist1 = new Playlist("2023-01-10", "Playlist 1", categoriaRecuperada, new ArrayList<>());
        playlistDAO.insert(playlist1);

        Playlist playlistRecuperada = playlistDAO.selectByTitulo("Playlist 1");
        System.out.println("Playlist Recuperada: " + playlistRecuperada);

        // Adicione outros trechos conforme necessário
    }
}
