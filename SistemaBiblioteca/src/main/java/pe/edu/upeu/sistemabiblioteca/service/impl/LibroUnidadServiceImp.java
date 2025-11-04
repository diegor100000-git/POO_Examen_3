package pe.edu.upeu.sistemabiblioteca.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.edu.upeu.sistemabiblioteca.modelo.LibroUnidad;
import pe.edu.upeu.sistemabiblioteca.repository.ICrudGenericoRepository;
import pe.edu.upeu.sistemabiblioteca.service.ILibroUnidadService;

@RequiredArgsConstructor
@Service
public class LibroUnidadServiceImp extends CrudGenericoServiceImp<LibroUnidad, Long> implements ILibroUnidadService {


    @Override
    protected ICrudGenericoRepository<LibroUnidad, Long> getRepo() {
        return null;
    }
}
