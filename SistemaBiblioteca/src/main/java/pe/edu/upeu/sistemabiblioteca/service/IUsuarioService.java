package pe.edu.upeu.sistemabiblioteca.service;

import pe.edu.upeu.sistemabiblioteca.modelo.Usuario;

import java.util.List;

public interface IUsuarioService extends ICrudGenericoService<Usuario, Long>{
    Usuario loginUsuario(String nombreUsuario, String clave);
    List<Usuario> findAll();
}
