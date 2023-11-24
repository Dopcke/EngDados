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
        Categoria POP = new Categoria(1, "POP");
        Categoria Samba = new Categoria(2, "Samba");
        Categoria Mpb = new Categoria(3, "Mpb");
        Categoria Forro = new Categoria(4, "Forro");
        Categoria Pagode = new Categoria(5, "Pagode");
        Categoria Rock = new Categoria(6, "Rock");

        Autor autor1 = new Autor("Daniel Studart", "Stu", "00000000001");
        Autor autor2 = new Autor("Davi Jacob", "DaviJac", "00000000002");
        Autor autor3 = new Autor("João Paulo", "JP Dopcke", "00000000003");
        Autor autor4 = new Autor("Douglas", "Big Dog", "00000000004");
        Autor autor5 = new Autor("Talita", "Tatá Wernek", "00000000005");
        Autor autor6 = new Autor("Clayton Gomes", "Clayton", "00000000006");
        Autor autor7 = new Autor("Ubiratan", "Um Bira", "00000000007");

        ArrayList<Autor> Lista_Autores1 = new ArrayList<Autor>();
        Lista_Autores1.add(autor1);
        Lista_Autores1.add(autor2);
        Lista_Autores1.add(autor5);
        
        ArrayList<Autor> Lista_Autores2 = new ArrayList<Autor>();
        Lista_Autores2.add(autor3);
        Lista_Autores2.add(autor5);
        
        ArrayList<Autor> Lista_Autores3 = new ArrayList<Autor>();
        Lista_Autores3.add(autor4);
     
        
        ArrayList<Autor> Lista_Autores4 = new ArrayList<Autor>();
        Lista_Autores4.add(autor7);
        Lista_Autores4.add(autor6);

        Musica musica1 = new Musica("Gang Gang", "abcde", "2003-07-22", Pagode, 230, Lista_Autores1);
        Musica musica2 = new Musica("Bang Bang da Anitta", "abcde", "2003-07-22", Samba, 230, Lista_Autores2);
        Musica musica3 = new Musica("Braga Samba", "abcde", "2003-07-22", Mpb, 210, Lista_Autores3);
        Musica musica4 = new Musica("Vai malandra", "abcde", "2003-07-22", Forro, 190, Lista_Autores4);
        Musica musica5 = new Musica("Pé de pano", "abcde", "2003-07-22", Rock, 522, Lista_Autores1);
        Musica musica6 = new Musica("Sítio do Pica-Pau", "abcde", "2003-07-22", Forro, 200, Lista_Autores2);
        Musica musica7 = new Musica("Naquela Mesa", "abcde", "2003-07-22", Mpb, 90, Lista_Autores3);
        Musica musica8 = new Musica("Não era pra ser", "abcde", "2003-07-22", Rock, 100, Lista_Autores4);

        ArrayList<Musica> Conjunto1 = new ArrayList<Musica>();
        ArrayList<Musica> Conjunto2 = new ArrayList<Musica>();
        ArrayList<Musica> Conjunto3 = new ArrayList<Musica>();


        Playlist playlist1 = new Playlist();
        Playlist.ConfereCateforia(musica1, Conjunto1, playlist1);
        Playlist.ConfereCateforia(musica7, Conjunto1, playlist1);
        Playlist.ConfereCateforia(musica3, Conjunto1, playlist1);
        playlist1 = new Playlist("2003-07-22", "Meus MPBS", Mpb, Conjunto1);
        
        Playlist playlist2 = new Playlist();
        Playlist.ConfereCateforia(musica4, Conjunto2, playlist2);
        Playlist.ConfereCateforia(musica6, Conjunto2, playlist2);
        Playlist.ConfereCateforia(musica7, Conjunto2, playlist2);
        playlist2 = new Playlist("2019-09-20", "Meus Forros", Forro, Conjunto2);

        Playlist playlist3 = new Playlist();
        Playlist.ConfereCateforia(musica5, Conjunto3, playlist3);
        Playlist.ConfereCateforia(musica8, Conjunto3, playlist3);
        playlist3 = new Playlist("2003-10-17", "Meus Rocks", Rock, Conjunto3);

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
            autorDAO.insert(autor4);
            autorDAO.insert(autor5);
            autorDAO.insert(autor6);
            autorDAO.insert(autor7);

            categoriaDAO.insert(POP);
            categoriaDAO.insert(Samba);
            categoriaDAO.insert(Mpb);
            categoriaDAO.insert(Forro);
            categoriaDAO.insert(Rock);
            categoriaDAO.insert(Pagode);

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
            Musica musicaConsultada = musicaDAO.selectByTitulo("Música 1");

            // Exibição dos dados consultados
            System.out.println("Autor consultado: " + autorConsultado);
            System.out.println("Categoria consultada: " + categoriaConsultada);
            System.out.println("Música consultada: " + musicaConsultada);

            // Atualização de dados
            autor1.setNomeArtistico("Novo Artista");
            autorDAO.update(autor1);

            // Deleção de dados
            musicaDAO.delete("Musica 2");

            // Consulta de todos os elementos das tabelas
            ArrayList<Autor> autores = autorDAO.selectAll();
            ArrayList<Categoria> categorias = categoriaDAO.selectAll();
            ArrayList<Musica> musicas = musicaDAO.selectAll();

            // Exibição de todos os elementos das tabelas
            System.out.println("\nTodos os autores: " + autores);
            System.out.println("Todas as categorias: " + categorias);
            System.out.println("Todas as músicas: " + musicas);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
