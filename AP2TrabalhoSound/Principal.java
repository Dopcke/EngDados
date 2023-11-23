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
        Autor autor1 = new Autor("Artista 1", "Autor Original 1", "12345678901");
        Autor autor2 = new Autor("Artista 2", "Autor Original 2", "23456789012");

        Categoria categoria1 = new Categoria("Categoria 1");
        Categoria categoria2 = new Categoria("Categoria 2");

        Musica musica1 = new Musica("Música 1", "Letra da Música 1", "2023-01-01", categoria1, 200, new ArrayList<>());
        Musica musica2 = new Musica("Música 2", "Letra da Música 2", "2023-02-02", categoria2, 180, new ArrayList<>());


        // Inicialização da conexão com o banco de dados
        ConnectionFactory fabricaDeConexao = new ConnectionFactory();
        try (Connection connection = fabricaDeConexao.recuperaConexao()) {
            AutorDAO autorDAO = new AutorDAO(connection);
            CategoriaDAO categoriaDAO = new CategoriaDAO(connection);
            MusicaDAO musicaDAO = new MusicaDAO(connection);

            // Inserção de dados no banco
            autorDAO.insert(autor1);
            autorDAO.insert(autor2);

            categoriaDAO.insert(categoria1);
            categoriaDAO.insert(categoria2);

            musicaDAO.insert(musica1);
            musicaDAO.insert(musica2);


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
