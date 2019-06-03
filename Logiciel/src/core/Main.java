package core;

import com.sun.javafx.application.LauncherImpl;
import core.Model.AuthService;
import core.preloader.Preload;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import core.Controller.ControllerIndex;
import plugin.PluginManager;

import java.awt.*;
import java.io.IOException;


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
    }

    public static void main(String[] args) throws Exception {
        //launch(args);
        //System.out.println(FileSystemView.getFileSystemView().getDefaultDirectory().getPath());
        /*File folder =  new File(FileSystemView.getFileSystemView().getDefaultDirectory().getPath()+"/Lenuage/Plugin");
        folder.mkdirs();*/
        /*
        AuthService.getUser().setNom("slamai");
        System.out.println(AuthService.getUser().getNom());*/
        LauncherImpl.launchApplication(Main.class, Preload.class, args);

        PluginManager a = new PluginManager();
        a.openJarFile(a.listPlugins[1]);
    }

    public String getId() {
        return "main";
    }
}
