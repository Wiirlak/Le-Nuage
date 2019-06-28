package core.Controller;


import annotation.AnnotatedClass;
import annotation.Status;
import annotation.Usage;
import core.Model.PluginFxml;
import core.Model.SynchroFxml;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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

    @FXML
    public TableView files;

    @FXML
    public CheckBox checkAll;


    @FXML
    public TextField recherche;

    @FXML
    public ObservableList<SynchroFxml> masterData = FXCollections.observableArrayList();

    public SortedList<SynchroFxml> sortedData;

    @Usage(description = "Affecter le stage courant")
    public static void setStage(Stage primaryStage){
        stage = primaryStage;
    }


    @FXML
    @Usage(description = "Traitement réaliser lors de l'initialisation")
    public void initialize(){
        files.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        for( int i = 0 ; i < 50; i ++){
            masterData.add(new SynchroFxml());
        }
        masterData.add(new SynchroFxml("Salo"));
        /*
        - Recuperer tous les fichiers présent dans le dossier local
        - Récuperer tous les fichiers présent dans le dossier distant du nuage
        - Ne conserver que ceux existant dans les 2 tableau
        */

        files.getItems().addAll(masterData);

        //pluginFxmls.get(2).getActivated().setSelected(true);
    }

    @Usage(description = "Tous cocher ou tous décocher")
    public void tickedNoTicked() throws IOException {
        if(checkAll.isSelected()){
            masterData.forEach(c -> c.selected.setSelected(true));
            //pluginManager.openJarFiles();
        }else{
            masterData.forEach(c -> c.selected.setSelected(false));
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
                    - Télécharger la nouvelle version du fichier sur le pc de l'utilisateur (écrasement)
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

    @Usage(description = "Rechercher un fichier")
    public void search(){
        recherche.getText();
        FilteredList<SynchroFxml> filteredList = new FilteredList<>(masterData, p-> true);

        recherche.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(synchroFxml -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (synchroFxml.getName().toString().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches first name.
                } else if (synchroFxml.getName().toString().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches last name.
                }
                return false; // Does not match.
            });
        });

        sortedData = new SortedList<>(filteredList);

        sortedData.comparatorProperty().bind(files.comparatorProperty());


        files.setItems(sortedData);
    }
}
