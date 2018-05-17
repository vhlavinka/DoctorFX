package doctorfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import models.DBProps;


public class DoctorFX extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("DoctorFX.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();

        stage.setWidth(700);
        stage.setHeight(500);
        stage.setTitle("DoctorFX - " + DBProps.which);

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
