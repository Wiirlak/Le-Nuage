package core.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class ControllerOption {
    public static Stage stage;

    public static void setStage(Stage primaryStage){
        stage = primaryStage;
    }


    @FXML
    public void openplugins() throws IOException {
        Stage subStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("plugin.fxml"));
        Scene scene = new Scene(loader.load());
        ControllerPlugin controllerPlugin = loader.getController();
        controllerPlugin.setStage(subStage);
        subStage.setResizable(false);
        subStage.setTitle("Plugins");
        subStage.setScene(scene);
        subStage.initOwner(stage);
        subStage.initModality(Modality.WINDOW_MODAL);
        scene.getStylesheets().add("core/StyleSheet/stylesheet.css");
        subStage.show();
    }
}
