package core.model;

import annotation.AnnotatedClass;
import annotation.Status;
import annotation.Usage;

@Status(author = "Bastien NISOLE",
        progression = 90,
        version = 2.5)
public class Type  implements AnnotatedClass {

    private String name;
    private String _id;

    @Usage(description = "Constructeur")
    public Type(String name, String _id) {
        this.name = name;
        this._id = _id;
    }

    @Usage(description = "Constructeur")
    public Type() {
    }

    @Usage(description = "Récuperation du nom")
    public String getName() {
        return name;
    }

    @Usage(description = "Assignation du nom ")
    public void setName(String name) {
        this.name = name;
    }

    @Usage(description = "Récuperation du nom")
    public String get_id() {
        return _id;
    }

    @Usage(description = "Assignation de l'id")
    public void set_id(String _id) {
        this._id = _id;
    }
}
