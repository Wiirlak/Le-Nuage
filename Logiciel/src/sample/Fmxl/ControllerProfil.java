package sample.Fmxl;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ControllerProfil {

    public static Stage stage;

    @FXML
    public VBox profilPicture;

    public static void setStage(Stage stage) {
        ControllerProfil.stage = stage;
    }

    @FXML
    public void openExplorer() throws IOException {
        //Desktop.getDesktop().open(new File("C:\\"));
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une nouvelle image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Images", "*.*","*.jpg", "*.png","*.gif"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png"),
                new FileChooser.ExtensionFilter("GIF", "*.gif")
        );
        File selected = fileChooser.showOpenDialog(stage);
        if(selected != null){
            System.out.println(selected.toURI().toString());
            ImageView img = new ImageView();
            img.setImage(new Image(selected.toURI().toString(),128,128,true,true));
            profilPicture.getChildren().add(1,img);

        }
    }
}
