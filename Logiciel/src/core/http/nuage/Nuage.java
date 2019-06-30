package core.http.nuage;

public class Nuage {
        private String [] entities;
        private String _id;
        private String name;
        private String image;
        private int __v;
        private String parentEntity;

    public Nuage(String[] entities, String _id, String name, String image, int __v, String parentEntity) {
        this.entities = entities;
        this._id = _id;
        this.name = name;
        this.image = "https://www.shorturl.at/img/shorturl-square.png";
        this.__v = __v;
        this.parentEntity = parentEntity;
    }

    public String[] getEntities() {
        return entities;
    }

    public String get_id() {
        return _id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public int get__v() {
        return __v;
    }

    public String getParentEntity() {
        return parentEntity;
    }
}
