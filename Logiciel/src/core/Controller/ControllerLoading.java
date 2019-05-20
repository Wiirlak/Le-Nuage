package core.Controller;

import core.Model.Data;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class ControllerLoading {

    public static Stage stage;

    public static void setStage(Stage stagep) {
        stage = stagep;
    }

    public static Data data;

    public static void setData(Data datap) {
        data = datap;
    }

    @FXML
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
        stage.setTitle("Le-Nuage");
        stage.setScene(scene);
        scene.getStylesheets().add("core/StyleSheet/stylesheet.css");
        stage.show();
    }
}
