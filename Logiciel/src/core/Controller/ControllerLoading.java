package core.Controller;

import annotation.AnnotatedClass;
import annotation.Status;
import annotation.Usage;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

@Status(author = "Bastien NISOLE",
        progression = 50,
        version = 2.3)
public class ControllerLoading implements AnnotatedClass {

    public static Stage stage;

    @Usage(description = "Affecter le stage courant")
    public static void setStage(Stage stagep) {
        stage = stagep;
    }

    @FXML
    @Usage(description = "Traitement a faire lors de l'initialisation de la page")
    public void initialize(){
        PauseTransition delay = new PauseTransition(Duration.seconds(2));
        delay.setOnFinished( event -> {
            try {
                login();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        delay.play();
    }
    @Usage(description = "Chargement et affichage de page principale")
    public void login() throws IOException {
        Parent root;
        root = FXMLLoader.load(getClass().getClassLoader().getResource("file.fxml"));
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("file.fxml"));
        ControllerFile controllerFile = loader.getController();

        //controllerFile.setData(data);
        controllerFile.setStage(stage);
        //data
        //System.out.println(data.user.getEmail());
        //data.user.setEmail(emailIndex.getText());
        //data.user.setMdp(passwordIndex.getText());
        //endData
        Scene scene = new Scene(root);
        stage.setResizable(true);
        stage.setHeight(800);
        stage.setMinHeight(800);
        stage.setMinWidth(900);
        stage.setTitle("Le-NuageModel");
        stage.setScene(scene);
        scene.getStylesheets().add("core/StyleSheet/stylesheet.css");
        stage.show();
    }
}
