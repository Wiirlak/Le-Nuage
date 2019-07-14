package core.controller;

import annotation.AnnotatedClass;
import annotation.Status;
import annotation.Usage;
import core.http.profil.HttpProfil;
import core.http.profil.Profil;
import javafx.fxml.FXML;
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


@Status(author = "Bastien NISOLE",
        progression = 50,
        version = 2.3)
public class ControllerProfil  implements AnnotatedClass {

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

    @Usage(description = "Affecter le stage courant")
    public static void setStage(Stage stagep) {
        stage = stagep;
    }

    @FXML
    @Usage(description = "Actions faitre slors du chargement de la page")
    public void initialize() {
        profilPicture.setFill(new ImagePattern(new Image ("assets/pictures/profile.jpg")));
        printOutput();

    }

    @FXML
    @Usage(description = "Affichage des informations de l'utilisateur")
    public void printOutput(){
        try {
            Profil response = HttpProfil.getProfil();
            name.setText(response.getName());
            surname.setText(response.getFirstname());
            email.setText(response.getEmail());
            birthdate.setText(response.getDate().split("T")[0]);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    @Usage(description = "Ouvre l'exploreur de fichier pour selectionner une photo")
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
            profil.getChildren().remove(0);
            Circle cir2 = new Circle(0,0,64);
            Image img = new Image(selected.toURI().toString());
            cir2.setFill(new ImagePattern(img));
            profil.getChildren().add(0,cir2);

        }
    }


    @FXML
    @Usage(description = "Ouvrir le navigateur vers la page pour ranger de mot de passe")
    public void updatePwd() throws Exception{
        Profil response = HttpProfil.getProfil();
        Desktop.getDesktop().browse(new URL("http://localhost:3000/user/"+response.get_id()+"/reset").toURI());
    }

    @FXML
    @Usage(description = "Fermeture de la fenettre et sauvegarde")
    public void close() throws IOException {
        Profil response = HttpProfil.getProfil();
        if(!email.getText().equals(response.getEmail())){
            if (HttpProfil.updateProfilEmail(response.get_id(),email.getText()) == 1 ){
                stage.close();
            }
        }

    }
}
