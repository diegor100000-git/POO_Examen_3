package pe.edu.upeu.sistemabiblioteca.controller;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import pe.edu.upeu.sistemabiblioteca.components.ColumnInfo;
import pe.edu.upeu.sistemabiblioteca.components.ComboBoxAutoComplete;
import pe.edu.upeu.sistemabiblioteca.components.TableViewHelper;
import pe.edu.upeu.sistemabiblioteca.components.Toast;
import pe.edu.upeu.sistemabiblioteca.dto.ComboBoxOption;
import pe.edu.upeu.sistemabiblioteca.modelo.Libro;
import pe.edu.upeu.sistemabiblioteca.service.ICategoriaService;
import pe.edu.upeu.sistemabiblioteca.service.ILibroService;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

@Controller
public class LibroController {
    @FXML
    private TextField txtNombre, txtCantidadEjemplares;

    @FXML
    private ComboBox<ComboBoxOption> cbxCategoria;

    @FXML
    private TableView<Libro> tableView;

    @FXML
    Label lbnMsg;

    @Autowired
    private ICategoriaService categoriaService;

    @Autowired
    private ILibroService libroService;

    private Stage stage;
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private Validator validator;

    ObservableList<Libro> listaLibros;
    Libro formulario;
    Long idLibroCE = 0L;

    private Integer parseIntegerSafe(String value) {
        if (value == null || value.trim().isEmpty()) return null;
        try {
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }
    public void listar() {
        try {
            tableView.getItems().clear();
            listaLibros = FXCollections.observableArrayList(libroService.findAll());
            tableView.getItems().addAll(listaLibros);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    public void initialize() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        cbxCategoria.getItems().addAll(categoriaService.listarCombobox());
        new ComboBoxAutoComplete<>(cbxCategoria);
        txtCantidadEjemplares.textProperty().addListener((obs, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                txtCantidadEjemplares.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        TableViewHelper<Libro> tableViewHelper = new TableViewHelper<>();
        LinkedHashMap<String, ColumnInfo> columns = new LinkedHashMap<>();

        columns.put("ID", new ColumnInfo("idLibro", 60.0));
        columns.put("Nombre", new ColumnInfo("nombre", 200.0));
        columns.put("Ejemplares", new ColumnInfo("cantidadEjemplares", 100.0));
        columns.put("Categoría", new ColumnInfo("categoria.nombre", 200.0));

        Consumer<Libro> updateAction = (Libro libro) -> editForm(libro);
        Consumer<Libro> deleteAction = (Libro libro) -> {
            libroService.delete(libro.getIdLibro());
            listar();
            Toast.showToast(new Stage(), "Libro eliminado", 2000, 400, 200);
        };
        tableViewHelper.addColumnsInOrderWithSize(tableView, columns, updateAction, deleteAction);
        listar();
    }

    public void clearForm() {
        txtNombre.clear();
        txtCantidadEjemplares.clear();
        cbxCategoria.getSelectionModel().clearSelection();

        idLibroCE = 0L;
    }

    public void editForm(Libro libro) {
        txtNombre.setText(libro.getNombre());
        txtCantidadEjemplares.setText(libro.getCantidadEjemplares().toString());

        cbxCategoria.getSelectionModel().select(
                cbxCategoria.getItems().stream()
                        .filter(opt -> Long.parseLong(opt.getKey()) == libro.getCategoria().getIdCategoria())
                        .findFirst()
                        .orElse(null)
        );
        idLibroCE = libro.getIdLibro();
    }

    private void mostrarErroresValidacion(List<ConstraintViolation<Libro>> violaciones) {

        // Mapa ordenado de campos con el mismo nombre que la entidad
        Map<String, Control> campos = new LinkedHashMap<>();
        campos.put("nombre", txtNombre);
        campos.put("cantidadEjemplares", txtCantidadEjemplares);
        campos.put("categoria", cbxCategoria);

        // Donde se guardará el primer error encontrado
        Control primerControlConError = null;
        String mensajeError = null;

        for (String campo : campos.keySet()) {
            var violacion = violaciones.stream()
                    .filter(v -> v.getPropertyPath().toString().equals(campo))
                    .findFirst();

            if (violacion.isPresent()) {
                mensajeError = violacion.get().getMessage();
                primerControlConError = campos.get(campo);
                break; // salimos al encontrar el primer error
            }
        }

        if (mensajeError != null) {
            lbnMsg.setText(mensajeError);
            lbnMsg.setStyle("-fx-text-fill: red; -fx-font-size: 16px;");

            Control finalPrimerControl = primerControlConError;
            if (finalPrimerControl != null) {
                Platform.runLater(finalPrimerControl::requestFocus);
            }
        }
    }

    private void procesarFormulario() {
        lbnMsg.setText("Formulario válido");
        lbnMsg.setStyle("-fx-text-fill: green; -fx-font-size: 16px;");

        double width = 400;
        double height = 200;

        if (idLibroCE > 0L) {
            formulario.setIdLibro(idLibroCE);
            libroService.update(formulario);
            Toast.showToast(stage, "Se actualizó el libro correctamente", 2000, width, height);
        } else {
            libroService.save(formulario);
            Toast.showToast(stage, "Se registró el libro correctamente", 2000, width, height);
        }
        clearForm();
        listar();
    }
    @FXML
    private void limpiarCampos() {
        txtNombre.clear();
        txtCantidadEjemplares.clear();
        cbxCategoria.getSelectionModel().clearSelection();
        txtCantidadEjemplares.clear();
    }

    @FXML
    public void validarFormulario() {
        formulario = new Libro();
        formulario.setNombre(txtNombre.getText());

        formulario.setCantidadEjemplares(parseIntegerSafe(txtCantidadEjemplares.getText()));
        if (formulario.getCantidadEjemplares() == null || formulario.getCantidadEjemplares() <= 0) {
            lbnMsg.setText("La cantidad de ejemplares debe ser mayor que 0.");
            lbnMsg.setStyle("-fx-text-fill: red; -fx-font-size: 16px;");
            txtCantidadEjemplares.requestFocus();
            return;
        }
        String idCat = cbxCategoria.getSelectionModel().getSelectedItem() == null
                ? "0" : cbxCategoria.getSelectionModel().getSelectedItem().getKey();
        formulario.setCategoria(idCat.equals("0") ? null : categoriaService.findById(Long.parseLong(idCat)));

        Set<ConstraintViolation<Libro>> violaciones = validator.validate(formulario);
        if (violaciones.isEmpty()) {
            procesarFormulario();
        } else {
            mostrarErroresValidacion(violaciones.stream().toList());
        }
    }
}