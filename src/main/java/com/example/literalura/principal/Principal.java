package com.example.literalura.principal;

import com.example.literalura.service.ConsumoApi;
import com.example.literalura.service.ConverteDados;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class Principal {

    private ConsumoApi consumoApi = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();
    private final String ENDERECO = "https://gutendex.com/books/?search=";
    private Scanner sc = new Scanner(System.in);

    public void exibeMenu() {

        var opcao = -1;

        while (opcao != 9){

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

            switch (opcao){
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
    }

    private void listarAutoresVivosPorAno() {
    }

    private void listarAutores() {
    }

    private void listarLivros() {
    }

    private void buscarLivroPorTitulo() {
    }
}
