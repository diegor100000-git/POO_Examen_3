package pe.edu.upeu.sistemabiblioteca.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.edu.upeu.sistemabiblioteca.dto.ComboBoxOption;
import pe.edu.upeu.sistemabiblioteca.modelo.Categoria;
import pe.edu.upeu.sistemabiblioteca.repository.CategoriaRepository;
import pe.edu.upeu.sistemabiblioteca.repository.ICrudGenericoRepository;
import pe.edu.upeu.sistemabiblioteca.service.ICategoriaService;

import java.util.ArrayList;
import java.util.List;

@Transactional
@RequiredArgsConstructor
@Service
public class CategoriaServiceImp extends CrudGenericoServiceImp <Categoria, Long> implements ICategoriaService {

    private final CategoriaRepository categoriaRepository;
    @Override
    protected ICrudGenericoRepository<Categoria, Long> getRepo() {
        return categoriaRepository;
    }

    @Override
    public List<ComboBoxOption> listarCombobox() {
        List<ComboBoxOption> listar=new ArrayList<>();
        ComboBoxOption cb;
        for(Categoria cate : categoriaRepository.findAll()) {
            cb=new ComboBoxOption();
            cb.setKey(String.valueOf(cate.getIdCategoria()));
            cb.setValue(cate.getNombre());
            listar.add(cb);
        }
        return listar;
    }
}
