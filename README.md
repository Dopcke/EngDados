# Trabalho de Engenharia de Dados (BackEnd)

## 🔮 Objetivos do projeto:
- Diagrama EER do software que sua equipe está responsável -- usando o MySQL Workbench
- Criação das classes do software e de seus respectivos atributos e métodos usando a linguagem de programação Java
- Criação das classes de dao para manipulação e consulta de dados do software via Java
  - Um método para inserção de dados na tabela
  - Um método para atualização de dados na tabela
  - Um método para deleção de dados da tabela
  - Um método para consultar dados de um elemento específico da tabela
  - Um método para consultar dados de todos os elementos da tabela
  - Criação de uma classe contendo o main que irá fazer a criação dos objetos das classes criadas e chamará os respectivos comandos para popular o banco, manipular dados do banco e consultar informações do banco.
  - 
-------
### 💻 Qual é o nosso software ? 

O Software Sound é uma aplicação dedicada à gestão de informações relacionadas a músicas, autores, playlists e categorias. 

---------
### 🚹 Entidades:
- **Autor:** cpf, nome original, nome artístico
- **Categoria:** nome
- **Música:** título, letra, data de lançamento, categoria, duração, autores.
- **👹 Playlist:** data de criação, título, categoria de músicas que podem ser incluídas na
playlist, músicas
  - Músicas inseridas na playlist devem ser de _mesma categoria_ da playlist

