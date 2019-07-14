package core.model;

import annotation.AnnotatedClass;
import annotation.Status;
import annotation.Usage;

@Status(author = "Bastien NISOLE",
        progression = 90,
        version = 2.5)
public class FolderEntity  implements AnnotatedClass {

    private String id;
    private String name;

    @Usage(description = "Contructeur")
    public FolderEntity(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @Usage(description = "Contructeur")
    public FolderEntity() {
    }

    @Usage(description = "Recuperation de l'id")
    public String getId() {
        return id;
    }

    @Usage(description = "Assignation de l'id")
    public void setId(String id) {
        this.id = id;
    }

    @Usage(description = "Recuperation du nom de l'entité")
    public String getName() {
        return name;
    }

    @Usage(description = "Assignation du nom")
    public void setName(String name) {
        this.name = name;
    }
}
