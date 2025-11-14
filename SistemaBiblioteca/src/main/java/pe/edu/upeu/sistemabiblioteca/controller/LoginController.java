package pe.edu.upeu.sistemabiblioteca.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import pe.edu.upeu.sistemabiblioteca.modelo.Usuario;
import pe.edu.upeu.sistemabiblioteca.service.IUsuarioService;

@Controller
public class LoginController {
    @Autowired
    private ApplicationContext context;
    @Autowired
    IUsuarioService usuarioService;
    @FXML
    TextField txtUsuario;
    @FXML
    PasswordField txtClave;
    @FXML
    Button btnIngresar;

    @FXML
    public void cerrar(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
        Platform.exit();
        System.exit(0);
    }

    @FXML
    public void login(ActionEvent event) {
        try {
            Usuario usuario = usuarioService.loginUsuario(txtUsuario.getText(), txtClave.getText());
            if (usuario != null) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/maingui.fxml"));
                loader.setControllerFactory(context::getBean);
                Parent mainRoot = loader.load();

                Screen screen = Screen.getPrimary();
                Rectangle2D bounds = screen.getBounds();

                Scene mainScene = new Scene(mainRoot, bounds.getWidth(), bounds.getHeight() - 30);

                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(mainScene);
                stage.setTitle("Sistema Biblioteca");
                stage.setResizable(true);
                stage.setWidth(bounds.getWidth());
                stage.setHeight(bounds.getHeight());
                stage.show();
            }
        } catch (Exception e) {
            System.out.println("Error al iniciar sesión: " + e.getMessage());
        }
    }
}

