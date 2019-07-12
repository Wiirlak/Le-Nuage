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
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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

    public ArrayList<Entity> idFileDistant = new ArrayList<Entity>();

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
    public void initialize() {

        files.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        filesSynchro.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        localFilePath.setText(localFolder);
        distantFilePath.setText(distantFolder);

        File[] listOfFiles = new File(localFilePath.getText()).listFiles();
        for( File i : listOfFiles){
            if(i.isFile()){
                StringBuffer content =  HttpEntite.getLastEntityByNameAndParentId(distantFileId,i.getName());
                if(content != null) {
                    Gson gson = new Gson();
                    JsonObject entity = gson.fromJson(content.toString(), JsonObject.class);
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

                            if (!entity.get("hash").getAsString().equals(localFileHash)) {
                                masterData.add(new SynchroFxml(i.getName(), getSizeOfFile(i.length()), getSizeOfFile(entity.get("size").getAsDouble()), d,entity.get("created").getAsString()  ) );
                                idFileDistant.add(new Entity(i.getName(),entity.get("_id").getAsString()));

                            }
                            masterDataSynchro.add(new SynchroFxml(i.getName(), getSizeOfFile(i.length()), getSizeOfFile(entity.get("size").getAsDouble()), d, entity.get("created").getAsString()));
                        } catch (NoSuchAlgorithmException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        Arrays.fill(listOfFiles,null);
        sortedData = new SortedList<>(masterData);

        sortedDataSynchro = new SortedList<>(masterDataSynchro);

        files.getItems().addAll(masterData);
        filesSynchro.getItems().addAll(masterDataSynchro);
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
        for( SynchroFxml sfxml : sortedData){
            if(sfxml.getSelected().isSelected()){
                try {
                    HttpEntite.upload(localFolder+"/"+sfxml.getName().getText(),distantFileId);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        leave();
    }

    @Usage(description = "Synchronise tous les fichiers sélectionnés avec la version distante")
    public void synchroDistant(){
        for( SynchroFxml sfxml : sortedData){
            if(sfxml.getSelected().isSelected()){
                for(Entity i : idFileDistant){
                    if(i.getName().equals(sfxml.getName().getText())){
                        //
                        HttpEntite.download(i.get_id(),i.getName(),localFolder);
                    }
                }
            }
        }
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
