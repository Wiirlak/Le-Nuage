package core;

import annotation.AnnotatedClass;
import annotation.ParserAnnotations;
import annotation.Status;
import annotation.Usage;
import com.sun.javafx.application.LauncherImpl;
import core.controller.CliMenu;
import core.data.PluginData;
import core.preloader.Preload;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import core.controller.ControllerIndex;
import plugin.PluginManager;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import javax.sound.sampled.*;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

@Status(author = "Bastien NISOLE",
        progression = 90,
        version = 2.5)
public class Main extends Application implements AnnotatedClass {
    @Override
    @Usage(description = "Affichage de l'index")
    public void start(Stage primaryStage) throws Exception{

        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("index.fxml"));
        Parent root = loader.load();
        ControllerIndex controllerIndex = loader.getController();
        controllerIndex.setStage(primaryStage);
        primaryStage.setTitle(PluginData.nuageName);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.toFront();
        primaryStage.setResizable(false);
        primaryStage.setWidth(900);
        primaryStage.setHeight(700);
        primaryStage.getIcons().add(new Image("pictures/LNb.png"));
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        primaryStage.setMaxWidth(screenSize.getWidth());
        primaryStage.setMaxHeight(screenSize.getHeight());
        scene.getStylesheets().add("core/stylesheet/stylesheet.css");
        primaryStage.show();
    }

    @Usage(description = "Lancement du projet")
    public static void main(String[] args) throws Exception {
        if (args.length != 0){
                new CliMenu(args);
        }else {
            //launch(args);
            //System.out.println(FileSystemView.getFileSystemView().getDefaultDirectory().getPath());
            /*File folder =  new File(FileSystemView.getFileSystemView().getDefaultDirectory().getPath()+"/Lenuage/Plugin");
            folder.mkdirs();*/
            /*
            AuthService.getUser().setNom("slamai");
            System.out.println(AuthService.getUser().getNom());*/

            ParserAnnotations t = new ParserAnnotations();

            PluginManager a = new PluginManager();
            a.runSelectedJar("lnOpen");
            a.runSelectedJar("returnNuageName");

            LauncherImpl.launchApplication(Main.class, Preload.class, args);
        }
    }
}
