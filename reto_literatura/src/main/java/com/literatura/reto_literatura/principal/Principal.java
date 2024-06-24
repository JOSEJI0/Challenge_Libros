package com.literatura.reto_literatura.principal;

import com.literatura.reto_literatura.model.*;
import com.literatura.reto_literatura.repository.AutorRepositorio;
import com.literatura.reto_literatura.repository.LibroRepositorio;
import com.literatura.reto_literatura.service.ConsumoAPI;
import com.literatura.reto_literatura.service.ConvierteDatos;

import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {
    private Scanner entrada = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private final String URL_BASE = "https://gutendex.com/books/?search=";
    private ConvierteDatos conversor = new ConvierteDatos();
    private LibroRepositorio libroRepository;
    private AutorRepositorio autorRepository;
    private List<Libro> libros;
    private List<Autor> autores;

    public Principal(LibroRepositorio libroRepository, AutorRepositorio autorRepository){
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
    }
    public void mostrarMenu(){
        var opcion = -1;
        while (opcion != 0){
            var menu = """
                    1 - Buscar libros por título
                    2 - Mostrar libros registrados
                    3 - Mostrar autores registrados
                    4 - Autores vivos en determinado año
                    5 - Buscar libros por idioma
                    6 - Libro más descargado y menos descargado
                    
                    0 - Salir de la aplicación  
                    """;
            System.out.println(menu);
            while (!entrada.hasNextInt()){
                System.out.println("Error ingresa un número valido");
                entrada.nextLine();
            }
            opcion = entrada.nextInt();
            entrada.nextLine();
            switch (opcion){
                case 1:
                    buscarLibro();
                    break;
                case 2:
                    mostrarLibros();
                    break;
                case 3:
                    mostrarAutores();
                    break;
                case 4:
                    autoresVivosPorAnio();
                    break;
                case 5:
                    buscarLibroPorIdioma();
                    break;
                case 6:
                    rankingLibro();
                    break;
                case 0:
                    System.out.println("Saliste de la aplicación");
                    break;
                default:
                    System.out.printf("Opción Inválida\n");
            }
        }
    }
    private DatosBusqueda getBusqueda(){
        System.out.println("Ingresa el nombre del libro que deseas buscar: ");
        var nombreLibro = entrada.nextLine();
        var json = consumoApi.obtenerDatos(URL_BASE + nombreLibro.replace(" ","%20"));
        DatosBusqueda datos = conversor.obtenerDatos(json, DatosBusqueda.class);
        return datos;
    }
    private void buscarLibro(){
        DatosBusqueda datosBusqueda = getBusqueda();
        if(datosBusqueda != null && !datosBusqueda.resultado().isEmpty()){
            DatosLibros libroUno = datosBusqueda.resultado().get(0);

            Libro libro = new Libro(libroUno);
            System.out.println("***** Libro *****");
            System.out.println(libro);
            System.out.println("*****************");

            Optional<Libro> libroExiste = libroRepository.findByTitulo(libro.getTitulo());
            if (libroExiste.isPresent()){
                System.out.println("\nEl libro ya fue registrado\n");
            } else{
                if (!libroUno.autor().isEmpty()){
                    DatosAutor autor = libroUno.autor().get(0);
                    Autor autor1 = new Autor(autor);
                    Optional<Autor> autorOptional = autorRepository.findByNombre(autor1.getNombre());

                    if (autorOptional.isPresent()){
                        Autor autorExiste = autorOptional.get();
                        libro.setAutor(autorExiste);
                        libroRepository.save(libro);
                    } else {
                        Autor autornew = autorRepository.save(autor1);
                        libro.setAutor(autornew);
                        libroRepository.save(libro);
                    }
                    Integer numeroDescargas = libro.getNumero_descargas() != null ? libro.getNumero_descargas() : 0;
                    System.out.println("***** Libro *****");
                    System.out.printf("Titulo: %s%nAutor: %s%nIdioma: %s%nNumero de Descargas: %s%n",
                            libro.getTitulo(), autor1.getNombre(), libro.getIdioma(), libro.getNumero_descargas());
                    System.out.println("*****************\n");
                } else {
                    System.out.println("Sin autor");
                }
            }
        } else {
            System.out.println("Libro no encontrado");
        }
    }
    private void mostrarLibros(){
        libros = libroRepository.findAll();
        libros.stream()
                .forEach(System.out::println);
    }
    private void mostrarAutores(){
        autores = autorRepository.findAll();
        autores.stream()
                .forEach(System.out::println);
    }
    private void autoresVivosPorAnio(){
        System.out.println("Ingresa el año de vivo del autor o autores que desea buscar: ");
        var anio = entrada.nextInt();
        autores = autorRepository.listaAutoresVivosAnio(anio);
        autores.stream()
                .forEach(System.out::println);
    }
    private List<Libro> datosBusquedaIdioma(String idioma){
        var dato = Idioma.fromString(idioma);
        System.out.println("Lenguaje buscado: " + dato);

        List<Libro> libroIdioma = libroRepository.findByIdioma(dato);
        return libroIdioma;
    }
    private void buscarLibroPorIdioma(){
        System.out.println("Selecciona el idioma del libro que deseas buscar: ");

        var opcion = -1;
        while (opcion != 0){
            var opciones = """
                    1. En - Ingles
                    2. Es - Español
                    3. Fr - Francés
                    4. Pt - Portugués
                    
                    0. Regresar al inicio
                    """;
            System.out.println(opciones);
            while (!entrada.hasNextInt()){
                System.out.println("Error, ingresa un número válido");
                entrada.nextLine();
            }
            opcion = entrada.nextInt();
            entrada.nextLine();
            switch (opcion){
                case 1:
                    List<Libro> librosIngles = datosBusquedaIdioma("[en]");
                    librosIngles.forEach(System.out::println);
                    break;
                case 2:
                    List<Libro> librosEspanol = datosBusquedaIdioma("[es]");
                    librosEspanol.forEach(System.out::println);
                    break;
                case 3:
                    List<Libro> librosFrances = datosBusquedaIdioma("[fr]");
                    librosFrances.forEach(System.out::println);
                    break;
                case 4:
                    List<Libro> librosPortugues = datosBusquedaIdioma("[pt]");
                    librosPortugues.forEach(System.out::println);
                    break;
                case  0:
                    return;
                default:
                    System.out.println("Ningún idioma seleccionado");
            }
        }
    }
    private void rankingLibro(){
        libros = libroRepository.findAll();
        IntSummaryStatistics est = libros.stream()
                .filter(l -> l.getNumero_descargas() > 0)
                .collect(Collectors.summarizingInt(Libro::getNumero_descargas));

        Libro libroMasDescargado = libros.stream()
                .filter(l -> l.getNumero_descargas() == est.getMax())
                .findFirst()
                .orElse(null);

        Libro libroMenosDescargado = libros.stream()
                .filter(l -> l.getNumero_descargas() == est.getMin())
                .findFirst()
                .orElse(null);
        System.out.println("************************");
        System.out.printf("%nLibro más descargado: %s%nNúmero de descargas: " +
                "%d%n%nLibro menos descargado: %s%nNúmero de descargas: " +
                "%d%n%n", libroMasDescargado.getTitulo(),est.getMax(),
                libroMenosDescargado.getTitulo(),est.getMin());
        System.out.println("***********************");
    }
}