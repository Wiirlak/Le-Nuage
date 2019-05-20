package core.Controller;

import core.Model.Data;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;

import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;
import core.Model.User;

public class ControllerSignUp {

    public static Stage stage;

    @FXML
    public TextField nom;
    @FXML
    public TextField prenom;
    @FXML
    public TextField email;
    @FXML
    public DatePicker date;
    @FXML
    public PasswordField mdp;
    @FXML
    public PasswordField mdpc;
    @FXML
    public CheckBox check;

    public static Data data;

    public static void setData(Data datap) {
        data = datap;
    }


    public static void setStage(Stage primaryStage){
        stage = primaryStage;
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
    public void printData(){
        int data = 0;

        if(nom.getText() == null || nom.getText().trim().isEmpty()){
            nom.getStyleClass().add("inputWrong");
            data++;
        }else{
            nom.getStyleClass().remove("inputWrong");
        }

        if(prenom.getText() == null || prenom.getText().trim().isEmpty()){
            prenom.getStyleClass().add("inputWrong");
            data++;
        }else{
            prenom.getStyleClass().remove("inputWrong");;
        }

        if(email.getText() == null || email.getText().trim().isEmpty()){
            email.getStyleClass().add("inputWrong");
            data++;
        }else{
            email.getStyleClass().remove("inputWrong");
        }

        if(mdp.getText() == null || mdp.getText().trim().isEmpty()){
            mdp.getStyleClass().add("inputWrong");
            data++;
        }else{
            mdp.getStyleClass().remove("inputWrong");
        }

        if(mdpc.getText() == null || mdpc.getText().trim().isEmpty()){
            mdpc.getStyleClass().add("inputWrong");
            data++;
        }else{
            mdpc.getStyleClass().remove("inputWrong");
        }

        if(date.getValue() == null  ){
            date.getStyleClass().add("inputWrong");
            data++;
        }else{
            date.getStyleClass().remove("inputWrong");
        }

        if(check.isSelected() == false){
            check.getStyleClass().add("chekbox");
            data++;
        }

        if( data == 0) {
            LocalDate dateL = date.getValue();
            User user = new User(nom.getText(), prenom.getText(), email.getText(), dateL.toString(), mdp.getText(), mdpc.getText(), check.isSelected());
            if (user.isValidEmail() == false) {
                email.getStyleClass().add("inputWrong");
            } else {
                email.getStyleClass().remove("inputWrong");
            }

            if (user.pwdEqual() == false){
                mdpc.getStyleClass().add("inputWrong");
            }else{
                mdpc.getStyleClass().remove("inputWrong");
            }

            System.out.println("Nom :" + user.getNom());
            System.out.println("Prenom :" + user.getPrenom());
            System.out.println("Email :" + user.isValidEmail());
            System.out.println("Date :" + user.getDate());
            System.out.println("Mot de passe :" + user.pwdEqual());
            System.out.println("CGU :" + user.getCheck());
        }
    }

    @FXML
    public void goBack() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("index.fxml"));
        Scene scene = new Scene(loader.load());
        ControllerIndex controllerIndex = loader.getController();
        controllerIndex.setStage(stage);
        controllerIndex.setData(data);
        stage.setResizable(true);
        stage.setTitle("Le-Nuage");
        stage.setScene(scene);
        scene.getStylesheets().add("core/StyleSheet/stylesheet.css");
        stage.show();
    }
}
