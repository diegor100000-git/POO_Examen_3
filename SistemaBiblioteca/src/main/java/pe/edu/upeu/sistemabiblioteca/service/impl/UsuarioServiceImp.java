package pe.edu.upeu.sistemabiblioteca.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pe.edu.upeu.sistemabiblioteca.modelo.Usuario;
import pe.edu.upeu.sistemabiblioteca.repository.UsuarioRepository;
import pe.edu.upeu.sistemabiblioteca.service.IUsuarioService;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UsuarioServiceImp extends CrudGenericoServiceImp<Usuario, Long> implements IUsuarioService {


    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public Usuario loginUsuario(String nombreUsuario, String clave) {
        return usuarioRepository.findByNombreUsuarioAndClave(nombreUsuario, clave);
    }

    @Override
    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    @Override
    public UsuarioRepository getRepo() {
        return usuarioRepository;
    }
}
