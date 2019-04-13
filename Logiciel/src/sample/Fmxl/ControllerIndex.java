package sample.Fmxl;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.Main;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerIndex {
    public Stage stage;

    public void setStage(Stage primaryStage){
        this.stage = primaryStage;
    }

    @FXML
    private void log(){
        System.out.println("Logger");
    }

    @FXML
    private void signUp() throws IOException {
        System.out.println("Sign in");
        loadSecondFxml();

    }
    @FXML
    public void loadSecondFxml()throws  IOException{
        //Load new FXML and assign it to scene
        Parent root;
        root = FXMLLoader.load(getClass().getResource("signUp.fxml"));
        Scene scene = new Scene(root, 900, 700);
        stage.setTitle("Inscription");
        stage.setScene(scene);
        scene.getStylesheets().add("sample/stylesheet.css");
        stage.show();
    }

    @FXML
    public void openFB() throws Exception{
        Desktop.getDesktop().browse(new URL("http://quedescodes.blogspot.com/2016/04/java-ouvrir-un-lien-dans-le-navigateur.html").toURI());
    }

    @FXML
    public void openTW() throws Exception{
        Desktop.getDesktop().browse(new URL("https://stackoverflow.com/questions/23032253/how-to-change-the-current-scene-to-another-in-javafx").toURI());
    }

    @FXML
    public void login() throws IOException{
        Parent root;
        root = FXMLLoader.load(getClass().getResource("file.fxml"));
        Scene scene = new Scene(root);
        stage.setResizable(true);
        stage.setTitle("Le-Nuage");
        stage.setScene(scene);
        scene.getStylesheets().add("sample/stylesheet.css");
        stage.hide();
        stage.show();
    }

}
