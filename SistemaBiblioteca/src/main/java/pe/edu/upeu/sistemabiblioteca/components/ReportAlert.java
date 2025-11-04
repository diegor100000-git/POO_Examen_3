package pe.edu.upeu.sistemabiblioteca.components;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.StackPane;
import net.sf.jasperreports.engine.JasperPrint;
import win.zqxu.jrviewer.JRViewerFX;


public class ReportAlert {

    private JasperPrint jasperPrint;

    public ReportAlert(JasperPrint jasperPrint) {
        this.jasperPrint = jasperPrint;
    }

    public void show() {
        Alert alert = new Alert(Alert.AlertType.NONE); // Tipo NONE para no mostrar botones predeterminados
        alert.setTitle("Visualizar Reporte");
        alert.setHeaderText(null);

        // Crear el JRViewerFX
        JRViewerFX viewerFX = new JRViewerFX(jasperPrint);
        viewerFX.setPrefSize(800, 400); // Ajusta estos valores según necesites

        // Crear un contenedor para el JRViewerFX
        StackPane stackPane = new StackPane(viewerFX);
        alert.getDialogPane().setContent(stackPane); // Establecer contenido del Alert

        // Agregar botón de cerrar
        ButtonType closeButton = new ButtonType("Cerrar");
        alert.getButtonTypes().add(closeButton);

        // Mostrar el alert
        alert.setOnCloseRequest(event -> alert.close()); // Manejar el cierre
        alert.showAndWait().ifPresent(response -> {
            if (response == closeButton) {
                alert.close(); // Cerrar el alert al hacer clic en el botón "Cerrar"
            }
        });
    }
}
