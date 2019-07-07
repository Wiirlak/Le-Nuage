package core.controller;

import annotation.AnnotatedClass;
import annotation.Status;
import annotation.Usage;
import core.data.PluginData;
import core.http.auth.HttpAuth;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import plugin.PluginManager;

import java.awt.*;
import java.io.IOException;
import java.net.URL;

@Status(author = "Bastien NISOLE",
        progression = 50,
        version = 2.3)
public class ControllerIndex implements AnnotatedClass {

    public Stage stage;
    @FXML
    public TextField emailIndex;

    @FXML
    public PasswordField passwordIndex;

    @FXML
    public Label errorNetwork;

    @FXML
    public Label errorLogin;

    @Usage(description = "Affecter le stage courant")
    public void setStage(Stage primaryStage){
        this.stage = primaryStage;
    }

    @FXML
    @Usage(description = "Fonction de log")
    private void log(){
        System.out.println("Logger");
    }

    @FXML
    @Usage(description = "Afficher la page d'inscription")
    private void signUp() throws IOException {
        //System.out.println("Sign in");
        loadSecondFxml();

    }

    @FXML
    @Usage(description = "Chargement et affichage de la page d'inscription")
    public void loadSecondFxml()throws  IOException{
        //Load new FXML and assign it to scene
        Parent root;
        root = FXMLLoader.load(getClass().getClassLoader().getResource("signUp.fxml"));
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("signUp.fxml"));
        ControllerSignUp controllerSignUp = loader.getController();
        controllerSignUp.setStage(stage);
        Scene scene = new Scene(root, 900, 700);
        stage.setTitle(PluginData.nuageName +" - Inscription");
        stage.setScene(scene);
        scene.getStylesheets().add("core/stylesheet/stylesheet.css");
        stage.show();
    }

    @FXML
    @Usage(description = "Ouvrir le navigateur avec l'url de facebook")
    public void openFB() throws Exception{
        Desktop.getDesktop().browse(new URL("http://quedescodes.blogspot.com/2016/04/java-ouvrir-un-lien-dans-le-navigateur.html").toURI());
    }

    @FXML
    @Usage(description = "Ouvrir le navigateur avec l'url de twitter")
    public void openTW() throws Exception{
        Desktop.getDesktop().browse(new URL("https://stackoverflow.com/questions/23032253/how-to-change-the-current-scene-to-another-in-javafx").toURI());
    }


    @FXML
    @Usage(description = "Affichage de la fenetre de chargement")
    public void loading() throws IOException, InterruptedException {
        errorLogin.setVisible(false);
        int acces = HttpAuth.login(emailIndex.getText(),passwordIndex.getText());
        if( acces == 1) {
            Parent root;
            root = FXMLLoader.load(getClass().getClassLoader().getResource("Loading.fxml"));
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("Loading.fxml"));
            ControllerLoading controllerLoading = loader.getController();
            controllerLoading.setStage(stage);
            Scene scene = new Scene(root);
            stage.setResizable(true);
            stage.setTitle(PluginData.nuageName);

            stage.setScene(scene);
            scene.getStylesheets().add("core/stylesheet/stylesheet.css");
            stage.show();
        }else if(acces == 0){
            errorLogin.setVisible(true);
            errorLogin.setText("Identifiants de connexion incorrects.");
            //System.out.println("Mauvais identifiants");
        }else{
            errorLogin.setVisible(true);
            errorLogin.setText("Impossible d'accéder à l'api. ");
            //System.out.println("Impossible d'acceder à l'api");
        }

    }

}
