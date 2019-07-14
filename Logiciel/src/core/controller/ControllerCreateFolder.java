package core.controller;


import annotation.AnnotatedClass;
import annotation.Status;
import annotation.Usage;
import core.http.entite.HttpEntite;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

@Status(author = "Bastien NISOLE",
        progression = 50,
        version = 2.3)
public class ControllerCreateFolder implements AnnotatedClass {

    public static Stage stage;

    public ControllerFile parent;

    @FXML
    public TextField folderName;

    public String folderRootId;

    public String folderRootName;

    @FXML
    public Label folderRootNameLabel;

    @Usage(description = "Constructeur")
    public ControllerCreateFolder(ControllerFile parent, String folderRootId,String folderRootName) {
        this.parent = parent;
        this.folderRootId = folderRootId;
        this.folderRootName = folderRootName;
    }

    @Usage(description = "Constructeur")
    public ControllerCreateFolder() {
    }


    @FXML
    @Usage(description = "Action faite à l'initialisation")
    public void initialize(){
        folderRootNameLabel.setText(folderRootName);
        addTextLimiter();
    }

    @Usage(description = "Affecter le stage courant")
    public static void setStage(Stage primaryStage){
        stage = primaryStage;
    }

    @Usage(description = "Affecter le stage courant")
    public void create(){
        if(!folderName.getText().equals("")){
            if(HttpEntite.createFolder(folderName.getText(),folderRootId)== 1){
                stage.close();
                parent.reload();
            }
        }
    }

    @Usage(description = "Limiter le texte de l'input")
    public void addTextLimiter() {
        folderName.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String oldValue, String newValue) {
                if (folderName.getText().length() > 20) {
                    String s = folderName.getText().substring(0, 20);
                    folderName.setText(s);
                }
            }
        });
    }
}
