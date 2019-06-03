package core.Controller;

import core.Model.AuthService;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class ControllerProfil {

    public static Stage stage;

    @FXML
    public VBox profil;

    @FXML
    public Circle profilPicture;

    @FXML
    public TextField email;

    @FXML
    public TextField name;

    @FXML
    public TextField surname;

    @FXML
    public TextField birthdate;

    @FXML
    public PasswordField password;

    public static void setStage(Stage stagep) {
        stage = stagep;
    }

    @FXML
    public void initialize() {
        profilPicture.setFill(new ImagePattern(new Image ("assets/pictures/profile.jpg")));
        printOutput();

    }

    @FXML
    public void printOutput(){
        /*System.out.println(AuthService.getAuthUser().getNom());
        name.setText(AuthService.getUser().getNom());
        email.setText(AuthService.getUser().getEmail());
        surname.setText(AuthService.getUser().getPrenom());
        birthdate.setText(AuthService.getUser().getDate());
        password.setText(AuthService.getUser().getMdp());*/
    }

    @FXML
    public void openExplorer() throws IOException {
        //Desktop.getDesktop().open(new File("C:\\"));
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une nouvelle image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Images","*.jpg", "*.png","*.gif"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png"),
                new FileChooser.ExtensionFilter("GIF", "*.gif")
        );
        File selected = fileChooser.showOpenDialog(stage);
        if(selected != null){
            System.out.println(selected.toURI().toString());
            profil.getChildren().remove(0);
            //ImageView img = new ImageView();
            //Image img = new Image(selected.toURI().toString(),128,128,true,true);
           // profilPicture.setImage(img);
            //profilPicture.getChildren().add(1,img);
            Circle cir2 = new Circle(0,0,64);
            Image img = new Image(selected.toURI().toString());
            cir2.setFill(new ImagePattern(img));
            profil.getChildren().add(0,cir2);

        }
    }


    @FXML
    public void updatePwd() throws Exception{
        Desktop.getDesktop().browse(new URL("http://localhost:3000/user/123123156/reset").toURI());
    }

    @FXML
    public void close(){
        stage.close();
    }
}
