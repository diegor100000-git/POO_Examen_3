package pe.edu.upeu.sistemabiblioteca.components;

import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.layout.VBox;
import net.sf.jasperreports.engine.JasperPrint;
import win.zqxu.jrviewer.JRViewerFX;


public class ReportDialog {

    private JasperPrint jasperPrint;

    public ReportDialog(JasperPrint jasperPrint) {
        this.jasperPrint = jasperPrint;
    }

    public void show() {
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Visualizar Reporte");
        dialog.setHeaderText(null);

        // Crear el JRViewerFX
        JRViewerFX viewerFX = new JRViewerFX(jasperPrint);

        // Ajustar el tamaño del JRViewerFX
        viewerFX.setPrefSize(800, 400); // Ajusta estos valores según necesites

        // Crear un contenedor para el contenido y los botones
        VBox vbox = new VBox(viewerFX);
        vbox.setSpacing(10); // Espacio entre el JRViewerFX y el botón

        // Crear el botón de cierre
        Button closeButton = new Button("Cerrar");
        closeButton.setOnAction(e -> {
            System.out.println("Cerrar");
            dialog.close();
        }); // Acción para cerrar el diálogo

        // Agregar el botón al contenedor
        vbox.getChildren().add(closeButton);

        DialogPane dialogPane = new DialogPane();
        dialogPane.setContent(vbox); // Usar el VBox como contenido del DialogPane

        // Establecer tamaño mínimo y máximo del diálogo
        dialog.setResizable(true);
        dialog.getDialogPane().setContent(dialogPane);
        dialog.setHeight(400); // Establecer altura deseada
        dialog.setWidth(800);  // Establecer ancho deseado

        // Mostrar el diálogo
        //dialog.initStyle(StageStyle.UTILITY); // Opcional: para dar un estilo diferente
        dialog.showAndWait();
    }
}
