package core.Controller;


import annotation.AnnotatedClass;
import annotation.Status;
import annotation.Usage;
import core.Model.PluginFxml;
import core.Model.SynchroFxml;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import plugin.PluginManager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

@Status(author = "Bastien NISOLE",
        progression = 50,
        version = 2.3)
public class ControllerSynchro  implements AnnotatedClass {

    public static Stage stage;

    public ArrayList<SynchroFxml> synchroFxml;


    @FXML
    public TableView files;

    @FXML
    public CheckBox checkAll;

    @Usage(description = "Affecter le stage courant")
    public static void setStage(Stage primaryStage){
        stage = primaryStage;
    }


    @FXML
    @Usage(description = "Traitement réaliser lors de l'initialisation")
    public void initialize(){
        files.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        synchroFxml =  new ArrayList<SynchroFxml>();
        for( int i = 0 ; i < 50; i ++)
            synchroFxml.add(new SynchroFxml());
        /*
        - Recuperer tous les fichiers présent dans le dossier local
        - Récuperer tous les fichiers présent dans le dossier distant du nuage
        - Ne conserver que ceux existant dans les 2 tableau
        */

        files.getItems().addAll(synchroFxml);

        //pluginFxmls.get(2).getActivated().setSelected(true);
    }

    @Usage(description = "Tous cocher ou tous décocher")
    public void tickedNoTicked() throws IOException {
        if(checkAll.isSelected()){
            synchroFxml.forEach(c -> c.selected.setSelected(true));
            //pluginManager.openJarFiles();

        }else{
            synchroFxml.forEach(c -> c.selected.setSelected(false));
        }
    }

    @Usage(description = "Synchronise tous les fichiers sélectionnés")
    public void synchro(){
        /*
            - Recuperer toutes lignes sélectionnées
            - Pour chaque fichier
                - Comparer le contenu distant et local
                - Comparer la date de modification distante et locale
                - Si tout est identique
                    - Ne rien faire
                - Sinon
                    - Télécharger la nouvelle version du fichier sur le fichier de l'utilisateur (écrasement)
            - Fermer la fênetre

            ******
            Amélioration :
            N'afficher que les fichier different dès le début

        */


        leave();
    }

    @Usage(description = "Quitter l'application")
    public void leave(){
        stage.close();
    }
}
