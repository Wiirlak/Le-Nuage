package core;

import com.sun.javafx.application.LauncherImpl;
import core.Model.Data;
import core.preloader.Preload;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import core.Controller.ControllerIndex;
import plugin.PluginManager;

import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.io.IOException;
import java.io.File;


public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{

        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("index.fxml"));
        Parent root = loader.load();
        ControllerIndex controllerIndex = loader.getController();
        controllerIndex.setStage(primaryStage);
        primaryStage.setTitle("Test JAVAFX");
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.toFront();
        primaryStage.setResizable(false);
        primaryStage.setWidth(900);
        primaryStage.setHeight(700);
        primaryStage.getIcons().add(new Image("pictures/LN.png"));
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        primaryStage.setMaxWidth(screenSize.getWidth());
        primaryStage.setMaxHeight(screenSize.getHeight());
        scene.getStylesheets().add("core/StyleSheet/stylesheet.css");
        primaryStage.show();
        controllerIndex.setData(new Data());
    }

    public static void main(String[] args) throws IOException {
        PluginManager a = new PluginManager();
        System.out.println(a.mydoc);

        a.openJarFile(a.listPlugins[1]);
        //launch(args);
        //System.out.println(FileSystemView.getFileSystemView().getDefaultDirectory().getPath());
        File folder =  new File(FileSystemView.getFileSystemView().getDefaultDirectory().getPath()+"/Lenuage/Plugin");
        folder.mkdirs();
        LauncherImpl.launchApplication(Main.class, Preload.class, args);
    }

    public String getId() {
        return "main";
    }
}
