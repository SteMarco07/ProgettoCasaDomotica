package stellino.marco.casajavafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class CasaJFXApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(CasaJFXApplication.class.getResource("CasaJFX-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 0, 0);
        stage.setTitle("Sistema Domotico");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}