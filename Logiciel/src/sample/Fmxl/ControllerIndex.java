package sample.Fmxl;

import javafx.animation.PauseTransition;
import javafx.application.Preloader;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.util.Duration;
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("signUp.fxml"));
        ControllerSignUp controllerSignUp = loader.getController();
        controllerSignUp.setStage(stage);
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("file.fxml"));
        ControllerFile controllerFile = loader.getController();
        controllerFile.setStage(stage);
        Scene scene = new Scene(root);
        stage.setResizable(true);
        stage.setTitle("Le-Nuage");
        stage.setScene(scene);
        scene.getStylesheets().add("sample/stylesheet.css");
        stage.show();

    }


    @FXML
    public void loading() throws IOException, InterruptedException {
        Parent root;
        root = FXMLLoader.load(getClass().getResource("Loading.fxml"));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Loading.fxml"));
        ControllerLoading controllerLoading = loader.getController();
        controllerLoading.setStage(stage);
        Scene scene = new Scene(root);
        stage.setResizable(true);
        stage.setTitle("Le-Nuage");
        stage.setScene(scene);
        scene.getStylesheets().add("sample/stylesheet.css");
        stage.show();
        PauseTransition delay = new PauseTransition(Duration.seconds(2));
        delay.setOnFinished( event -> {
            try {
                login();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        delay.play();
    }



}
