package core.Model;

public class NuageModel {
    private String name;
    private String imagePath;
    private String lastEdit;
    private String type;
    private String id;

    public NuageModel(String name, String imagePath, String lasEdit, String type, String id) {
        this.name = name;
        this.imagePath = imagePath;
        this.lastEdit = lasEdit;
        this.type = type;
        this.id = id;
    }

    public NuageModel(String name, String imagePath, String lasEdit, String type) {
        this.name = name;
        this.imagePath = imagePath;
        this.lastEdit = lasEdit;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getLastEdit() {
        return lastEdit;
    }

    public String getType() {
        return type;
    }

    public String getId() {
        return id;
    }
}
