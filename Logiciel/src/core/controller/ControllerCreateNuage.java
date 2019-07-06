package core.controller;

import annotation.AnnotatedClass;
import annotation.Status;
import annotation.Usage;
import core.http.nuage.HttpNuage;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


@Status(author = "Bastien NISOLE",
        progression = 50,
        version = 2.3)
public class ControllerCreateNuage implements AnnotatedClass {

    public static Stage stage;

    public ControllerFile parent;

    @FXML
    public TextField nuageName;

    public ControllerCreateNuage(ControllerFile parent) {
        this.parent = parent;
    }

    @Usage(description = "Affecter le stage courant")
    public static void setStage(Stage primaryStage){
        stage = primaryStage;
    }

    @Usage(description = "Affecter le stage courant")
    public void create(){
        if(!nuageName.getText().equals("")){
            if(HttpNuage.createNuage(nuageName.getText())== 1){
                stage.close();
                parent.reload();
            }
        }
    }

}
