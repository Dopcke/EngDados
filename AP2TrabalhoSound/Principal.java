import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import dao.ConnectionFactory;
import dao.AutorDAO;
import dao.CategoriaDAO;
import dao.MusicaDAO;
import dao.PlaylistDAO;
import modelo.Autor;
import modelo.Categoria;
import modelo.Musica;
import modelo.Playlist;

public class Principal {

    public static void main(String[] args) {
        // Criação de objetos de exemplo
        Autor autor1 = new Autor("Davi", "David Guetta", "12345678901");
        Autor autor2 = new Autor("João", "João Paulo", "23456789012");
        Autor autor3 = new Autor("Daniel", "Studart", "90876543210");

        Categoria categoria1 = new Categoria("MPB");
        Categoria categoria2 = new Categoria("Forró");
        Categoria categoria3 = new Categoria("Rock");
        Categoria categoria4 = new Categoria("Pagode");
        Categoria categoria5 = new Categoria("Samba");
        Categoria categoria6 = new Categoria("POP");

        ArrayList<Autor> autores1 = new ArrayList<Autor>();
        ArrayList<Autor> autores2 = new ArrayList<Autor>();
        ArrayList<Autor> autores3 = new ArrayList<Autor>();

        autores1.add(autor2);
        autores1.add(autor1);
        autores2.add(autor1);
        autores3.add(autor3);

        Musica musica1 = new Musica("Gang Gang", "abcde", "2003-07-22", categoria1, 230, autores1);
        Musica musica2 = new Musica("Bang Bang da Anitta", "abcde", "2003-07-22", categoria2, 230, autores1);
        Musica musica3 = new Musica("Braga Samba", "abcde", "2003-07-22", categoria3, 210, autores2);
        Musica musica4 = new Musica("Vai malandra", "abcde", "2003-07-22", categoria4, 190, autores3);
        Musica musica5 = new Musica("Pé de pano", "abcde", "2003-07-22", categoria1, 522, autores2);
        Musica musica6 = new Musica("Sítio do Pica-Pau", "abcde", "2003-07-22", categoria4, 200, autores3);
        Musica musica7 = new Musica("Naquela Mesa", "abcde", "2003-07-22", categoria5, 90, autores1);
        Musica musica8 = new Musica("Não era pra ser", "abcde", "2003-07-22", categoria3, 100, autores3);

        ArrayList<Musica> Conjunto1 = new ArrayList<Musica>();
        ArrayList<Musica> Conjunto2 = new ArrayList<Musica>();
        ArrayList<Musica> Conjunto3 = new ArrayList<Musica>();


        Playlist playlist1 = new Playlist();
        Playlist.ConfereCategoria(musica1, Conjunto1, playlist1);
        Playlist.ConfereCategoria(musica7, Conjunto1, playlist1);
        Playlist.ConfereCategoria(musica3, Conjunto1, playlist1);
        playlist1 = new Playlist("2003-07-22", "Meus MPBS", categoria1, Conjunto1);
        
        Playlist playlist2 = new Playlist();
        Playlist.ConfereCategoria(musica2, Conjunto2, playlist2);
        Playlist.ConfereCategoria(musica6, Conjunto2, playlist2);
        Playlist.ConfereCategoria(musica7, Conjunto2, playlist2);
        playlist2 = new Playlist("2019-09-20", "Meus Forros", categoria2, Conjunto2);

        Playlist playlist3 = new Playlist();
        Playlist.ConfereCategoria(musica5, Conjunto3, playlist3);
        Playlist.ConfereCategoria(musica8, Conjunto3, playlist3);
        playlist3 = new Playlist("2003-10-17", "Meus Rocks", categoria3, Conjunto3);


        // Inicialização da conexão com o banco de dados
        ConnectionFactory fabricaDeConexao = new ConnectionFactory();
        try (Connection connection = fabricaDeConexao.recuperaConexao()) {
            AutorDAO autorDAO = new AutorDAO(connection);
            CategoriaDAO categoriaDAO = new CategoriaDAO(connection);
            MusicaDAO musicaDAO = new MusicaDAO(connection);
            PlaylistDAO playlistDAO = new PlaylistDAO(connection);

            // Inserção de dados no banco
            autorDAO.insert(autor1);
            autorDAO.insert(autor2);
            autorDAO.insert(autor3);

            categoriaDAO.insert(categoria1);
            categoriaDAO.insert(categoria2);
            categoriaDAO.insert(categoria3);
            categoriaDAO.insert(categoria4);
            categoriaDAO.insert(categoria5);
            categoriaDAO.insert(categoria6);

            musicaDAO.insert(musica1);
            musicaDAO.insert(musica2);
            musicaDAO.insert(musica3);
            musicaDAO.insert(musica4);
            musicaDAO.insert(musica5);
            musicaDAO.insert(musica6);
            musicaDAO.insert(musica7);
            musicaDAO.insert(musica8);

            playlistDAO.insert(playlist1);
            playlistDAO.insert(playlist2);
            playlistDAO.insert(playlist3);


            // Consulta de dados do banco
            Autor autorConsultado = autorDAO.selectByCpf("12345678901");
            Categoria categoriaConsultada = categoriaDAO.selectByNome("Categoria 1");
            Musica musicaConsultada = musicaDAO.selectByTitulo("Gang Gang");
            Playlist playlistConsultada = playlistDAO.selectByTitulo("Meus MPBS");

            // Exibição dos dados consultados
            System.out.println("Autor consultado: " + autorConsultado);
            System.out.println("Categoria consultada: " + categoriaConsultada);
            System.out.println("Música consultada: " + musicaConsultada);
            System.out.println("Playlist consultada: " + playlistConsultada);

            // Atualização de dados
            Autor autor1Update = new Autor(1, "Davi", "DaviJCB", "12345678912");
            autorDAO.update(autor1Update);

            Categoria categoria2Update = new Categoria(2,"Funk Pesadão");
            categoriaDAO.update(categoria2Update);

            Musica musica3Update = new Musica(3, "BUM BUM SHAKALAKA", "a", "2023-02-02", categoria2Update, 200, autores2);
            musicaDAO.update(musica3Update);

            Playlist playlist2Update = new Playlist(2, "2019-09-20", "Meus Funks", categoria2Update, Conjunto2);
            playlistDAO.update(playlist2Update);

            // Deleção de dados
            musicaDAO.delete("Sítio do Pica-Pau");
            categoriaDAO.delete("POP");
            autorDAO.delete("23456789012");
            playlistDAO.delete("Meus Rocks");

            // Consulta de todos os elementos das tabelas
            ArrayList<Autor> autores = autorDAO.selectAll();
            ArrayList<Categoria> categorias = categoriaDAO.selectAll();
            ArrayList<Musica> musicas = musicaDAO.selectAll();
            ArrayList<Playlist> playlists = playlistDAO.selectAll();

            // Exibição de todos os elementos das tabelas
            System.out.println("\nTodos os autores: " + autores + "\n");
            System.out.println("Todas as categorias: " + categorias + "\n");
            System.out.println("Todas as músicas: " + musicas + "\n");
            System.out.println("Todas as playlists: " + playlists + "\n");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
