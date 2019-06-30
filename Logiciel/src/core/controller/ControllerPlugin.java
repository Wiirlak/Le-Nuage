package core.controller;

import annotation.AnnotatedClass;
import annotation.Status;
import annotation.Usage;
import core.model.PluginFxml;
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

@Status(author = "Bastien NISOLE",
        progression = 50,
        version = 2.3)
public class ControllerPlugin  implements AnnotatedClass {

    public static Stage stage;

    @FXML
    public TableView tvPlugin;

    @FXML
    public CheckBox checkAll;

    public ArrayList<PluginFxml> pluginFxmls;

    public PluginManager pluginManager = new PluginManager();

    @Usage(description = "Affecter le stage courant")
    public static void setStage(Stage primaryStage){
        stage = primaryStage;
    }


    @FXML
    @Usage(description = "Traitement réaliser lors de l'initialisation")
    public void initialize(){
        tvPlugin.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        pluginFxmls =  new ArrayList<PluginFxml>();
        for(File f : pluginManager.listPlugins){
            pluginFxmls.add(new PluginFxml(f));
        }
        tvPlugin.getItems().addAll(pluginFxmls);

        //pluginFxmls.get(2).getActivated().setSelected(true);
    }

    @Usage(description = "Tous cocher ou tous décocher")
    public void tickedNoTicked() throws IOException {
        if(checkAll.isSelected()){
            pluginFxmls.forEach(c -> c.deleted.setSelected(true));
            //pluginManager.openJarFiles();

        }else{
            pluginFxmls.forEach(c -> c.deleted.setSelected(false));
        }
    }

    @FXML
    @Usage(description = "Ouverture de l'explorateur de fichier")
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

    @Usage(description = "Fermer la fenetre")
    public void save(){
        exit();
    }

    @Usage(description = "Quitter l'application")
    public void exit(){
        stage.close();
    }

    @Usage(description = "Raffraichir la liste de plugin")
    public void refresh(){
        tvPlugin.getItems().clear();
        pluginManager.findAllJar(pluginManager.pluginPath);
        pluginFxmls =  new ArrayList<PluginFxml>();
        for(File f : pluginManager.listPlugins){
            pluginFxmls.add(new PluginFxml(f));
        }
        tvPlugin.getItems().addAll(pluginFxmls);
    }

    @Usage(description = "Suppression d'un plugin")
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

