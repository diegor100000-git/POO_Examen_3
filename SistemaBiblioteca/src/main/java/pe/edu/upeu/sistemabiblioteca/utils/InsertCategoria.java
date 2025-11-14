package pe.edu.upeu.sistemabiblioteca.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pe.edu.upeu.sistemabiblioteca.modelo.Categoria;
import pe.edu.upeu.sistemabiblioteca.repository.CategoriaRepository;

import java.util.List;

@Component
public class InsertCategoria implements CommandLineRunner {
    @Autowired
    CategoriaRepository categoriaRepository;

    @Override
    public void run(String... args) throws Exception {

        if (categoriaRepository.count() == 0) {
            List<Categoria> categorias = List.of(
                    new Categoria(null, "Literatura"),
                    new Categoria(null, "Ciencias"),
                    new Categoria(null, "Tecnología"),
                    new Categoria(null, "Historia"),
                    new Categoria(null, "Ciencias Sociales"),
                    new Categoria(null, "Arte"),
                    new Categoria(null, "Psicología"),
                    new Categoria(null, "Educación"),
                    new Categoria(null, "Infantil"),
                    new Categoria(null, "General")
            );

            categoriaRepository.saveAll(categorias);
            System.out.println("✓ Categorías insertadas correctamente.");
        } else {
            System.out.println("Categorías ya estaban registradas.");
        }
    }
}
