package core.Model;

public class Nuage {
    private String name;
    private String imagePath;
    private String lastEdit;
    private String type;

    public Nuage(String name, String imagePath, String lasEdit, String type) {
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
}
