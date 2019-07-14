package core.controller;

import annotation.AnnotatedClass;
import annotation.Status;
import annotation.Usage;
import core.http.nuage.HttpNuage;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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

    @Usage(description = "Constructeur")
    public ControllerCreateNuage(ControllerFile parent) {
        this.parent = parent;
    }

    @Usage(description = "Constructeur")
    public ControllerCreateNuage() {

    }

    @Usage(description = "Affecter le stage courant")
    public static void setStage(Stage primaryStage){
        stage = primaryStage;
    }


    @FXML
    @Usage(description = "Action faite à l'initialisation")
    public void initialize(){
        addTextLimiter();
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

    @Usage(description = "Limiter le texte de l'input")
    public void addTextLimiter() {
        nuageName.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String oldValue, String newValue) {
                if (nuageName.getText().length() > 20) {
                    String s = nuageName.getText().substring(0, 20);
                    nuageName.setText(s);
                }
            }
        });
    }

}
