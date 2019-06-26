package core.Model;


import annotation.AnnotatedClass;
import annotation.Status;
import annotation.Usage;

@Status(author = "Bastien NISOLE",
        progression = 90,
        version = 2.5)
public class AuthUser implements AnnotatedClass {
    private String id;
    private String token;

    @Usage(description = "Constructeur de l'objet")
    public AuthUser() {
    }

    @Usage(description = "Récuperation de l'ID")
    public String getId() {
        return id;
    }

    @Usage(description = "Affecter un ID")
    public void setId(String id) {
        this.id = id;
    }

    @Usage(description = "recuperation du token")
    public String getToken() {
        return token;
    }

    @Usage(description = "Affectation du token")
    public void setToken(String token) {
        this.token = token;
    }
}
