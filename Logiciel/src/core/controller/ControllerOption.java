package core.controller;

import annotation.AnnotatedClass;
import annotation.Status;
import annotation.Usage;
import core.data.PluginData;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import plugin.PluginManager;

import java.io.IOException;

@Status(author = "Bastien NISOLE",
        progression = 50,
        version = 2.3)
public class ControllerOption implements AnnotatedClass {
    public static Stage stage;

    @Usage(description = "Affecter le stage courant")
    public static void setStage(Stage primaryStage){
        stage = primaryStage;
    }


    @FXML
    @Usage(description = "Affichage de la fenetre de plugin")
    public void openplugins() throws IOException {
        Stage subStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("plugin.fxml"));
        Scene scene = new Scene(loader.load());
        ControllerPlugin controllerPlugin = loader.getController();
        controllerPlugin.setStage(subStage);
        subStage.setResizable(false);
        subStage.setTitle(PluginData.nuageName + " - Plugins");
        subStage.getIcons().add(new Image("pictures/LNb.png"));
        subStage.setScene(scene);
        subStage.initOwner(stage);
        subStage.initModality(Modality.WINDOW_MODAL);
        scene.getStylesheets().add("core/stylesheet/stylesheet.css");
        subStage.show();
    }

    @Usage(description = "Quitter l'application")
    public void leave(){
        PluginManager a = new PluginManager();
        try {
            a.runSelectedJar("leave");
        } catch (Exception e) {
            e.printStackTrace();
        }
        Stage close = (Stage) stage.getOwner().getScene().getWindow();
        close.close();
    }
}
