package core.Model;


import annotation.AnnotatedClass;
import annotation.Status;
import annotation.Usage;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;

@Status(author = "Bastien NISOLE",
        progression = 90,
        version = 2.5)
public class SynchroFxml implements AnnotatedClass {

    @FXML
    public CheckBox selected = new CheckBox();

    @FXML
    public Label name;

    @FXML
    public Label lastEdit;

    @FXML
    public Label size;

    @Usage(description = "Constructeur de l'objet pluginFxml avec un traitement par defaut pour rentré dans la tableview")
    public SynchroFxml() {
        selected =  new CheckBox();
        name =  new Label("file.txt");
        lastEdit =  new Label("18-05-19 15:52 (17-05-19 12:25)");
        size =  new Label("25 ko (12ko)");
    }

    @Usage(description = "Constructeur de l'objet pluginFxml avec un nom pour rentré dans la tableview")
    public SynchroFxml(String filename) {
        selected =  new CheckBox();
        name =  new Label(filename);
        lastEdit =  new Label("18-05-19 15:52 (17-05-19 12:25)");
        size =  new Label("25 ko (12ko)");
    }

    @Usage(description = "Constructeur de l'objet pluginFxml pour rentré dans la tableview")
    public SynchroFxml(String filename, String sizeLocal, String sizeCloud, String lastUpdateLocal, String lastUpdateCloud) {
        selected =  new CheckBox();
        name =  new Label(filename);
        lastEdit =  new Label(lastUpdateLocal +" ("+lastUpdateCloud+")");
        size =  new Label(sizeLocal + " ("+sizeCloud+")");
    }


    @Usage(description = "Recuperation la checkbox")
    public CheckBox getSelected() {
        return selected;
    }

    @Usage(description = "Recuperation du nom")
    public Label getName() {
        return name;
    }

    @Usage(description = "Recuperation de la date de derniere modification")
    public Label getLastEdit() {
        return lastEdit;
    }


    @Usage(description = "Recuperation de la taille")
    public Label getSize() {
        return size;
    }

}
