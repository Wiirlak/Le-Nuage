package core.model;


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
    private String parentEntiteid;
    private String nuageId;

    @Usage(description = "Constructeur de l'objet avec ID")
    public NuageModel(String name, String imagePath, String lasEdit, String type, String parentEntiteid,String nuageId) {
        this.name = name;
        this.imagePath = imagePath;
        this.lastEdit = lasEdit;
        this.type = type;
        this.parentEntiteid = parentEntiteid;
        this.nuageId = nuageId;
    }
    @Usage(description = "Constructeur de l'objet sans ID")
    public NuageModel(String name, String imagePath, String lasEdit, String type) {
        this.name = name;
        this.imagePath = imagePath;
        this.lastEdit = lasEdit;
        this.type = type;
    }
    @Usage(description = "Constructeur")
    public NuageModel(){
        this.name = "";
        this.imagePath = "";
        this.lastEdit = "";
        this.type = "";
        this.parentEntiteid = "";
        this.nuageId = "";
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

    @Usage(description = "Récuperation de l'id de son parent")
    public String getParentEntiteid() {
        return parentEntiteid;
    }

    @Usage(description = "Récuperation de son ID")
    public String getNuageId() {
        return nuageId;
    }

    @Usage(description = "Recuperation du nom du nuage")
    public void setName(String name) {
        this.name = name;
    }

    @Usage(description = "Assignation de l'image")
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @Usage(description = "Assigntaion de la derniere modification")
    public void setLastEdit(String lastEdit) {
        this.lastEdit = lastEdit;
    }

    @Usage(description = "Assignation du type")
    public void setType(String type) {
        this.type = type;
    }

    @Usage(description = "Assgination de la parent entity")
    public void setParentEntiteid(String parentEntiteid) {
        this.parentEntiteid = parentEntiteid;
    }

    @Usage(description = "Assignation de l'id")
    public void setNuageId(String nuageId) {
        this.nuageId = nuageId;
    }

    @Usage(description = "Modifier toutes les valeurs")
    public void setAll(String name, String imagePath, String lasEdit, String type, String parentEntiteid,String nuageId){
        this.name = name;
        this.imagePath = imagePath;
        this.lastEdit = lasEdit;
        this.type = type;
        this.parentEntiteid = parentEntiteid;
        this.nuageId = nuageId;
    }
}



