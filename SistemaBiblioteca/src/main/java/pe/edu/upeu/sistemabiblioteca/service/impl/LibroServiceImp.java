package pe.edu.upeu.sistemabiblioteca.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upeu.sistemabiblioteca.dto.ModeloDataAutocomplet;
import pe.edu.upeu.sistemabiblioteca.modelo.Libro;
import pe.edu.upeu.sistemabiblioteca.repository.LibroRepository;
import pe.edu.upeu.sistemabiblioteca.service.ILibroService;

import java.util.ArrayList;
import java.util.List;

@Service
public class LibroServiceImp implements ILibroService {
    private static final Logger logger = LoggerFactory.getLogger(LibroServiceImp.class);
    @Autowired
    LibroRepository lRepo;
    @Override
    public Libro save(Libro libro) {
        return lRepo.save(libro);
    }
    @Override
    public List<Libro> findAll() {
        return lRepo.findAll();
    }
    @Override
    public Libro update(Libro producto) {
        return lRepo.save(producto);
    }
    @Override
    public void delete(Long id) {
        lRepo.deleteById(id);
    }
    @Override
    public Libro findById(Long id) {
        return lRepo.findById(id).orElse(null);
    }
    public List<ModeloDataAutocomplet> listAutoCompletLibro(String nombre) {
        List<ModeloDataAutocomplet> listarLibro = new ArrayList<>();
        try {
            for (Libro libro :
                    lRepo.listAutoCompletLibro(nombre + "%")) {
                ModeloDataAutocomplet data = new ModeloDataAutocomplet();data.setIdx(libro.getNombre());
                data.setNameDysplay(String.valueOf(libro.getIdLibro()));data.setOtherData(String.valueOf(libro.getLibroUnidad()));

                listarLibro.add(data);
            }
        } catch (Exception e) {
            logger.error("Error al realizar la busqueda", e);
        }
        return listarLibro;
    }
    public List<ModeloDataAutocomplet> listAutoCompletLibro() {
        List<ModeloDataAutocomplet> listarLibro = new ArrayList<>();
        try {
            for (Libro libro : lRepo.findAll())
            {ModeloDataAutocomplet data = new ModeloDataAutocomplet();
                data.setIdx(String.valueOf(libro.getIdLibro()));
                data.setNameDysplay(libro.getNombre());
                listarLibro.add(data);
            }
        } catch (Exception e) {
            logger.error("Error al realizar la busqueda", e);
        }
        return listarLibro;
    }
}
