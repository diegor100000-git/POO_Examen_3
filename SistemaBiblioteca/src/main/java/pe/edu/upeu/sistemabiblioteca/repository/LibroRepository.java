package pe.edu.upeu.sistemabiblioteca.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.edu.upeu.sistemabiblioteca.modelo.Libro;

import java.util.List;

public interface LibroRepository extends JpaRepository<Libro, Long> {
    @Query(value = "SELECT l.* FROM libro l WHERE l.nombre like:filter", nativeQuery = true)
    List<Libro> listAutoCompletLibro(@Param("filter") String filter);


    @Query(value = "SELECT l.* FROM libro l WHERE l.id_categoria=:filter", nativeQuery = true)
    List<Libro> listLibroCategoria(@Param("filter") Integer filter);


}
