package core.model;

import annotation.AnnotatedClass;
import annotation.Status;
import annotation.Usage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import plugin.PluginManager;

import java.awt.*;
import java.io.File;
import java.io.IOException;
//import plugin.NewPlugin;

@Status(author = "Bastien NISOLE",
        progression = 90,
        version = 2.5)
public class PluginFxml implements AnnotatedClass {
    /*@FXML
    public NewPlugin newPlugin = new NewPlugin();*/

    public File name;

    @FXML
    public Button launch = new Button("Lancer");


    @FXML
    public CheckBox activated = new CheckBox();

    @Usage(description = "Constructeur de l'objet pluginFxml avec un traitement par defaut pour rentré dans la tableview")
    public PluginFxml(File named,Boolean bool){
        this.name = named;
        launch.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    //PluginManager.openJarUrl(name.toString());
                    PluginManager t = new PluginManager();
                    t.runJar2(named,"returnNewStage");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        activated.setSelected(bool);
    }

    /*public String getNewPlugin() {
        return newPlugin.getName();
    }*/
    @Usage(description = "Recuperation du nom du plugin")
    public String getName() {
        return name.getName();
    }

    @Usage(description = "Récuperation du bouton d'activation")
    public Button getLaunch() {
        return launch;
    }

    @Usage(description = "Récuperation de la checkbox de l'activation")
    public CheckBox getActivated() {
        return activated;
    }
}
