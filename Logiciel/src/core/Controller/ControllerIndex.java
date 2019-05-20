package core.Controller;

import core.Model.Data;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.*;
import java.io.IOException;
import java.net.URL;

public class ControllerIndex {
    public Stage stage;

    public void setStage(Stage primaryStage){
        this.stage = primaryStage;
    }


    public Data data;

    public void setData(Data datap) {
        data = datap;
    }

    @FXML
    public TextField emailIndex;

    @FXML
    public PasswordField passwordIndex;

    @FXML
    private void log(){
        System.out.println("Logger");
    }

    @FXML
    private void signUp() throws IOException {
        //System.out.println("Sign in");
        loadSecondFxml();

    }
    @FXML
    public void loadSecondFxml()throws  IOException{
        //Load new FXML and assign it to scene
        Parent root;
        root = FXMLLoader.load(getClass().getClassLoader().getResource("signUp.fxml"));
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("signUp.fxml"));
        ControllerSignUp controllerSignUp = loader.getController();
        controllerSignUp.setStage(stage);
        controllerSignUp.setData(data);
        Scene scene = new Scene(root, 900, 700);
        stage.setTitle("Inscription");
        stage.setScene(scene);
        scene.getStylesheets().add("core/StyleSheet/stylesheet.css");
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
    public void loading() throws IOException, InterruptedException {
        Parent root;
        root = FXMLLoader.load(getClass().getClassLoader().getResource("Loading.fxml"));
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("Loading.fxml"));
        ControllerLoading controllerLoading = loader.getController();
        controllerLoading.setStage(stage);
        controllerLoading.setData(data);
        Scene scene = new Scene(root);
        stage.setResizable(true);
        stage.setTitle("Le-Nuage");
        stage.setScene(scene);
        scene.getStylesheets().add("core/StyleSheet/stylesheet.css");
        stage.show();

    }

    public boolean canConnect(){

        return true;
    }


}
