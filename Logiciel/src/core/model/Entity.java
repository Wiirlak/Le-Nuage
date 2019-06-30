package core.model;

public class Entity {
    private String is_deleted;
    private String _id;
    private String created;
    private String name;
    private String parent;
    private Type type;
    private int __v;


    public Entity(String is_deleted, String _id, String created, String name, String parent, String nameType, String idType, int __v) {
        this.is_deleted = is_deleted;
        this._id = _id;
        this.created = created;
        this.name = name;
        this.parent = parent;
        this.type = new Type(nameType,idType);
        this.__v = __v;
    }

    public String getIs_deleted() {
        return is_deleted;
    }

    public void setIs_deleted(String is_deleted) {
        this.is_deleted = is_deleted;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public int get__v() {
        return __v;
    }

    public void set__v(int __v) {
        this.__v = __v;
    }

    public String toString(){
        return this.getName();
    }
}
