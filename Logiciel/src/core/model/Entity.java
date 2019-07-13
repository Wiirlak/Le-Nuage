package core.model;

import annotation.AnnotatedClass;
import annotation.Status;
import annotation.Usage;

import java.util.Date;

@Status(author = "Bastien NISOLE",
        progression = 90,
        version = 2.5)
public class Entity implements AnnotatedClass {
    private String is_deleted;
    private String _id;
    private Date created;
    private String name;
    private String hash;
    private String parent;
    private Type type;
    private int __v;
    private int size;

    @Usage(description = "Contructeur")
    public Entity(String is_deleted, String _id, Date created, String name, String parent, String nameType, String idType, int __v, int size, String hash) {
        this.is_deleted = is_deleted;
        this._id = _id;
        this.created = created;
        this.name = name;
        this.hash = hash;
        this.parent = parent;
        this.type = new Type(nameType,idType);
        this.__v = __v;
        this.size = size;
    }

    @Usage(description = "Contructeur")
    public Entity(String name,String _id){
        this.name = name;
        this._id = _id;
    }

    @Usage(description = "Contructeur")
    public Entity(){
    }

    @Usage(description = "Recuperation de l'etat du fichier")
    public String getIs_deleted() {
        return is_deleted;
    }

    @Usage(description = "Assignation de l'etat du fichier")
    public void setIs_deleted(String is_deleted) {
        this.is_deleted = is_deleted;
    }

    @Usage(description = "Recuperation de l'id")
    public String get_id() {
        return _id;
    }

    @Usage(description = "Assignation de l'id")
    public void set_id(String _id) {
        this._id = _id;
    }

    @Usage(description = "Recuperation de la date de création")
    public Date getCreated() {
        return created;
    }

    @Usage(description = "Assignation de la date de création")
    public void setCreated(Date created) {
        this.created = created;
    }

    @Usage(description = "Recuperation du nom de l'entité")
    public String getName() {
        return name;
    }

    @Usage(description = "Assignation du nom de l'entité")
    public void setName(String name) {
        this.name = name;
    }

    @Usage(description = "Recuperation du parent")
    public String getParent() {
        return parent;
    }

    @Usage(description = "Assignation du parent")
    public void setParent(String parent) {
        this.parent = parent;
    }

    @Usage(description = "Recuperation du type")
    public Type getType() {
        return type;
    }

    @Usage(description = "Assignation du type")
    public void setType(Type type) {
        this.type = type;
    }

    @Usage(description = "Recuperation de _v")
    public int get__v() {
        return __v;
    }

    @Usage(description = "Assignation de _v")
    public void set__v(int __v) {
        this.__v = __v;
    }

    @Usage(description = "Convertir en chaine")
    public String toString(){
        return this.getName();
    }

    @Usage(description = "Recuperation de la taille")
    public int getSize() {
        return size;
    }

    @Usage(description = "Assignation de la taille")
    public void setSize(int size) {
        this.size = size;
    }

    @Usage(description = "Recuperation du hash")
    public String getHash() {
        return hash;
    }

    @Usage(description = "Assignation du hash")
    public void setHash(String hash) {
        this.hash = hash;
    }
}
