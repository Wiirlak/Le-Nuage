package core.Controller;

import annotation.AnnotatedClass;
import annotation.Status;
import core.Http.Apple.Apple;
import core.Http.Apple.HttpApple;
import core.Http.Nuage.HttpNuage;
import core.Http.Nuage.Nuage;
import core.Http.Profil.HttpProfil;
import core.Http.Profil.Profil;
import core.Model.NuageModel;
import javafx.animation.RotateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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

@Status(author = "Krishan Class",
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


    public  String  url1;
    public  String  url2;
    public  ArrayList<NuageModel> nuageArray = new ArrayList<NuageModel>();

    public static void setStage(Stage primaryStage){
        stage = primaryStage;
    }

    public  void initialize() {

        //System.out.println(AuthService.getUser().getEmail());
        setUrlFromOs();

        label1.setText(url1);
        label2.setText(url2);

        /*for(int i = 0 ; i < 100; i++){
            if(i % 5 == 0){
                nuageArray.add(new NuageModel("My nuage "+i, "/assets/pictures/LN.png", "15/12/19", "nuages"));
            }else if( i % 5 == 1){
                nuageArray.add(new NuageModel("My nuage "+i, "/assets/pictures/LN.png", "15/12/19", "shareNuages"));
            }else if( i % 5 == 2){
                nuageArray.add(new NuageModel("My nuage "+i, "/assets/pictures/LN.png", "15/12/19", "recent"));
            }else if( i % 5 == 3){
                nuageArray.add(new NuageModel("My nuage "+i, "/assets/pictures/LN.png", "15/12/19", "favorit"));
            }else if( i % 5 == 4){
                nuageArray.add(new NuageModel("My nuage "+i, "/assets/pictures/LN.png", "15/12/19", "trash"));
            }else{
                nuageArray.add(new NuageModel("My nuage "+i, "/assets/pictures/LN.png", "15/12/19", "trash"));
            }


        }*/
        getData();

        /*for(NuageModel i : nuageArray){
            addNuage(i.getImagePath(),i.getName(),i.getLastEdit());
        }*/

        //NuageModel file
        listFile2(nuageFile);

        // Fichier d'un dossier courant local
       //listFileByFolder(myFiles,url1);
        // Fichier d'un dossier courant distant
       //listFileByFolder(nuageFiles,url2);




        /* **********

            Call API


         */

    }


    public void getData(){
        try {
            nuageArray.clear();
            flowpane.getChildren().clear();
            Profil response = HttpProfil.getProfil();
            for(Nuage n : HttpNuage.getNuages(response.getNuages())){
                nuageArray.add(new NuageModel(n.getName(),n.getImage()== null ? "/assets/pictures/LN.png": n.getImage(),"15/12/19","nuages"));
                addNuage(n.getImage()== null ? "/assets/pictures/LN.png": n.getImage(),n.getName(),"15/12/19");
                //System.out.println(n.getImage());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void addNuage(String nuageImage, String nuageName, String lastEdit){
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
        vbox.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
            @Override
            public void handle(ContextMenuEvent event) {
                createRightClickMenu(nuageName).show(vbox, event.getScreenX(), event.getScreenY());
            }
        });
        vbox.setOnMouseClicked(event -> {
            MouseButton button = event.getButton();
            if(button== MouseButton.PRIMARY){
                labelNuage.setText(nuageName);
            }
        });



        flowpane.getChildren().add(vbox);
    }

    public ContextMenu createRightClickMenu(String nuageName){
        ContextMenu contextMenu = new ContextMenu();
        MenuItem item1 = new MenuItem("S'envoler");
        item1.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                labelNuage.setText(nuageName);
            }
        });
        MenuItem item2 = new MenuItem("Voir sa composition");
        MenuItem item3 = new MenuItem("Tomber");
        MenuItem item4 = new MenuItem("Souffler le nuage");
        contextMenu.getItems().addAll(item1, item2,item3,item4);
        return contextMenu;
    }

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

    public void listFile2(VBox vbox){
        /*TreeView<File> fileViewMine = new TreeView<File>(
                new SimpleFileTreeItem(new File(filename)));
        vbox.getChildren().add(fileViewMine);
        fileViewMine.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            url2 = newValue.getValue().toString();
            nuageFiles.getChildren().removeAll();
            listFileByFolder(nuageFiles,url2);
            label2.setText(url2);
        });*/

        /*TreeItem<String> distant = new TreeItem<String>("wowow");
        TreeItem<String> rootItem = new TreeItem<String>("salade");

        // JSP Item
        TreeItem<String> itemJSP = new TreeItem<String>("tomate");

        // Spring Item
        TreeItem<String> itemSpring = new TreeItem<>("oignon");*/

        // Add to Root
        //distant.getChildren().addAll(rootItem, itemJSP, itemSpring);
        vbox.getChildren().clear();
        vbox.getChildren().add(labelNuage);
        TreeView<String> tree;
        TreeItem<String> distant = new TreeItem<String>("Pommes");
        try {
            HttpApple test = new HttpApple();
            //System.out.println(test.getApple("5c5819ea0bbc7a1b444e9d9f"));
            //System.out.println(test.getApples()[1].get_id());
            // System.out.println(test.deleteApple("5c5819ea0bbc7a1b444e9d9f"));
            //System.out.println(test.createApple("Cookie",635));
            //System.out.println(test.updateApple("5c45f7c51d5463541812ddf4","Pasteque",115));
            for(Apple a :test.getApples() ){
                distant.getChildren().add(new TreeItem<>(a.getName()+" - "+a.getPepins()));
            }

        }catch (Exception e){
            e.printStackTrace();
        }


        tree = new TreeView<String>(distant);
        vbox.getChildren().add(tree);
    }


    public void listFileByFolder(VBox vbox, String filename){
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
            hbox.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> System.out.println(hbox.getUserData().toString()));
        }
    }

    public void setUrlFromOs(){
        if( System.getProperty("os.name").contains("Windows")){//Windows
            //url1 = "C:\\";
            url2 = "D:\\";
            TreeItem t = new TreeItem("Ordinateur");
            for(File file : File.listRoots()){
                SimpleFileTreeItem To = new SimpleFileTreeItem(new File(String.valueOf(file)));
                t.getChildren().addAll(To);

            }
            TreeView<File> fileViewMine = new TreeView<File>(t);
            myFile.getChildren().add(fileViewMine);
            fileViewMine.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                url1 = newValue.getValue().toString();
                myFiles.getChildren().clear();
                listFileByFolder(myFiles,url1);
                label1.setText(url1);
            });
        }else{ // other
            url1 = "/";
            url2 = "/";
            listFile(myFile,url1);
            listFileByFolder(myFiles,url1);
        }
    }


    @FXML
    public void orderbyNuage() {
        setNewNuage("nuages");
    }
    @FXML
    public void orderbyNuageShared(){
        setNewNuage("shareNuages");
    }

    @FXML
    public void orderbyLastEdit(){
        setNewNuage("recent");
    }

    @FXML
    public void orderbyFavorite(){
        setNewNuage("favorit");
    }

    @FXML
    public void orderbyTrash(){
        setNewNuage("trash");
    }

    @FXML
    public void orderbyNothing(){
        setNewNuage("nothing");
    }

    @FXML
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
            addNuage(i.getImagePath(),i.getName(),i.getLastEdit());
        }
    }


    @FXML
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
        stage.setTitle("Le-NuageModel");
        stage.setScene(scene);
        scene.getStylesheets().add("core/StyleSheet/stylesheet.css");
        stage.show();
    }

    @FXML
    public void openProfile() throws IOException {
        Stage subStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("profil.fxml"));
        Scene scene = new Scene(loader.load(),400,700);
        ControllerProfil controllerProfil = loader.getController();
        controllerProfil.setStage(subStage);
        subStage.setResizable(false);
        subStage.setTitle("Mon profil");
        subStage.setScene(scene);
        subStage.initOwner(stage);
        subStage.initModality(Modality.WINDOW_MODAL);
        scene.getStylesheets().add("core/StyleSheet/stylesheet.css");
        subStage.show();
    }

    @FXML
    public void openLoading() throws IOException {
        Stage subStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("Loading.fxml"));
        Scene scene = new Scene(loader.load());
        ControllerLoading controllerLoading = loader.getController();
        ControllerLoading.setStage(subStage);
        subStage.setResizable(false);
        subStage.setTitle("Mon profil");
        subStage.setScene(scene);
        subStage.initOwner(stage);
        subStage.initModality(Modality.WINDOW_MODAL);
        scene.getStylesheets().add("core/StyleSheet/stylesheet.css");
        subStage.show();
    }



    @FXML
    public void openOptions() throws IOException {
        Stage subStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("options.fxml"));
        Scene scene = new Scene(loader.load());
        ControllerOption controllerOption = loader.getController();
        controllerOption.setStage(subStage);
        subStage.setResizable(false);
        subStage.setTitle("Options");
        subStage.setScene(scene);
        subStage.initOwner(stage);
        subStage.initModality(Modality.WINDOW_MODAL);
        scene.getStylesheets().add("core/StyleSheet/stylesheet.css");
        subStage.show();
    }


    @FXML
    public void  onEnter(){

        ArrayList<NuageModel> nuageToPrint;
        nuageToPrint = new ArrayList<NuageModel>( nuageArray.stream().filter(type -> type.getName().toLowerCase().contains(searchBar.getText().toLowerCase()) ).collect(Collectors.<NuageModel>toList()));
        flowpane.getChildren().clear();
        for(NuageModel i : nuageToPrint){
            addNuage(i.getImagePath(),i.getName(),i.getLastEdit());
        }

    }

    @FXML
    public void reload() throws InterruptedException {
        /*for(int i = 0 ; i < 360 ; i++){
            reloaded.setRotate(reloaded.getRotate() + i);
            TimeUnit.MILLISECONDS.sleep(25);
        }*/
        RotateTransition rt = new RotateTransition(Duration.millis(3000),reloaded);
        rt.setByAngle(360);
        rt.setFromAngle(0);
        rt.setCycleCount(1);
        rt.setAutoReverse(true);
        rt.play();
        getData();
        listFile2(nuageFile);
    }



}
