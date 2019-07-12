package core.controller;

import annotation.AnnotatedClass;
import annotation.Status;
import annotation.Usage;
import core.http.nuage.HttpNuage;
import core.model.AuthService;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


@Status(author = "Bastien NISOLE",
        progression = 50,
        version = 2.3)
public class ControllerRename implements AnnotatedClass {



    public String oldNuageName;

    public String nuageId;

    @FXML
    public Label nuageName;

    @FXML
    public TextField newNuageName;


    public ControllerFile parent;


    public Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }


    public ControllerRename(String oldNuageName, String nuageId, ControllerFile parent) {
        this.oldNuageName = oldNuageName;
        this.nuageId = nuageId;
        this.parent = parent;
    }


    @Usage(description = "Traitement réaliser lors de l'initialisation")
    @FXML
    public void initialize(){
        nuageName.setText(oldNuageName);

        addTextLimiter();
    }


    @FXML
    public void save(){
        if(HttpNuage.rename(nuageId,newNuageName.getText()) == 1){

            if(AuthService.getNuage().getName().equals(oldNuageName)){

                AuthService.getNuage().setName(newNuageName.getText());
            }else{

            }
            stage.close();
            parent.reload();
        };
    }

    public void addTextLimiter() {
        newNuageName.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String oldValue, String newValue) {
                if (newNuageName.getText().length() > 20) {
                    String s = newNuageName.getText().substring(0, 20);
                    newNuageName.setText(s);
                }
            }
        });
    }
}
