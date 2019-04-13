package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import sample.Fmxl.ControllerIndex;

import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.io.File;



public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        FXMLLoader loader = new FXMLLoader(getClass().getResource("Fmxl/index.fxml"));
        Parent root = loader.load();
        ControllerIndex controllerIndex = loader.getController();
        controllerIndex.setStage(primaryStage);
        primaryStage.setTitle("Test JAVAFX");
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setWidth(900);
        primaryStage.setHeight(700);
        primaryStage.setMinWidth(900);
        primaryStage.setMinHeight(700);
        primaryStage.getIcons().add(new Image("/sample/LN.png"));
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        primaryStage.setMaxWidth(screenSize.getWidth());
        primaryStage.setMaxHeight(screenSize.getHeight());
        scene.getStylesheets().add("sample/stylesheet.css");
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
