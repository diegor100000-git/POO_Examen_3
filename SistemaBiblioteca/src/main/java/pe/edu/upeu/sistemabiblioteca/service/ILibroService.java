package pe.edu.upeu.sistemabiblioteca.service;

import pe.edu.upeu.sistemabiblioteca.dto.ModeloDataAutocomplet;
import pe.edu.upeu.sistemabiblioteca.modelo.Libro;

import java.util.List;

public interface ILibroService{

    Libro save(Libro libro);
    List<Libro> findAll();
    Libro update(Libro libro);
    void delete(Long id);
    Libro findById(Long id);
    List<ModeloDataAutocomplet> listAutoCompletLibro(String nombre);
    public List<ModeloDataAutocomplet> listAutoCompletLibro();

}
