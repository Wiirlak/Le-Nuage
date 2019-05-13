package core.Controller;

import core.Model.PluginFxml;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class ControllerPlugin {

    public static Stage stage;

    public static void setStage(Stage primaryStage){
        stage = primaryStage;
    }

    @FXML
    public TableView tvPlugin;


    @FXML
    public void initialize(){
        PluginFxml pluginFxml =  new PluginFxml();
        PluginFxml pluginFxml2 =  new PluginFxml();
        PluginFxml pluginFxml3 =  new PluginFxml();

        tvPlugin.getItems().add(pluginFxml);
        tvPlugin.getItems().add(pluginFxml2);
        tvPlugin.getItems().add(pluginFxml3);

        pluginFxml2.getActivated().setSelected(true);
    }
}
