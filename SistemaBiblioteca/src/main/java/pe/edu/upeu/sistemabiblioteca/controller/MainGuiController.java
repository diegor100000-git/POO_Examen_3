package pe.edu.upeu.sistemabiblioteca.controller;


import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import pe.edu.upeu.sistemabiblioteca.dto.MenuItem;
import pe.edu.upeu.sistemabiblioteca.service.IMenuItemService;
import java.io.IOException;

@Controller
public class MainGuiController {

    @Autowired
    private ApplicationContext context;

    @Autowired
    private IMenuItemService menuItemService;

    @FXML
    private TabPane tabPaneFx;

    @FXML
    private BorderPane bp;

    @FXML
    private MenuBar menuBarFx;

    private Stage stage;

    @FXML
    public void initialize() {
        Platform.runLater(() -> stage = (Stage) tabPaneFx.getScene().getWindow());
        crearMenu();
        bp.setCenter(tabPaneFx);
    }

    private void crearMenu() {
        Menu menu = new Menu("Opciones");

        for (MenuItem itemData : menuItemService.listarMenu()) {
            javafx.scene.control.MenuItem itemFx =
                    new javafx.scene.control.MenuItem(itemData.getMenuitemnombre());

            itemFx.setId(itemData.getIdNombreObj());
            itemFx.setOnAction(e -> seleccionar(itemData));

            menu.getItems().add(itemFx);
        }

        menuBarFx.getMenus().add(menu);
        bp.setTop(menuBarFx);
    }

    private void seleccionar(MenuItem item) {
        try {
            if (item.getTipoTab().equals("S")) {
                redireccionar(item.getRutaFile());
            } else {
                abrirTab(item.getRutaFile(), item.getMenuitemnombre());
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private void abrirTab(String fxmlPath, String titulo) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        loader.setControllerFactory(context::getBean);

        Parent root = loader.load();

        Object controller = loader.getController();

        if (controller instanceof LibroController) {
            ((LibroController) controller).setStage(stage);
        }

        ScrollPane scrollPane = new ScrollPane(root);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        Tab newTab = new Tab(titulo, scrollPane);
        tabPaneFx.getTabs().clear();
        tabPaneFx.getTabs().add(newTab);
    }

    private void redireccionar(String fxmlPath) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        loader.setControllerFactory(context::getBean);

        Parent parent = loader.load();
        Scene scene = new Scene(parent);

        stage.setScene(scene);
        stage.setTitle("Sistema Biblioteca");
        stage.show();
    }
}
