package pe.edu.upeu.sistemabiblioteca;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SistemaBibliotecaApplication extends Application {

	private ConfigurableApplicationContext applicationContext;
	private Parent root;


	public static void main(String[] args) {

		launch(args);
	}

	@Override
	public void init() throws Exception {
		SpringApplicationBuilder builder = new SpringApplicationBuilder(SistemaBibliotecaApplication.class);
		builder.application().setWebApplicationType(WebApplicationType.NONE);
		applicationContext=builder.run(getParameters().getRaw().toArray(new String[0]));

		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/login.fxml"));
		loader.setControllerFactory(applicationContext::getBean);
		root = loader.load();
	}

	@Override
	public void start(Stage stage) throws Exception {
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.setTitle("SistemaBiblioteca 1");
		stage.show();
	}
}
