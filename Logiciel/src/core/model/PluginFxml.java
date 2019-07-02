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
    public Button activated = new Button("Lancer");

    @FXML
    public CheckBox deleted = new CheckBox();

    @FXML
    public Button edited = new Button("Ouvrir");

    @Usage(description = "Constructeur de l'objet pluginFxml avec un traitement par defaut pour rentré dans la tableview")
    public PluginFxml(File named){
        this.name = named;
        deleted.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("deleted");
                System.out.println(name.getName());
                name.delete();
            }
        });

        activated.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("activated");
                try {
                    //PluginManager.openJarUrl(name.toString());
                    PluginManager t = new PluginManager();
                    t.runJar2(named,"returnNewStage");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        edited.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    Desktop.getDesktop().open(new File(name.getParent()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    /*public String getNewPlugin() {
        return newPlugin.getName();
    }*/
    @Usage(description = "Recuperation du nom du plugin")
    public String getName() {
        return name.getName();
    }

    @Usage(description = "Récuperation du bouton d'activation")
    public Button getActivated() {
        return activated;
    }

    @Usage(description = "Récuperation de la checkbox de suppression")
    public CheckBox getDeleted() {
        return deleted;
    }

    @Usage(description = "Récuperation du bouton pour l'edition")
    public Button getEdited() {
        return edited;
    }
}
