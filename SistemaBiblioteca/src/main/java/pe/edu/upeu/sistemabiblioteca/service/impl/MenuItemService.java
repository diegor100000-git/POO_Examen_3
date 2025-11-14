package pe.edu.upeu.sistemabiblioteca.service.impl;

import org.springframework.stereotype.Service;
import pe.edu.upeu.sistemabiblioteca.dto.MenuItem;
import pe.edu.upeu.sistemabiblioteca.service.IMenuItemService;

import java.util.ArrayList;
import java.util.List;


@Service
public class MenuItemService implements IMenuItemService {

    @Override
    public List<MenuItem> listarMenu() {
        List<MenuItem> lista = new ArrayList<>();
        lista.add(new MenuItem("principal", "/view/login.fxml", "Salir", "S"));
        lista.add(new MenuItem("libro", "/view/main_libro.fxml", "Gestión Libros", "T"));
        lista.add(new MenuItem("cliente", "/view/main_cliente.fxml", "Gestionar Cliente", "T"));
        lista.add(new MenuItem("prestamo", "/view/main_prestamo.fxml", "RegistrarPrestamo", "T"));
        return lista;
    }
}
