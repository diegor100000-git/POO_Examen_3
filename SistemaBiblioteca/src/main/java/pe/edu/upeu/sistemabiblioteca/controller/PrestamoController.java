package pe.edu.upeu.sistemabiblioteca.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import pe.edu.upeu.sistemabiblioteca.components.*;
import pe.edu.upeu.sistemabiblioteca.dto.ModeloDataAutocomplet;

import pe.edu.upeu.sistemabiblioteca.modelo.Prestamo;
import pe.edu.upeu.sistemabiblioteca.service.*;
import java.util.SortedSet;
import java.util.TreeSet;


@Controller
public class PrestamoController {

    @FXML
    TextField autocompCliente, txtNombreApellido, txtDireccion, txtTelefono,
            autocompLibro, txtNombreLibro, txtCategoria, txtCantidad,
            txtRegDni,txtDescripcion;

    @FXML
    TableView<Prestamo> tableView;

    @FXML
    DatePicker FechaPrestamo, FechaRetorno;

    @FXML
    Button btnPrestamo;

    @FXML
    AnchorPane miContenedor;

    Stage stage;

    // Autocomplete con tipos correctos
    AutoCompleteTextField<ModeloDataAutocomplet> actfCliente;
    AutoCompleteTextField<ModeloDataAutocomplet> actfLibro;

    ModeloDataAutocomplet lastCliente;
    ModeloDataAutocomplet lastLibro;

    @Autowired
    IClienteService cs;

    @Autowired
    ILibroService libroService;

    @Autowired
    IPrestamoService prestamoService;

    @Autowired
    IDetallePrestamoService detallePrestamoService;

    private final SortedSet<ModeloDataAutocomplet> entriesClientes =
            new TreeSet<>((a, b) -> a.toString().compareToIgnoreCase(b.toString()));

    private final SortedSet<ModeloDataAutocomplet> entriesLibros =
            new TreeSet<>((a, b) -> a.toString().compareToIgnoreCase(b.toString()));

    @FXML
    public void initialize() {

        Platform.runLater(() -> stage = (Stage) miContenedor.getScene().getWindow());
        listarClientes();
        listarLibros();
        actfCliente = new AutoCompleteTextField<>(entriesClientes, autocompCliente);
        actfLibro   = new AutoCompleteTextField<>(entriesLibros, autocompLibro);

        autocompCliente.setOnAction(e -> seleccionarCliente());
        autocompCliente.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) seleccionarCliente();
        });

        autocompLibro.setOnAction(e -> seleccionarLibro());
        autocompLibro.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) seleccionarLibro();
        });

        personalizarTabla();
    }
    public void personalizarTabla() {

        TableColumn<Prestamo, String> colDni = new TableColumn<>("DNI");
        colDni.setCellValueFactory(new PropertyValueFactory<>("dniCliente"));

        TableColumn<Prestamo, String> colFechaPrestamo = new TableColumn<>("Fecha Préstamo");
        colFechaPrestamo.setCellValueFactory(new PropertyValueFactory<>("fechaPrestamo"));

        TableColumn<Prestamo, String> colFechaRetorno = new TableColumn<>("Fecha Retorno");
        colFechaRetorno.setCellValueFactory(new PropertyValueFactory<>("fechaRetorno"));

        TableColumn<Prestamo, String> colDesc = new TableColumn<>("Descripción");
        colDesc.setCellValueFactory(new PropertyValueFactory<>("descripcion"));

        tableView.getColumns().clear();
        tableView.getColumns().addAll(colDni, colFechaPrestamo, colFechaRetorno, colDesc);
        tableView.setTableMenuButtonVisible(true);
    }
    public void listarClientes() {
        entriesClientes.clear();
        entriesClientes.addAll(cs.listAutoCompletCliente());
    }

    public void listarLibros() {
        entriesLibros.clear();
        entriesLibros.addAll(libroService.listAutoCompletLibro());
    }

    private void seleccionarCliente() {

        ModeloDataAutocomplet sel = actfCliente.getLastSelectedObject();
        if (sel == null) return;

        txtRegDni.setText(sel.getIdx());
        txtNombreApellido.setText(sel.getNameDysplay());

        if (sel.getOtherData() != null && sel.getOtherData().contains(":")) {
            String[] data = sel.getOtherData().split(":");
            txtDireccion.setText(data.length > 0 ? data[0] : "");
            txtTelefono.setText(data.length > 1 ? data[1] : "");
        } else {
            txtDireccion.clear();
            txtTelefono.clear();
        }
    }

    private void seleccionarLibro() {

        ModeloDataAutocomplet sel = actfLibro.getLastSelectedObject();
        if (sel == null) return;
        txtNombreLibro.setText(sel.getNameDysplay());
        txtCategoria.setText(sel.getOtherData() != null ? sel.getOtherData() : "");

        txtCantidad.clear();
    }

    @FXML
    public void registrarPrestamo() {

        if (txtRegDni.getText().isEmpty()) {
            Toast.showToast(stage, "Seleccione un cliente", 2000, 400, 50);
            return;
        }

        if (FechaPrestamo.getValue() == null || FechaRetorno.getValue() == null) {
            Toast.showToast(stage, "Seleccione fechas", 2000, 400, 50);
            return;
        }

        if (txtCantidad.getText().isBlank()) {
            Toast.showToast(stage, "Ingrese cantidad", 2000, 400, 50);
            return;
        }
        try {
            int cant = Integer.parseInt(txtCantidad.getText());
            if (cant <= 0) {
                Toast.showToast(stage, "Cantidad debe ser mayor que 0", 2000, 400, 50);
                return;
            }
        } catch (Exception e) {
            Toast.showToast(stage, "Cantidad inválida", 2000, 400, 50);
            return;
        }

        Prestamo prestamo = new Prestamo();
        prestamo.setDniCliente(txtRegDni.getText());
        prestamo.setFechaPrestamo(FechaPrestamo.getValue().toString());
        prestamo.setFechaRetorno(FechaRetorno.getValue().toString());
        prestamo.setDescripcion(txtDescripcion.getText());

        Prestamo guardado = prestamoService.save(prestamo);

        Toast.showToast(stage, "Préstamo registrado", 2000, 400, 50);

        tableView.getItems().add(guardado);
        limpiarTodo();
    }
    public void limpiarTodo() {

        txtNombreApellido.clear();
        txtRegDni.clear();
        txtDireccion.clear();
        txtTelefono.clear();

        autocompLibro.clear();
        txtNombreLibro.clear();
        txtCategoria.clear();
        txtCantidad.clear();

        FechaPrestamo.setValue(null);
        FechaRetorno.setValue(null);
        txtDescripcion.clear();
    }
}

