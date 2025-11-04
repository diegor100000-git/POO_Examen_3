package pe.edu.upeu.sistemabiblioteca.controller;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import pe.edu.upeu.sistemabiblioteca.components.Toast;
import pe.edu.upeu.sistemabiblioteca.dto.PersonaDto;
import pe.edu.upeu.sistemabiblioteca.modelo.Cliente;
import pe.edu.upeu.sistemabiblioteca.service.IClienteService;
import pe.edu.upeu.sistemabiblioteca.utils.ConsultaDNI;

import java.util.Set;

@Controller
public class ClienteController {
    @FXML
    TextField txtDni,txtNombre,txtApellidos,txtDireccion,txtTelefono;

    @FXML
    TableView<Cliente> tableView;
    ObservableList<Cliente> clientes;
    TableColumn<Cliente, String> dniCol,nombreCol,apellidoCol,direccionCol,telefonoCol;
    TableColumn<Cliente, Void> opcionCol;

    @FXML
    private Label lblError;

    @Autowired
    IClienteService cs;

    private Validator validator;



    @FXML
    public void initialize(){
        definirNombresColumnas();
        listarClientes();
    }

    public void definirNombresColumnas(){
        dniCol = new TableColumn("DNI");
        apellidoCol = new TableColumn("Apellidos");
        apellidoCol.setMinWidth(180);
        nombreCol = new TableColumn("Nombres");
        nombreCol.setMinWidth(180);
        direccionCol = new TableColumn("Direccion");
        direccionCol.setMinWidth(180);
        telefonoCol = new TableColumn("Telefono");
        opcionCol = new TableColumn<>("opciones");
        opcionCol.setMinWidth(160);

        tableView.getColumns().addAll(dniCol, nombreCol, apellidoCol,direccionCol,telefonoCol,opcionCol);
    }
    public void agregarAccionBotones(){
        Callback<TableColumn<Cliente, Void>, TableCell<Cliente,Void >> cellFactory = param-> new TableCell<>() {
            Button btnEditar = new Button("Editar");
            Button btnEliminar = new Button("Eliminar");
            {
                btnEditar.setOnAction(event -> {
                    Cliente cliente = getTableView().getItems().get(getIndex());
                    editarCliente(cliente);
                });

                btnEliminar.setOnAction(event -> {
                    Cliente cliente = getTableView().getItems().get(getIndex());
                    eliminarCliente(cliente.getDni());
                });
            }
            @Override
            public void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                }else{
                    HBox hBox = new HBox(btnEditar,btnEliminar);
                    hBox.setSpacing(10);
                    setGraphic(hBox);
                }
            }
        };
        opcionCol.setCellFactory(cellFactory);
    }

    public void listarClientes(){

        dniCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDni()));
        nombreCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombres()));
        apellidoCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getApellido()));
        direccionCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDireccion()));
        telefonoCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTelefono()));
        agregarAccionBotones();
        clientes= FXCollections.observableArrayList(cs.findAll());
        tableView.setItems(clientes);
    }

    @FXML
    public void buscarPorDni(){
            String dni = txtDni.getText();
            if (dni == null || dni.isEmpty()) {
                return;
            }
            ConsultaDNI consultaDNI = new ConsultaDNI();
            PersonaDto personaDto = consultaDNI.consultarDNI(dni);
            if (personaDto != null && personaDto.getNombre() != null) {
                txtNombre.setText(personaDto.getNombre());
                txtApellidos.setText(personaDto.getApellidoPaterno() + " " + personaDto.getApellidoMaterno());
            } else {
                txtNombre.clear();
                txtApellidos.clear();
            }
    }

    @FXML
    public void crearCliente(){

        lblError.setText("");

        Cliente cliente = new Cliente();
        cliente.setDni(txtDni.getText());
        cliente.setNombres(txtNombre.getText());
        cliente.setApellido(txtApellidos.getText());
        cliente.setDireccion(txtDireccion.getText());
        cliente.setTelefono(txtTelefono.getText());

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Cliente>> validaciones = validator.validate(cliente);

        if (!validaciones.isEmpty()) {
            String mensaje = validaciones.iterator().next().getMessage();
            lblError.setText(mensaje);
            return;
        }

        Stage stage = (Stage) tableView.getScene().getWindow();

        if (cs.findByDni(cliente.getDni()) == null) {
            cs.save(cliente);
            Toast.showToast(stage, "✅ Cliente registrado correctamente", 2500,
                    stage.getX() + stage.getWidth() - 350,
                    stage.getY() + stage.getHeight() - 100);
        } else {
            cs.update(cliente.getDni(), cliente);
            Toast.showToast(stage, "📝 Cliente actualizado correctamente", 2500,
                    stage.getX() + stage.getWidth() - 350,
                    stage.getY() + stage.getHeight() - 100);
        }
        limpiarFormulario();
        listarClientes();
    }
    public void limpiarFormulario(){
        txtDni.clear();
        txtNombre.clear();
        txtApellidos.clear();
        txtDireccion.clear();
        txtTelefono.clear();
    }
    public void editarCliente(Cliente c){
        txtDni.setText(c.getDni());
        txtNombre.setText(c.getNombres());
        txtApellidos.setText(c.getApellido());
        txtDireccion.setText(c.getDireccion());
        txtTelefono.setText(c.getTelefono());
    }

    public void eliminarCliente(String dni) {
        cs.deleteById(dni);
        listarClientes();
    }

    public void cancelar(){
        limpiarFormulario();
    }
}
