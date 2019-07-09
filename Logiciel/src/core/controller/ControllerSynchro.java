package core.controller;


import annotation.AnnotatedClass;
import annotation.Status;
import annotation.Usage;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
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

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.TimeZone;

@Status(author = "Bastien NISOLE",
        progression = 50,
        version = 2.3)
public class ControllerSynchro  implements AnnotatedClass {

    public static Stage stage;

    @FXML
    public TableView files;

    @FXML
    public TableView filesSynchro;

    @FXML
    public CheckBox checkAll;


    @FXML
    public TextField recherche;

    @FXML
    public TextField rechercheSynchro;


    public String localFolder;

    public String distantFolder;

    public String distantFileId;


    @FXML
    public Label localFilePath;

    @FXML
    public Label distantFilePath;

    public ObservableList<SynchroFxml> masterData = FXCollections.observableArrayList();

    public ObservableList<SynchroFxml> masterDataSynchro = FXCollections.observableArrayList();

    public SortedList<SynchroFxml> sortedData;

    public SortedList<SynchroFxml> sortedDataSynchro;

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
        filesSynchro.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        /*for( int i = 0 ; i < 50; i ++){
            masterData.add(new SynchroFxml());
        }*/

        localFilePath.setText(localFolder);
        distantFilePath.setText(distantFolder);


        /*Entity[] distantFilename = HttpEntite.getTreeByParentId(distantFileId);



        for (File i : listOfFiles) {
            if (i.isFile() && isStrignInArray(distantFilename,i.getName())) {
                SimpleDateFormat dt1 = new SimpleDateFormat("dd-MM-YY HH:mm");
                String d = dt1.format(new Date(i.lastModified()));
                //masterData.add(new SynchroFxml(i.getName(),getSizeOfFile(i.length()),getSize(distantFilename,i.getName()),d,getDate(distantFilename,i.getName())));

                try {
                    MessageDigest digest = null;
                    digest = MessageDigest.getInstance("SHA-256");
                    byte[] buffer= new byte[8192];
                    int count;
                    BufferedInputStream bis = new BufferedInputStream(new FileInputStream(i));
                    while ((count = bis.read(buffer)) > 0) {
                        digest.update(buffer, 0, count);
                    }
                    bis.close();
                    byte[] hash = digest.digest();
                    if(isHashInDistantFile(bytesToHex(hash),distantFilename)){
                        String id = getIdFromHash(bytesToHex(hash),distantFilename);
                        masterDataSynchro.add(new SynchroFxml(i.getName(),getSizeOfFile(i.length()),getSize(distantFilename,i.getName(),id),d,getDate(distantFilename,i.getName(),id)));
                    }else{
                        String id = getIdFromName(i.getName(),distantFilename);
                        masterData.add(new SynchroFxml(i.getName(),getSizeOfFile(i.length()),getSize(distantFilename,i.getName(),id),d,getDate(distantFilename,i.getName(),id)));
                    }
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else{
                //System.out.println(i.getName());
            }
        }

        */

        File[] listOfFiles = new File(localFilePath.getText()).listFiles();
        for( File i : listOfFiles){
            StringBuffer content =  HttpEntite.getLastEntityByNameAndParentId(distantFileId,i.getName());
            if(content != null) {
                Gson gson = new Gson();
                JsonArray tmp = gson.fromJson(content.toString(), JsonArray.class);
                JsonObject entity = tmp.getAsJsonObject();
                //return temp.get("name").getAsString();

                if (entity != null) {
                    try {


                        /*

                        date

                         */
                        SimpleDateFormat dt1 = new SimpleDateFormat("dd-MM-YY HH:mm");
                        String d = dt1.format(new Date(i.lastModified()));

                        /*

                        sha 256

                         */
                        MessageDigest digest = null;
                        digest = MessageDigest.getInstance("SHA-256");
                        byte[] buffer = new byte[8192];
                        int count;
                        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(i));
                        while ((count = bis.read(buffer)) > 0) {
                            digest.update(buffer, 0, count);
                        }
                        bis.close();
                        byte[] hash = digest.digest();
                        String localFileHash = bytesToHex(hash);
                        if (entity.get("hash").getAsString().equals(localFileHash)) {
                            masterData.add(new SynchroFxml(i.getName(), getSizeOfFile(i.length()), getSizeOfFile(entity.get("size").getAsDouble()), d, dt1.format(entity.get("created").getAsString())));
                        }
                        masterDataSynchro.add(new SynchroFxml(i.getName(), getSizeOfFile(i.length()), getSizeOfFile(entity.get("size").getAsDouble()), d, dt1.format(entity.get("created").getAsString())));
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }




        /*
        - Recuperer tous les fichiers présent dans le dossier local
        - Récuperer tous les fichiers présent da6ns le dossier distant du nuage
        - Ne conserver que ceux existant dans les 2 tableau
        */
        //sortedData.addAll(masterData);
        sortedData = new SortedList<>(masterData);

        sortedDataSynchro = new SortedList<>(masterDataSynchro);

        files.getItems().addAll(masterData);
        filesSynchro.getItems().addAll(masterDataSynchro);

        //pluginFxmls.get(2).getActivated().setSelected(true);
    }

    private static String bytesToHex(byte[] hash) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }

    @Usage(description = "Recuperation de la taille d'un fichier")
    public String getSizeOfFile(double size){
        NumberFormat nf = new DecimalFormat("0.##");
        String[] data = {"o", "Ko", "Mo", "Go", "To"};
        int index = 0;
        while(size > 1024 ) {
            size /= 1024;
            index++;
        }
        return nf.format(size)+" "+data[index];
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

    @Usage(description = "Synchronise tous les fichiers sélectionnés avec la version locale")
    public void synchroLocal(){

        /*

            Si case choché upload la version sur le nuage

         */
        for( SynchroFxml sfxml : sortedData){
            if(sfxml.getSelected().isSelected()){
                try {
                    HttpEntite.upload(localFolder+"/"+sfxml.getName(),distantFileId);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        /*
            - Recuperer toutes lignes sélectionnées
            - Pour chaque fichier
                - Comparer le contenu distant et local
                - Comparer la date de modification distante et locale
                - Si tout est identique
                    - Ne rien faire
                - Sinon
                - Sinon
                    - Télécharger la nouvelle version du fichier sur le pc de l'utilisateur (écrasement)
            - Fermer la fênetre

            ******
            Amélioration :
            N'afficher que les fichier different dès le début
        */


        leave();
    }

    @Usage(description = "Synchronise tous les fichiers sélectionnés avec la version distante")
    public void synchroDistant(){
        /*

            Si case choché téléchargé la version sur le nuage

         */
        /*for( SynchroFxml sfxml : sortedData){
            if(sfxml.getSelected().isSelected()){
                try {
                    HttpEntite.download();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }*/
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

    @Usage(description = "Rechercher un fichier")
    public void searchSynchro(){
        rechercheSynchro.getText();
        FilteredList<SynchroFxml> filteredList = new FilteredList<>(masterDataSynchro, p-> true);

        rechercheSynchro.textProperty().addListener((observable, oldValue, newValue) -> {
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

        sortedDataSynchro = new SortedList<>(filteredList);

        sortedDataSynchro.comparatorProperty().bind(filesSynchro.comparatorProperty());


        filesSynchro.setItems(sortedDataSynchro);
    }


    public boolean isHashInDistantFile(String hash,Entity [] array ){
        for(Entity i : array){
            if(i.getHash().equals(hash))
                return true;
        }
        return false;
    }
}
