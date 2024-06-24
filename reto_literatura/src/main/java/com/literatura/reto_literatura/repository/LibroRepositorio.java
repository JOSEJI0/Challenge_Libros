package com.literatura.reto_literatura.repository;

import com.literatura.reto_literatura.model.Idioma;
import com.literatura.reto_literatura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LibroRepositorio extends JpaRepository<Libro, Long> {
    List<Libro> findByIdioma(Idioma idioma);
    Optional<Libro> findByTitulo(String titulo);
    @Query("SELECT l FROM Libro l ORDER BY l.numero_descargas DESC LIMIT 10")
    List<Libro> top10LibrosDescargados();
}
