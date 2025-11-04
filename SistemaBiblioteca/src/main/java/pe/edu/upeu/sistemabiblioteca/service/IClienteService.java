package pe.edu.upeu.sistemabiblioteca.service;

import pe.edu.upeu.sistemabiblioteca.dto.ModeloDataAutocomplet;
import pe.edu.upeu.sistemabiblioteca.dto.PersonaDto;
import pe.edu.upeu.sistemabiblioteca.modelo.Cliente;

import java.util.List;

public interface IClienteService extends ICrudGenericoService<Cliente,String> {

    PersonaDto findByDni(String dni);
    List<ModeloDataAutocomplet> listAutoCompletCliente();
}
