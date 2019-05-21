package core.Model;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import plugin.NewPlugin;

public class PluginFxml {
    @FXML
    public NewPlugin newPlugin = new NewPlugin();

    @FXML
    public CheckBox activated = new CheckBox();

    @FXML
    public Button deleted = new Button("deleted");

    @FXML
    public Button edited = new Button("edited");

    public PluginFxml() {
        deleted.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("deleted");
                System.out.println(getNewPlugin());
            }
        });

        activated.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("activated");
            }
        });

        edited.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("edited");
            }
        });
    }

    public String getNewPlugin() {
        return newPlugin.getName();
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
