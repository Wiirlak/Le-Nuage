package core.Model;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import plugin.Plugin;
import plugin.PluginManager;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
//import plugin.NewPlugin;

public class PluginFxml {
    /*@FXML
    public NewPlugin newPlugin = new NewPlugin();*/

    public File name;

    @FXML
    public CheckBox activated = new CheckBox();

    @FXML
    public Button deleted = new Button("deleted");

    @FXML
    public Button edited = new Button("edited2");


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
                    PluginManager.openJarUrl(name.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        edited.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("edited");
            }
        });
    }


    /*public String getNewPlugin() {
        return newPlugin.getName();
    }*/

    public String getName() {
        return name.getName();
    }

    public CheckBox getActivated() {
        return activated;
    }

    public Button getDeleted() {
        return deleted;
    }

    public Button getEdited() {
        return edited;
    }
}
