package com.literatura.reto_literatura.repository;

import com.literatura.reto_literatura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AutorRepositorio extends JpaRepository<Autor, Long> {
    Optional<Autor> findByNombre(String nombre);
    @Query("SELECT a FROM Autor a WHERE a.fecha_nacimiento <= :anio AND a.fecha_deceso >= :anio")
    List<Autor> listaAutoresVivosAnio(Integer anio);
}
