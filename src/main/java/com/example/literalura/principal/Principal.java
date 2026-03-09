package com.example.literalura.principal;

import com.example.literalura.model.*;
import com.example.literalura.repository.AutorRepository;
import com.example.literalura.repository.LivroRepository;
import com.example.literalura.service.ConsumoApi;
import com.example.literalura.service.ConverteDados;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Component
public class Principal {

    private ConsumoApi consumoApi = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();
    private final String ENDERECO = "https://gutendex.com/books/?search=";
    private Scanner sc = new Scanner(System.in);

    private LivroRepository repositorio;

    private AutorRepository repositorioAutor;

    public Principal(LivroRepository repositorio, AutorRepository repositorioAutor) {
        this.repositorio = repositorio;
        this.repositorioAutor = repositorioAutor;
    }

    public void exibeMenu() {

        var opcao = -1;

        while (opcao != 9) {

            var menu = """
                    LiterAlura
                    
                    ESCOLHA O NÚMERO DE SUA OPÇÃO:
                    1 - Buscar livro pelo título
                    2 - Listar livros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos em um determinado ano
                    5 - Listar livros de um determinado idioma
                    
                    9 - Sair
                    """;
            System.out.println(menu);
            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {
                case 1:
                    buscarLivroPorTitulo();
                    break;

                case 2:
                    listarLivros();
                    break;

                case 3:
                    listarAutores();
                    break;

                case 4:
                    listarAutoresVivosPorAno();
                    break;

                case 5:
                    listarLivrosPorIdioma();
                    break;

                case 9:
                    System.out.println("Encerrando a aplicação!");
                    break;

                default:
                    System.out.println("Opção Inválida!");
            }
        }

    }

    private void listarLivrosPorIdioma() {
        System.out.println("""
                Insira o idioma para realizar a busca:
                es - espanhol
                en - inglês
                fr - francês
                pt - português
                """);
        var idioma = sc.nextLine();

        List<Livro> livrosPorIdioma = repositorio.findByIdiomaIgnoreCase(idioma);

        if (livrosPorIdioma.isEmpty()) {

            System.out.println("Não existem livros nesse idioma");
        } else {

            livrosPorIdioma.forEach(System.out::println);
        }
    }

    private void listarAutoresVivosPorAno() {
        System.out.println("Insira o ano que deseja pesquisar: ");
        var ano = sc.nextInt();
        sc.nextLine();

        List<Autor> autores = repositorioAutor.autoresPorDeterminadoAno(ano);

        if (autores.isEmpty()) {
            System.out.println("Nenhum autor encontrado neste ano.");
        } else {
            autores.forEach(System.out::println);
        }

    }

    private void listarAutores() {
        List<Autor> autores = repositorioAutor.findAll();

        autores.stream()
                .sorted(Comparator.comparing(Autor::getNome))
                .forEach(System.out::println);
    }

    private void listarLivros() {
        List<Livro> livros = repositorio.findAll();
        livros.stream()
                .sorted(Comparator.comparing(Livro::getTitulo))
                .forEach(System.out::println);
    }

    @Transactional
    private void buscarLivroPorTitulo() {

        System.out.println("Insira o nome do livro que você deseja procurar: ");
        var nomeLivro = sc.nextLine();

        var json = consumoApi.obterDados(ENDERECO + nomeLivro.replace(" ", "%20"));

        DadosResultado dadosR = conversor.obterDados(json, DadosResultado.class);

        if (dadosR.livros() == null || dadosR.livros().isEmpty()) {
            System.out.println("Livro não encontrado.");
            return;
        }

        DadosLivros dadosLivro = dadosR.livros().get(0);

        Livro livro = new Livro(dadosLivro);

        if (livro.getAutor() == null) {
            System.out.println("Livro encontrado, mas não possui autor cadastrado.");
            return;
        }

        Optional<Autor> autorBanco =
                repositorioAutor.findByNome(livro.getAutor().getNome());

        if (autorBanco.isPresent()) {
            livro.setAutor(autorBanco.get());
        } else {
            repositorioAutor.save(livro.getAutor());
        }

        Optional<Livro> livroExistente =
                repositorio.findByTituloIgnoreCase(livro.getTitulo());

        if (livroExistente.isPresent()) {
            System.out.println("Livro já cadastrado.");
        } else {
            repositorio.save(livro);
            System.out.println("Livro salvo com sucesso!");
        }

        System.out.println(livro);
    }
}
