package pe.edu.upeu.sistemabiblioteca.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

import pe.edu.upeu.sistemabiblioteca.modelo.DetallePrestamo;
import pe.edu.upeu.sistemabiblioteca.modelo.Libro;
import pe.edu.upeu.sistemabiblioteca.modelo.Prestamo;
import pe.edu.upeu.sistemabiblioteca.service.*;
import java.util.SortedSet;
import java.util.TreeSet;


@Controller
public class PrestamoController {

    @FXML
    TextField autocompCliente, txtNombreApellido, txtDireccion, txtTelefono,
            autocompLibro, txtNombreLibro, txtCategoria,
            txtRegDni, txtDescripcion;

    @FXML
    TableView<Prestamo> tableView;

    @FXML
    DatePicker FechaPrestamo, FechaRetorno;

    @FXML
    Button btnPrestamo, btnAñadir;

    @FXML
    AnchorPane miContenedor;

    Stage stage;
    AutoCompleteTextField<ModeloDataAutocomplet> actfCliente;
    AutoCompleteTextField<ModeloDataAutocomplet> actfLibro;

    ModeloDataAutocomplet lastLibro;

    @Autowired
    IClienteService cs;

    @Autowired
    ILibroService libroService;

    @Autowired
    IPrestamoService prestamoService;

    @Autowired
    IDetallePrestamoService detallePrestamoService;

    private ObservableList<Prestamo> previsualizacion = FXCollections.observableArrayList();

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
        autocompCliente.setOnKeyPressed(e -> { if (e.getCode() == KeyCode.ENTER) seleccionarCliente(); });

        autocompLibro.setOnAction(e -> seleccionarLibro());
        autocompLibro.setOnKeyPressed(e -> { if (e.getCode() == KeyCode.ENTER) seleccionarLibro(); });

        personalizarTabla();
        tableView.setItems(previsualizacion);
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

        TableColumn<Prestamo, Void> colAcciones = new TableColumn<>("Acciones");

        colAcciones.setCellFactory(col -> new TableCell<>() {
            private final Button btnEliminar = new Button("Eliminar");

            {
                btnEliminar.setOnAction(e -> {
                    Prestamo p = getTableView().getItems().get(getIndex());
                    if (p != null) {
                        previsualizacion.remove(p);
                        Toast.showToast(stage, "Eliminado de la lista", 2000, 400, 50);
                    }
                });
                btnEliminar.setStyle(
                        "-fx-background-color: #d9534f; -fx-text-fill: white; -fx-font-size: 12px; -fx-cursor: hand;"
                );
            }
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btnEliminar);
            }
        });
        tableView.getColumns().clear();
        tableView.getColumns().addAll(colDni, colFechaPrestamo, colFechaRetorno, colDesc, colAcciones);
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

        lastLibro = sel;

        txtNombreLibro.setText(sel.getNameDysplay());

        if (sel.getOtherData() != null) {
            if (sel.getOtherData().contains(":")) {
                txtCategoria.setText(sel.getOtherData().split(":")[0]);
            } else {
                txtCategoria.setText(sel.getOtherData());
            }
        } else {
            txtCategoria.clear();
        }
    }
    @FXML
    public void añadirPrevisualizacion() {

        if (txtRegDni.getText().isEmpty()
                || txtNombreLibro.getText().isEmpty()
                || FechaPrestamo.getValue() == null
                || FechaRetorno.getValue() == null) {

            Toast.showToast(stage, "Complete todos los campos antes de añadir", 2000, 400, 50);
            return;
        }

        Prestamo p = new Prestamo();
        p.setDniCliente(txtRegDni.getText());
        p.setFechaPrestamo(FechaPrestamo.getValue().toString());
        p.setFechaRetorno(FechaRetorno.getValue().toString());
        p.setDescripcion(txtDescripcion.getText());

        previsualizacion.add(p);

        Toast.showToast(stage, "Añadido", 2000, 400, 50);

        limpiarTodo();
    }

    @FXML
    public void registrarPrestamo() {

        if (previsualizacion.isEmpty()) {
            Toast.showToast(stage, "Primero añada elementos a la previsualización", 2000, 400, 50);
            return;
        }

        for (Prestamo p : previsualizacion) {

            Prestamo prestamoGuardado = prestamoService.save(p);

            DetallePrestamo detalle = new DetallePrestamo();
            detalle.setPrestamo(prestamoGuardado);

            Libro libro = new Libro();
            libro.setIdLibro(Long.valueOf(lastLibro.getIdx()));
            libro.setNombre(txtNombreLibro.getText());
            detalle.setLibro(libro);

            detallePrestamoService.save(detalle);
        }

        Toast.showToast(stage, "Préstamo(s) registrados", 2000, 400, 50);

        previsualizacion.clear();
        limpiarTodo();
    }

    private void limpiarDatosLibro() {
        autocompLibro.clear();
        txtNombreLibro.clear();
        txtCategoria.clear();
    }

    public void limpiarTodo() {
        autocompCliente.clear();
        txtNombreApellido.clear();
        txtRegDni.clear();
        txtDireccion.clear();
        txtTelefono.clear();

        limpiarDatosLibro();

        FechaPrestamo.setValue(null);
        FechaRetorno.setValue(null);
        txtDescripcion.clear();
    }
}

