package cluelessProjectApp;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class cluelessApp extends Application{
    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/cluelessProjectApp/cluelessProject.fxml"));
        Scene scene = new Scene(loader.load());

        primaryStage.setTitle("Clueless app");
        primaryStage.setScene(scene);

        cluelessController controller = loader.getController();
        primaryStage.setOnHidden(e -> controller.shutdown());

     primaryStage.show();
    }
    
}
