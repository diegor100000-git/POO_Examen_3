package pe.edu.upeu.sistemabiblioteca.service;


import pe.edu.upeu.sistemabiblioteca.dto.ComboBoxOption;
import pe.edu.upeu.sistemabiblioteca.modelo.Categoria;

import java.util.List;

public interface ICategoriaService extends ICrudGenericoService<Categoria,Long> {

    List<ComboBoxOption> listarCombobox();

}
