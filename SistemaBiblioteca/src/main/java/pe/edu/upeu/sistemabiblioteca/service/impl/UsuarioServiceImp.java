package pe.edu.upeu.sistemabiblioteca.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Usuario loginUsuario(String nombreUsuario, String clave) {
        Usuario usuario = usuarioRepository.findByNombreUsuario(nombreUsuario);

        if (usuario != null && passwordEncoder.matches(clave, usuario.getClave())) {
            return usuario;
        }
        return null;
    }

    @Override
    public Usuario registrar(Usuario usuario) {
        usuario.setClave(passwordEncoder.encode(usuario.getClave()));
        return usuarioRepository.save(usuario);
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
