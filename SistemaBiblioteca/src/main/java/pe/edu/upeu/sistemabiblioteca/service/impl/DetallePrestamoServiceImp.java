package pe.edu.upeu.sistemabiblioteca.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.edu.upeu.sistemabiblioteca.modelo.DetallePrestamo;
import pe.edu.upeu.sistemabiblioteca.repository.DetallePrestamoRepository;
import pe.edu.upeu.sistemabiblioteca.repository.ICrudGenericoRepository;
import pe.edu.upeu.sistemabiblioteca.service.IDetallePrestamoService;

@RequiredArgsConstructor
@Service
public class DetallePrestamoServiceImp extends CrudGenericoServiceImp <DetallePrestamo, Long> implements IDetallePrestamoService {

    private final DetallePrestamoRepository detallePrestamoRepositoryRepository;
    @Override

    protected ICrudGenericoRepository<DetallePrestamo, Long> getRepo() {
        return detallePrestamoRepositoryRepository;
    }
}
