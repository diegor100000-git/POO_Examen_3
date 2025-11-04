package pe.edu.upeu.sistemabiblioteca.service.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pe.edu.upeu.sistemabiblioteca.dto.ModeloDataAutocomplet;
import pe.edu.upeu.sistemabiblioteca.dto.PersonaDto;
import pe.edu.upeu.sistemabiblioteca.modelo.Cliente;
import pe.edu.upeu.sistemabiblioteca.repository.ClienteRepository;
import pe.edu.upeu.sistemabiblioteca.repository.ICrudGenericoRepository;
import pe.edu.upeu.sistemabiblioteca.service.IClienteService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteServiceImp extends CrudGenericoServiceImp<Cliente,String> implements IClienteService {
    private final ClienteRepository clienteRepository;

    Logger logger = LoggerFactory.getLogger(ClienteServiceImp.class);

    @Override
    protected ICrudGenericoRepository<Cliente, String> getRepo() {
        return clienteRepository;
    }

    @Override
    public PersonaDto findByDni(String dni) {
        return null;
    }

    @Override
    public List<ModeloDataAutocomplet> listAutoCompletCliente() {
        List<ModeloDataAutocomplet> listarclientes = new ArrayList<>();
        try {
            for (Cliente cliente : clienteRepository.findAll()) {
                ModeloDataAutocomplet data = new ModeloDataAutocomplet();
                data.setIdx(cliente.getDni());
                data.setNameDysplay(cliente.getNombres());
                data.setOtherData(cliente.getApellido());
                data.setOtherData(cliente.getDireccion());
                data.setOtherData(cliente.getTelefono());

                listarclientes.add(data);
            }
        } catch (Exception e) {
            logger.error("Error durante la operación", e);
        }
        return listarclientes;
    }
}