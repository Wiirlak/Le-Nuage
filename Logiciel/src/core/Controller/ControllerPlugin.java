package core.Controller;

import core.Model.PluginFxml;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import plugin.PluginManager;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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

    public PluginManager pluginManager = new PluginManager();

    @FXML
    public void initialize(){
        tvPlugin.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        pluginFxmls =  new ArrayList<PluginFxml>();
        for(File f : pluginManager.listPlugins){
            pluginFxmls.add(new PluginFxml(f));
        }
        tvPlugin.getItems().addAll(pluginFxmls);

        //pluginFxmls.get(2).getActivated().setSelected(true);
    }

    public void tickedNoTicked() throws IOException {
        if(checkAll.isSelected()){
            pluginFxmls.forEach(c -> c.deleted.setSelected(true));
            //pluginManager.openJarFiles();

        }else{
            pluginFxmls.forEach(c -> c.deleted.setSelected(false));
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
            Files.copy(selected.toPath(), Paths.get(FileSystemView.getFileSystemView().getDefaultDirectory().getPath() + "/Le-Nuage/plugins/"+selected.getName()));
            refresh();
        }
    }

    public void save(){
        exit();
    }

    public void exit(){
        stage.close();
    }

    public void refresh(){
        tvPlugin.getItems().clear();
        pluginManager.findAllJar(pluginManager.pluginPath);
        pluginFxmls =  new ArrayList<PluginFxml>();
        for(File f : pluginManager.listPlugins){
            pluginFxmls.add(new PluginFxml(f));
        }
        tvPlugin.getItems().addAll(pluginFxmls);
    }


    public void delete(){
        for( PluginFxml pgxml: pluginFxmls){
            if(pgxml.deleted.isSelected()){
                pgxml.name.delete();
            }
        }
        refresh();
        //pluginFxmls.forEach(c -> c.deleted.setSelected(true));
    }


}

