package core.Model;

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
