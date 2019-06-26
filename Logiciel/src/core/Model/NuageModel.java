package core.Model;


import annotation.AnnotatedClass;
import annotation.Status;
import annotation.Usage;

@Status(author = "Bastien NISOLE",
        progression = 90,
        version = 2.5)
public class NuageModel implements AnnotatedClass {
    private String name;
    private String imagePath;
    private String lastEdit;
    private String type;
    private String id;

    @Usage(description = "Constructeur de l'objet avec ID")
    public NuageModel(String name, String imagePath, String lasEdit, String type, String id) {
        this.name = name;
        this.imagePath = imagePath;
        this.lastEdit = lasEdit;
        this.type = type;
        this.id = id;
    }
    @Usage(description = "Constructeur de l'objet sans ID")
    public NuageModel(String name, String imagePath, String lasEdit, String type) {
        this.name = name;
        this.imagePath = imagePath;
        this.lastEdit = lasEdit;
        this.type = type;
    }

    @Usage(description = "Recuperation du nom du nuage")
    public String getName() {
        return name;
    }

    @Usage(description = "Recuperation du chemin de l'image")
    public String getImagePath() {
        return imagePath;
    }

    @Usage(description = "Recuperation de la dernière édition")
    public String getLastEdit() {
        return lastEdit;
    }

    @Usage(description = "Récuperation du type de nuage")
    public String getType() {
        return type;
    }

    @Usage(description = "Récuperation de son ID")
    public String getId() {
        return id;
    }
}
