package com.literatura.reto_literatura.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String titulo;
    @ManyToOne
    private Autor autor;
    @Enumerated(EnumType.STRING)
    private Idioma idioma;
    private Integer numero_descargas;
    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
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

    public Idioma getIdioma() {
        return idioma;
    }

    public void setIdioma(Idioma idioma) {
        this.idioma = idioma;
    }

    public Integer getNumero_descargas() {
        return numero_descargas;
    }

    public void setNumero_descargas(Integer numero_descargas) {
        this.numero_descargas = numero_descargas;
    }

    public Libro(){}
    public Libro(DatosLibros datosLibros){
        this.titulo = datosLibros.titulo();
        this.idioma = Idioma.fromString(datosLibros.idiomas().toString().split(",")[0].trim());
        this.numero_descargas = datosLibros.numero_descargas();
    }
    @Override
    public String toString(){
        String nombreAutor = (autor != null) ? autor.getNombre() : "Autor desconocido";
        return String.format("***** Libro *****%nTitulo:"+
                " %s%nAutor: %s%nIdioma: %s%nNumero de Descarga:"+
                " %d%n**********%n",titulo,nombreAutor,idioma,numero_descargas);
    }
}
