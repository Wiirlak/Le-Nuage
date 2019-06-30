package core.model;


import annotation.AnnotatedClass;
import annotation.Status;
import annotation.Usage;

@Status(author = "Bastien NISOLE",
        progression = 90,
        version = 2.5)
public class AuthService implements AnnotatedClass {

    private static AuthService AUTH  =  new AuthService();
    private static AuthUser user = new AuthUser();

    @Usage(description = "Constructeur de l'objet par defaut")
    public AuthService(){

    }

    @Usage(description = "Recuperation de l'instance AUTH")
    public static AuthService getAUTH(){
        return AUTH;
    }

    @Usage(description = "Recuperation de l'utilisateur courrant")
    public static  AuthUser getAuthUser(){
        return user;
    }
}
