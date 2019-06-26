package core;

import annotation.AnnotatedClass;
import annotation.ParserAnnotations;
import annotation.Status;
import annotation.Usage;
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

    @Usage(description = "Lancement du projet")
    public static void main(String[] args) throws Exception {
        //launch(args);
        //System.out.println(FileSystemView.getFileSystemView().getDefaultDirectory().getPath());
        /*File folder =  new File(FileSystemView.getFileSystemView().getDefaultDirectory().getPath()+"/Lenuage/Plugin");
        folder.mkdirs();*/
        /*
        AuthService.getUser().setNom("slamai");
        System.out.println(AuthService.getUser().getNom());*/

        ParserAnnotations t =  new ParserAnnotations();
        LauncherImpl.launchApplication(Main.class, Preload.class, args);

        /*PluginManager a = new PluginManager();
        a.openJarFile(a.listPlugins[1]);*/
    }

    @Usage(description = "Récuperation du nom de la classe")
    public String getId() {
        return "main";
    }
}
