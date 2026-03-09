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
private Autor autor;
private String idioma;
private Double numeroDownloads;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public Double getNumeroDownloads() {
        return numeroDownloads;
    }

    public void setNumeroDownloads(Double numeroDownloads) {
        this.numeroDownloads = numeroDownloads;
    }

    public Livro(){

    }

    public Livro(DadosLivros dadosLivros){
    this.titulo = dadosLivros.titulo();
    this.numeroDownloads = dadosLivros.numeroDownloads();
        if (dadosLivros.idioma() != null && !dadosLivros.idioma().isEmpty()) {
            this.idioma = dadosLivros.idioma().get(0);
        }
    }

    @Override
    public String toString() {
        String nomeAutor = (autor != null) ? autor.getNome() : "Autor desconhecido";

        return "***Livro***" +
                "Titulo='" + titulo + '\n' +
                ", Autor=" + autor + '\n' +
                ", Idioma='" + idioma + '\n' +
                ", Número de Downloads=" + numeroDownloads;
    }
}
