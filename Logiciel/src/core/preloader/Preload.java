package core.preloader;

import annotation.AnnotatedClass;
import annotation.Status;
import annotation.Usage;
import javafx.application.Preloader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


@Status(author = "Bastien NISOLE",
        progression = 90,
        version = 2.5)
public class Preload extends Preloader implements AnnotatedClass {
    private Stage preloaderStage;

    @Override
    @Usage(description = "Affichage d'une fenetre en attendant que la l'index charge")
    public void start(Stage primaryStage) throws Exception {
        this.preloaderStage = primaryStage;
        VBox loading = new VBox(20);
        loading.getChildren().add(new ProgressBar());
        loading.getChildren().add(new Label("Please wait..."));
        BorderPane root = new BorderPane(loading);
        Scene scene = new Scene(root);
        primaryStage.setWidth(200);
        primaryStage.setHeight(200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    @Usage(description = "Handler du chargement de la page principale")
    public void handleStateChangeNotification(StateChangeNotification stateChangeNotification) {
        if (stateChangeNotification.getType() == StateChangeNotification.Type.BEFORE_START) {
            preloaderStage.hide();
        }
    }
}
