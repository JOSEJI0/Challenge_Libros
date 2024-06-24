package com.literatura.reto_literatura;

import com.literatura.reto_literatura.principal.Principal;
import com.literatura.reto_literatura.repository.AutorRepositorio;
import com.literatura.reto_literatura.repository.LibroRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RetoLiteraturaApplication implements CommandLineRunner {
	@Autowired
	private LibroRepositorio libroRepository;
	@Autowired
	private AutorRepositorio autorRepository;
	public static void main(String[] args) {
		SpringApplication.run(RetoLiteraturaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(libroRepository, autorRepository);
		principal.mostrarMenu();
	}
}