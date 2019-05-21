package core.Controller;

import core.Model.Data;
import core.Model.PluginFxml;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ControllerPlugin {

    public static Stage stage;

    public static void setStage(Stage primaryStage){
        stage = primaryStage;
    }

    @FXML
    public TableView tvPlugin;

    @FXML
    public CheckBox checkAll;

    public ArrayList<PluginFxml> pluginFxmls;

    public static Data data;

    public  static void setData(Data datap) {
        data = datap;
    }

    @FXML
    public void initialize(){
        tvPlugin.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        pluginFxmls = new ArrayList<>();
        for(int i = 0 ; i< 50; i ++){
            pluginFxmls.add(new PluginFxml());
        }

        tvPlugin.getItems().addAll(pluginFxmls);

        pluginFxmls.get(2).getActivated().setSelected(true);
    }

    public void tickedNoTicked(){
        if(checkAll.isSelected()){
            pluginFxmls.forEach(c -> c.activated.setSelected(true));
        }else{
            pluginFxmls.forEach(c -> c.activated.setSelected(false));
        }
    }

    @FXML
    public void openExplorer() throws IOException {
        //Desktop.getDesktop().open(new File("C:\\"));
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Sélectionner un fichier à ajouter");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JAR", "*.jar")
        );
        File selected = fileChooser.showOpenDialog(stage);
        if(selected != null){
            System.out.println(selected.toURI().toString());
        }
    }

    public void save(){
        exit();
    }

    public void exit(){
        stage.close();
    }
}

