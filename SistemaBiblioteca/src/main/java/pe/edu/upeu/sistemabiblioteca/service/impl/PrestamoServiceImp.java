package pe.edu.upeu.sistemabiblioteca.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.edu.upeu.sistemabiblioteca.modelo.Prestamo;
import pe.edu.upeu.sistemabiblioteca.repository.ICrudGenericoRepository;
import pe.edu.upeu.sistemabiblioteca.service.IPrestamoService;

@RequiredArgsConstructor
@Service
public class PrestamoServiceImp extends CrudGenericoServiceImp<Prestamo, Long> implements IPrestamoService {


    @Override
    protected ICrudGenericoRepository<Prestamo, Long> getRepo() {
        return null;
    }
}
