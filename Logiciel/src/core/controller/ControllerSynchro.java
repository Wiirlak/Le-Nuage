package core.controller;


import annotation.AnnotatedClass;
import annotation.Status;
import annotation.Usage;
import core.http.entite.HttpEntite;
import core.model.Entity;
import core.model.SynchroFxml;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

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


    public String localFolder;

    public String distantFolder;

    public String distantFileId;


    @FXML
    public Label localFilePath;

    @FXML
    public Label distantFilePath;

    @FXML
    public ObservableList<SynchroFxml> masterData = FXCollections.observableArrayList();

    public SortedList<SynchroFxml> sortedData;

    @Usage(description = "Affecter le stage courant")
    public static void setStage(Stage primaryStage){
        stage = primaryStage;
    }


    public ControllerSynchro(String LocalFolder,String distantFolder, String distantFileId ) {
        this.localFolder = LocalFolder;
        this.distantFolder = distantFolder;
        this.distantFileId = distantFileId;
    }

    public ControllerSynchro() {
        this("","","");
    }

    @Usage(description = "Traitement réaliser lors de l'initialisation")
    @FXML
    public void initialize() throws ParseException {

        files.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        /*for( int i = 0 ; i < 50; i ++){
            masterData.add(new SynchroFxml());
        }*/

        localFilePath.setText(localFolder);
        distantFilePath.setText(distantFolder);
        File[] listOfFiles = new File(localFilePath.getText()).listFiles();

        Entity[] distantFilename = HttpEntite.getTreeByParentId(distantFileId);



        for (File i : listOfFiles) {
            if (i.isFile() && isStrignInArray(distantFilename,i.getName())) {
                SimpleDateFormat dt1 = new SimpleDateFormat("dd-MM-YY HH:mm");
                String d = dt1.format(new Date(i.lastModified()));
                masterData.add(new SynchroFxml(i.getName(),getSizeOfFile(i.length()),"0Kb",d,"no sé"));
            }else{
                System.out.println(i.getName());
            }
        }
        /*
        - Recuperer tous les fichiers présent dans le dossier local
        - Récuperer tous les fichiers présent dans le dossier distant du nuage
        - Ne conserver que ceux existant dans les 2 tableau
        */
        sortedData.addAll(masterData);

        files.getItems().addAll(masterData);

        //pluginFxmls.get(2).getActivated().setSelected(true);
    }


    @Usage(description = "Recuperation de la taille d'un fichier")
    public String getSizeOfFile(double size){
        NumberFormat nf = new DecimalFormat("0.##");
        String[] data = {"B", "KB", "MB", "GB", "TB"};
        int index = 0;
        while(size > 1024 ) {
            size /= 1024;
            index++;
        }
        return nf.format(size)+" "+data[index];
    }

    public boolean isStrignInArray(Entity [] array, String id){
        for(Entity i : array){
            if(i.getName().equals(id))
                return true;
        }
        return false;
    }

    @Usage(description = "Tous cocher ou tous décocher")
    public void tickedNoTicked() throws IOException {
        if(checkAll.isSelected()){
            sortedData.forEach(c -> c.selected.setSelected(true));
            //pluginManager.openJarFiles();
        }else{
            sortedData.forEach(c -> c.selected.setSelected(false));
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
