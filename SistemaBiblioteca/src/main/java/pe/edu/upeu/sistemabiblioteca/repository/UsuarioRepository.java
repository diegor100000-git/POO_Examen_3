package pe.edu.upeu.sistemabiblioteca.repository;

import pe.edu.upeu.sistemabiblioteca.modelo.Usuario;

public interface UsuarioRepository extends ICrudGenericoRepository <Usuario, Long>{
    Usuario findByNombreUsuarioAndClave(String nombreUsuario, String clave);
}
