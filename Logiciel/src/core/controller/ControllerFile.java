package core.controller;

import annotation.AnnotatedClass;
import annotation.Status;
import annotation.Usage;
import core.data.PluginData;
import core.http.entite.HttpEntite;
import core.http.nuage.HttpNuage;
import core.http.nuage.Nuage;
import core.http.profil.HttpProfil;
import core.http.profil.Profil;
import core.model.AuthService;
import core.model.Entity;
import core.model.FolderEntity;
import core.model.NuageModel;
import javafx.animation.RotateTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Status(author = "Bastien NISOLE",
        progression = 50,
        version = 2.3)
public class ControllerFile implements AnnotatedClass {
    public static Stage stage;
    @FXML
    public HBox resizable;

    @FXML
    public FlowPane flowpane;

    @FXML
    public VBox myFile;

    @FXML
    public VBox myFiles;

    @FXML
    public VBox nuageFile;

    @FXML
    public VBox nuageFiles;

    @FXML
    public Label label1;

    @FXML
    public Label label2;

    @FXML
    public Label labelNuage;

    @FXML
    public TextField searchBar;

    @FXML
    public ImageView reloaded;

    @FXML
    public ArrayList<ContextMenu> contextMenuArrayList = new ArrayList<>();


    public  String  url1;
    public  ArrayList<NuageModel> nuageArray = new ArrayList<NuageModel>();


    public  ArrayList<FolderEntity> folderEntityArrayList = new ArrayList<FolderEntity>();


    @Usage(description = "Affecter le stage courant")
    public static void setStage(Stage primaryStage){
        stage = primaryStage;
    }

    @Usage(description = "Actions faites lors de l'initialisation de la page")
    public  void initialize() {

        setUrlFromOs();

        getData();






    }

    @Usage(description = "Récuperation de nuage")
    public void getData(){
        try {
            nuageArray.clear();
            flowpane.getChildren().clear();
            Nuage[] nuageArrayList = HttpNuage.getNuages();
            if (nuageArrayList != null)
                for (Nuage n : nuageArrayList) {
                    nuageArray.add(new NuageModel(n.getName(), n.getImage() == null ? "/assets/pictures/LN.png" : n.getImage(), "15/12/19", "nuages"));
                    addNuage(n.getImage() == null ? "/assets/pictures/LN.png" : n.getImage(), n.getName(), "15/12/19", n.getParentEntity(),n.get_id());
                    //System.out.println(n.getImage());
                }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Usage(description = "Ajout du nuage sur l'interface")
    public void addNuage(String nuageImage, String nuageName, String lastEdit, String ParentEntityid,String nuageId){
        System.out.println("parent entite ? "+ParentEntityid);
        VBox vbox = new VBox();
        vbox.getStyleClass().add("nuages");
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(15, 15, 15, 15));
        Image image = new Image(nuageImage, 70, 50, true, true);
        ImageView imageView = new ImageView(image);
        Label label1 = new Label(nuageName);
        Label label2 = new Label("Derniere modification le :"+lastEdit);
        vbox.getChildren().add(imageView);
        vbox.getChildren().add(label1);
        vbox.getChildren().add(label2);
        vbox.setUserData(ParentEntityid);
        vbox.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
            @Override
            public void handle(final ContextMenuEvent event) {
                for (ContextMenu i : contextMenuArrayList)
                    i.hide();
                createRightClickMenu(nuageName, ((VBox)event.getSource()).getUserData().toString(),nuageId).show(vbox,event.getScreenX(), event.getScreenY());
            }
        });
        vbox.setOnMouseClicked(event -> {
            MouseButton button = event.getButton();
            if(button== MouseButton.PRIMARY){
                AuthService.getNuage().setAll(nuageName,nuageImage,lastEdit,"nuages",ParentEntityid,nuageId);
                labelNuage.setText(nuageName);
                labelNuage.setUserData(ParentEntityid);
                listFile2(nuageFile);
            }
        });



        flowpane.getChildren().add(vbox);
    }

    @Usage(description = "Création de menu sur le click droit sur un nuage")
    public ContextMenu createRightClickMenu(String nuageName, String id,String nuageId){
        ContextMenu contextMenu = new ContextMenu();
        MenuItem item1 = new MenuItem("S'envoler");
        item1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                labelNuage.setText(nuageName);
                labelNuage.setUserData(id);
                listFile2(nuageFile);
            }
        });
        MenuItem item2 = new MenuItem("Voir sa composition");
        MenuItem item3 = new MenuItem("Tomber");
        item3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                labelNuage.setText("Les fichiers de votre nuage");
                nuageFiles.getChildren().clear();
                if(nuageFile.getChildren().size() >= 1 )
                    nuageFile.getChildren().remove(1);
                label2.setText("");
            }
        });
        MenuItem item4 = new MenuItem("Souffler le nuage");
        item4.setUserData(id);
        item4.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    Profil response = HttpProfil.getProfil();
                    if( response != null) {
                         if( HttpNuage.deleteNuage(nuageId) == 1 ){
                             reload();
                             labelNuage.setText("Les fichiers de votre nuage");
                         }

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
        MenuItem item5 = new MenuItem("Renomer");
        item5.setUserData(id);
        item5.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    rename(nuageId,nuageName);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        contextMenu.getItems().addAll(item1, item2,item3,item4,item5);
        contextMenuArrayList.add(contextMenu);
        return contextMenu;
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

    @Usage(description = "Recuperation et affichage des dossiers de l'utilisateur")
    public void listFile(VBox vbox, String filename ){
        TreeView<File> fileViewMine = new TreeView<File>(
                new SimpleFileTreeItem(new File(filename)));
        vbox.getChildren().add(fileViewMine);
        fileViewMine.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            url1 = newValue.getValue().toString();
            myFiles.getChildren().removeAll();
            listFileByFolder(myFiles,url1);
            label1.setText(url1);
        });

    }
    @Usage(description = "Récuperation et affichage des dossiers du nuage")
    public void listFile2(VBox vbox){

        vbox.getChildren().clear();
        vbox.getChildren().add(labelNuage);
        TreeView<String> tree;
        TreeItem<String> distant = new TreeItem<String>("Fichiers distants");
        tree = new TreeView<String>(distant);

        EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                for (ContextMenu i : contextMenuArrayList)
                    i.hide();
                TreeItem tmp = (TreeItem)tree.getSelectionModel().getSelectedItem();
                if(tmp != null && e.getButton() ==  MouseButton.SECONDARY){
                    ContextMenu contextMenu = new ContextMenu();
                    MenuItem item1 = new MenuItem("Créer un dossier");
                    item1.setOnAction(new EventHandler<ActionEvent>() {
                        public void handle(ActionEvent t) {
                            try {
                                if(tmp.getValue().toString().equals("Fichiers distants")){
                                    createFolder("/",labelNuage.getUserData().toString());
                                }else{
                                    createFolder(tmp.getValue().toString(),getIdFromname(tmp.getValue().toString()));
                                }
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        }
                    });
                    contextMenu.getItems().add(item1);
                    contextMenuArrayList.add(contextMenu);
                    contextMenu.show(tree, e.getScreenX(), e.getScreenY());
                }else if(e.getButton() ==  MouseButton.PRIMARY){
                    if( tmp != null &&(String)tmp.getValue() == "Fichiers distants"){
                        listDistantFileByParentId("/",labelNuage.getUserData().toString());
                    }else if(tmp != null){
                        getAllFolderFromRoot(tmp,getIdFromname(tmp.getValue().toString()),tree);
                        listDistantFileByParentId((String)tmp.getValue(),/*tmp.getValue().toString()*/getIdFromname(tmp.getValue().toString()));
                    }
                }

            }
        };
        Entity[]o = HttpEntite.getTreeByParentId(labelNuage.getUserData().toString());
        for( Entity i : o){
            if(i.getType().getName().equals("folder")){
                TreeItem <String> t = new TreeItem<String>(i.getName());
                folderEntityArrayList.add(new FolderEntity(i.get_id(),i.getName()));
                distant.getChildren().add(t);

            }

        }
        tree.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
        vbox.getChildren().add(tree);
    }



    public String getIdFromname(String name){
        for(FolderEntity fe : folderEntityArrayList){
            if(fe.getName().equals(name)){
                return fe.getId();
            }
        }
        return "";
    }



    public void getAllFolderFromRoot(TreeItem treeItem, String id, TreeView tree){
        treeItem.getChildren().clear();
        EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                for (ContextMenu i : contextMenuArrayList)
                    i.hide();
                TreeItem tmp = (TreeItem)tree.getSelectionModel().getSelectedItem();
                if(tmp != null && e.getButton() ==  MouseButton.SECONDARY){
                    ContextMenu contextMenu = new ContextMenu();
                    MenuItem item1 = new MenuItem("Créer un dossier");
                    item1.setOnAction(new EventHandler<ActionEvent>() {
                        public void handle(ActionEvent t) {
                            try {
                                createFolder(tmp.getValue().toString().equals("Fichiers distants") ?"/":tmp.getValue().toString(),getIdFromname(tmp.getValue().toString()));
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        }
                    });
                    contextMenu.getItems().add(item1);
                    contextMenuArrayList.add(contextMenu);
                    contextMenu.show(tree, e.getScreenX(), e.getScreenY());
                }else if(e.getButton() ==  MouseButton.PRIMARY){
                    //System.out.println("dfsosd");
                    if( tmp != null &&(String)tmp.getValue() == "Fichiers distants"){
                        listDistantFileByParentId("/",labelNuage.getUserData().toString());
                    }else if(tmp != null){
                        getAllFolderFromRoot(tmp,getIdFromname(tmp.getValue().toString()),tree);
                        listDistantFileByParentId((String)tmp.getValue(),/*tmp.getValue().toString()*/getIdFromname(tmp.getValue().toString()));
                    }
                }

            }
        };
        Entity[] entity =  HttpEntite.getTreeByParentId(id);
        for( Entity i : entity){
            if(i.getType().getName().equals("folder")){
                TreeItem <String> t = new TreeItem<String>(i.getName());
                treeItem.getChildren().add(t);
                folderEntityArrayList.add(new FolderEntity(i.get_id(),i.getName()));
            }
        }
        treeItem.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
    }

    public void listDistantFileByParentId(String name,String id){


        /*
        GET SIZE OF FILE
        CHANGE NAME OF LABEL1 WITH THE Name of the current entitid

         */
        label2.setText(name);
        label2.setUserData(id);
        Entity[]o = HttpEntite.getTreeByParentId(id);
        nuageFiles.getChildren().clear();
        for( Entity i : o){
            if(i.getType().getName().equals("file")){
                //System.out.println(i);
                HBox hbox =  new HBox();
                hbox.setUserData(i);
                //hbox.getChildren().add(img);
                hbox.getChildren().add(new Label(i.getName()));
                Pane pane = new Pane();
                HBox.setHgrow(pane, Priority.ALWAYS);
                hbox.getChildren().add(pane);
                hbox.getChildren().add(new Label(getSizeOfFile(i.getSize())));
                nuageFiles.getChildren().add(hbox);
                hbox.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                    HttpEntite.download(i.get_id(),i.getName(),label1.getText().equals("")? "":label1.getText(),this);
                });
            }

        }
    }

    @Usage(description = "Affichage des fichiers d'un dossier")
    public void listFileByFolder(VBox vbox, String filename){
        vbox.getChildren().clear();
        //Filtre
        FilenameFilter fnf = (current, name) -> {
            File file = new File(current, name);
            return !file.isHidden() && !file.isDirectory();

    };

        ImageView img = new ImageView();
        img.setImage(new Image("/assets/pictures/file.png"));
        img.setFitHeight(40);
        img.setFitWidth(40);
        img.setPreserveRatio(true);

        //Deroulement
        File repertoire2 = new File(filename);
        String[] liste = repertoire2.list(fnf);
        File[] tmp = repertoire2.listFiles();
        for (int i = 0; i < liste.length; i++) {
            HBox hbox =  new HBox();
            hbox.setUserData(liste[i]);
            //hbox.getChildren().add(img);
            hbox.getChildren().add(new Label(liste[i]));
            Pane pane = new Pane();
            HBox.setHgrow(pane, Priority.ALWAYS);
            hbox.getChildren().add(pane);
            hbox.getChildren().add(new Label(getSizeOfFile(tmp[i].length())));
            vbox.getChildren().add(hbox);
            hbox.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                if(label2.getText() != "" )
                    HttpEntite.threadT(label1.getText()+"\\"+hbox.getUserData().toString(),label2.getUserData().toString(),this);
            });
        }
    }

    @Usage(description = "Affecter le chemin par defaut selon l'os")
    public void setUrlFromOs(){
        if( System.getProperty("os.name").contains("Windows")){//Windows
            //url1 = "C:\\";
            TreeItem t = new TreeItem("Ordinateur");
            for(File file : File.listRoots()){
                SimpleFileTreeItem To = new SimpleFileTreeItem(new File(String.valueOf(file)));
                t.getChildren().addAll(To);

            }
            TreeView<File> fileViewMine = new TreeView<File>(t);
            myFile.getChildren().add(fileViewMine);
            fileViewMine.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                TreeItem tmp = (TreeItem)fileViewMine.getSelectionModel().getSelectedItem();
                if(tmp.getValue() != "Ordinateur"){
                    url1 = newValue.getValue().toString();
                    myFiles.getChildren().clear();
                    listFileByFolder(myFiles,url1);
                    label1.setText(url1);
                }

            });
        }else{ // other
            url1 = "/";
            listFile(myFile,url1);
            listFileByFolder(myFiles,url1);
        }
    }


    @FXML
    @Usage(description = "Changer le type de tri à nuages")
    public void orderbyNuage() {
        setNewNuage("nuages");
    }

    @FXML
    @Usage(description = "Changer le type de tri à shareNuages")
    public void orderbyNuageShared(){
        setNewNuage("shareNuages");
    }

    @FXML
    @Usage(description = "Changer le type de tri à recent")
    public void orderbyLastEdit(){
        setNewNuage("recent");
    }

    @FXML
    @Usage(description = "Changer le type de tri à favorit")
    public void orderbyFavorite(){
        setNewNuage("favorit");
    }

    @FXML
    @Usage(description = "Changer le type de tri à trash")
    public void orderbyTrash(){
        setNewNuage("trash");
    }

    @FXML
    @Usage(description = "Changer le type de tri à nothing")
    public void orderbyNothing(){
        setNewNuage("nothing");
    }

    @FXML
    @Usage(description = "Affichage des nuages en fonction du tri")
    public void setNewNuage(String content){
        ArrayList<NuageModel> nuageToPrint;
        flowpane.getChildren().clear();
        if(content == "nuages"){
            nuageToPrint =  new ArrayList<NuageModel>( nuageArray.stream().filter(type -> type.getType()== content).collect(Collectors.<NuageModel>toList()));
        }else if(content == "shareNuages"){
            nuageToPrint = new ArrayList<NuageModel>( nuageArray.stream().filter(type -> type.getType()== content).collect(Collectors.<NuageModel>toList()));
        }else if(content == "recent"){
            nuageToPrint = new ArrayList<NuageModel>( nuageArray.stream().filter(type -> type.getType()== content).collect(Collectors.<NuageModel>toList()));
        }else if(content == "favorit"){
            nuageToPrint = new ArrayList<NuageModel>( nuageArray.stream().filter(type -> type.getType()== content).collect(Collectors.<NuageModel>toList()));
        }else if(content == "trash"){
            nuageToPrint = new ArrayList<NuageModel>( nuageArray.stream().filter(type -> type.getType()== content).collect(Collectors.<NuageModel>toList()));
        }else {
            nuageToPrint = nuageArray;
        }
        for(NuageModel i : nuageToPrint){
            addNuage(i.getImagePath(),i.getName(),i.getLastEdit(),i.getParentEntiteid(),i.getNuageId());
        }
    }


    @FXML
    @Usage(description = "Se deconnecter")
    public void disconnect() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("index.fxml"));
        Scene scene = new Scene(loader.load());
        ControllerIndex controllerIndex = loader.getController();
        controllerIndex.setStage(stage);
        stage.setResizable(false);
        stage.setWidth(900);
        stage.setHeight(700);
        stage.setMinWidth(900);
        stage.setMinHeight(700);
        stage.setMaxWidth(900);
        stage.setMaxHeight(700);
        stage.setTitle(PluginData.nuageName);
        stage.setScene(scene);
        scene.getStylesheets().add("core/stylesheet/stylesheet.css");
        stage.show();
    }

    @FXML
    @Usage(description = "Ouverture de la page du profil de l'utilisateur")
    public void openProfile() throws IOException {
        Stage subStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("profil.fxml"));
        Scene scene = new Scene(loader.load(),400,700);
        ControllerProfil controllerProfil = loader.getController();
        controllerProfil.setStage(subStage);
        subStage.setResizable(false);
        subStage.setTitle(PluginData.nuageName+" - Mon profil");
        subStage.setScene(scene);
        subStage.getIcons().add(new Image("pictures/LNb.png"));
        subStage.initOwner(stage);
        subStage.initModality(Modality.WINDOW_MODAL);
        scene.getStylesheets().add("core/stylesheet/stylesheet.css");
        subStage.show();
    }

    @FXML
    @Usage(description = "Ouverture de la fenetre de chargement")
    public void openLoading() throws IOException {
        Stage subStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("Loading.fxml"));
        Scene scene = new Scene(loader.load());
        ControllerLoading controllerLoading = loader.getController();
        ControllerLoading.setStage(subStage);
        subStage.setResizable(false);
        subStage.setTitle(PluginData.nuageName + " - Mon profil");
        subStage.getIcons().add(new Image("pictures/LNb.png"));
        subStage.setScene(scene);
        subStage.initOwner(stage);
        subStage.initModality(Modality.WINDOW_MODAL);
        scene.getStylesheets().add("core/stylesheet/stylesheet.css");
        subStage.show();
    }



    @FXML
    @Usage(description = "Ouverture de la fenetre des options")
    public void openOptions() throws IOException {
        Stage subStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("options.fxml"));
        Scene scene = new Scene(loader.load());
        ControllerOption controllerOption = loader.getController();
        controllerOption.setStage(subStage);
        subStage.setResizable(false);
        subStage.setTitle(PluginData.nuageName+" - Options");
        subStage.setScene(scene);
        subStage.getIcons().add(new Image("pictures/LNb.png"));
        subStage.initOwner(stage);
        subStage.initModality(Modality.WINDOW_MODAL);
        scene.getStylesheets().add("core/stylesheet/stylesheet.css");
        subStage.show();
    }


    @FXML
    @Usage(description = "Lancer la rechercher lorsque l'on appuie sur entrer")
    public void  onEnter(){

        ArrayList<NuageModel> nuageToPrint;
        nuageToPrint = new ArrayList<NuageModel>( nuageArray.stream().filter(type -> type.getName().toLowerCase().contains(searchBar.getText().toLowerCase()) ).collect(Collectors.<NuageModel>toList()));
        flowpane.getChildren().clear();
        for(NuageModel i : nuageToPrint){
            addNuage(i.getImagePath(),i.getName(),i.getLastEdit(), i.getParentEntiteid(),i.getParentEntiteid());
        }

    }

    @FXML
    @Usage(description = "Recharger les informations de la page")
    public void reload() {
        RotateTransition rt = new RotateTransition(Duration.millis(3000),reloaded);
        rt.setByAngle(360);
        rt.setFromAngle(0);
        rt.setCycleCount(1);
        rt.setAutoReverse(true);
        rt.play();
        getData();
        if(!label2.getText().equals("")){
            listFile2(nuageFile);
            listDistantFileByParentId(label2.getText(),label2.getUserData().toString());
        }
        System.out.println("reloaded");
        if(!label1.getText().equals(""))
            listFileByFolder(myFiles,url1);


        //refresh labelNuage
        if(!AuthService.getNuage().getName().equals(""))
            labelNuage.setText(AuthService.getNuage().getName());
    }

    @FXML
    @Usage(description = "Ouverture de la fenetre de synchronisation")
    public void synchro() throws IOException {
        if(!label1.getText().equals("") && !label2.getText().equals("") ){
            Stage subStage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("synchro.fxml"));
            ControllerSynchro controllerSynchro = new ControllerSynchro(label1.getText(),label2.getText(),label2.getUserData().toString());
            loader.setController(controllerSynchro);
            /*controllerSynchro.setLocalFolder(label1.getText());
            controllerSynchro.setDistantFolder(label2.getText());*/
            Scene scene = new Scene(loader.load());
            controllerSynchro.setStage(subStage);
            subStage.setResizable(false);
            subStage.setTitle(PluginData.nuageName + " - Synchronisation");
            subStage.setScene(scene);
            subStage.getIcons().add(new Image("pictures/LNb.png"));
            subStage.initOwner(stage);
            subStage.initModality(Modality.WINDOW_MODAL);
            scene.getStylesheets().add("core/stylesheet/stylesheet.css");
            subStage.show();
        }

    }

    @FXML
    @Usage(description = "Ouverture de la fenetre de synchronisation")
    public void rename(String nuageId, String nuageName) throws IOException {
            Stage subStage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("Rename.fxml"));
            ControllerRename controllerRename = new ControllerRename(nuageName,nuageId,this);
            loader.setController(controllerRename);
            Scene scene = new Scene(loader.load());
            controllerRename.setStage(subStage);
            subStage.setResizable(false);
            subStage.setTitle(PluginData.nuageName + " - Renomage");
            subStage.setScene(scene);
            subStage.getIcons().add(new Image("pictures/LNb.png"));
            subStage.initOwner(stage);
            subStage.initModality(Modality.WINDOW_MODAL);
            scene.getStylesheets().add("core/stylesheet/stylesheet.css");
            subStage.show();
    }


    @FXML
    @Usage(description = "Ouverture de la fenetre de création de nuage")
    public void createNuage() throws IOException {
        Stage subStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("createNuage.fxml"));
        ControllerCreateNuage controllerCreateNuage = new ControllerCreateNuage(this);
        loader.setController(controllerCreateNuage);
        Scene scene = new Scene(loader.load());
        controllerCreateNuage.setStage(subStage);
        subStage.setResizable(false);
        subStage.setTitle(PluginData.nuageName+" - Créer un nuage");
        subStage.setScene(scene);
        subStage.getIcons().add(new Image("pictures/LNb.png"));
        subStage.initOwner(stage);
        subStage.initModality(Modality.WINDOW_MODAL);
        scene.getStylesheets().add("core/stylesheet/stylesheet.css");
        subStage.show();
    }

    @FXML
    @Usage(description = "Ouverture de la fenetre de création de dossier")
    public void createFolder(String parentName, String parentId ) throws IOException {
        Stage subStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("createFolder.fxml"));
        ControllerCreateFolder controllerCreateFolder = new ControllerCreateFolder(this,parentId,parentName);
        loader.setController(controllerCreateFolder);
        Scene scene = new Scene(loader.load());
        controllerCreateFolder.setStage(subStage);
        subStage.setResizable(false);
        subStage.setTitle(PluginData.nuageName+" - Créer un dossier");
        subStage.setScene(scene);
        subStage.getIcons().add(new Image("pictures/LNb.png"));
        subStage.initOwner(stage);
        subStage.initModality(Modality.WINDOW_MODAL);
        scene.getStylesheets().add("core/stylesheet/stylesheet.css");
        subStage.show();
    }

}




