package com.example.literalura.model;

import jakarta.persistence.*;

@Entity
@Table(name = "livros")
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    @ManyToOne
    @JoinColumn(name = "autor_id")
    private Autor autor;

    private String idioma;

    private Integer numeroDownloads;

    public Livro() {
    }

    public Livro(DadosLivros dadosLivros) {

        this.titulo = dadosLivros.titulo();
        this.numeroDownloads = dadosLivros.numeroDownloads();

        if (dadosLivros.idioma() != null && !dadosLivros.idioma().isEmpty()) {
            this.idioma = dadosLivros.idioma().get(0);
        }

        if (dadosLivros.autor() != null && !dadosLivros.autor().isEmpty()) {
            this.autor = new Autor(dadosLivros.autor().get(0));
        }
    }

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public Autor getAutor() {
        return autor;
    }

    public String getIdioma() {
        return idioma;
    }

    public Integer getNumeroDownloads() {
        return numeroDownloads;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public void setNumeroDownloads(Integer numeroDownloads) {
        this.numeroDownloads = numeroDownloads;
    }

    @Override
    public String toString() {

        String nomeAutor = (autor != null) ? autor.getNome() : "Autor desconhecido";

        return """
                -------- LIVRO --------
                Título: %s
                Autor: %s
                Idioma: %s
                Downloads: %d
                """.formatted(titulo, nomeAutor, idioma, numeroDownloads);
    }
}