package core.model;

import annotation.AnnotatedClass;
import annotation.Status;
import annotation.Usage;
import org.apache.commons.validator.routines.EmailValidator;

@Status(author = "Bastien NISOLE",
        progression = 90,
        version = 2.5)
public class User implements AnnotatedClass {

    private String nom;
    private String prenom;
    private String email;
    private String date;
    private String mdp;
    private String mdpc;
    private Boolean check;
    private String token;

    @Usage(description = "Constructeur d'un utilisateur")
    public User(String nom, String prenom, String email, String date, String mdp, String mdpc, Boolean check) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.date = date;
        this.mdp = mdp;
        this.mdpc = mdpc;
        this.check = check;
    }

    @Usage(description = "Constructeur par defaut d'utilisateur")
    public User() {
        this.nom = "nom";
        this.prenom = "prenom";
        this.email = "email@free.fr";
        this.date = "2019-02-12";
        this.mdp = "mdp";
        this.mdpc = "mdpc";
        this.check = true;
    }

    @Usage(description = "Retourne le nom de l'utilisateur")
    public String getNom() {
        return nom;
    }

    @Usage(description = "Affecter un nom à un utilisateur")
    public void setNom(String nom) {
        this.nom = nom;
    }

    @Usage(description = "Recuperation du nom de l'utilisateur")
    public String getPrenom() {
        return prenom;
    }

    @Usage(description = "Affecter un nom à un utilisateur")
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    @Usage(description = "Recuperation de l'email de l'utilisateur")
    public String getEmail() {
        return email;
    }

    @Usage(description = "Affecter une email à un utilisateur")
    public void setEmail(String email) {
        this.email = email;
    }

    @Usage(description = "Recuperation de la date de naissance de l'utilisateur")
    public String getDate() {
        return date;
    }

    @Usage(description = "Affecter une date de naissance à un utilisateur")
    public void setDate(String date) {
        this.date = date;
    }

    @Usage(description = "Recuperation du mot de passe de l'utilisateur")
    public String getMdp() {
        return mdp;
    }

    @Usage(description = "Affecter un mot de passe à un utilisateur")
    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    @Usage(description = "Recuperation du mot de passe confirmé de l'utilisateur")
    public String getMdpc() {
        return mdpc;
    }

    @Usage(description = "Affecter un mot de passe confirmé à un utilisateur")
    public void setMdpc(String mdpc) {
        this.mdpc = mdpc;
    }

    @Usage(description = "Recuperation de la validation des CGU de l'utilisateur")
    public Boolean getCheck() {
        return check;
    }

    @Usage(description = "Affecter une valeur à la validation des CGU à un utilisateur")
    public void setCheck(Boolean check) {
        this.check = check;
    }

    @Usage(description = "Verification de l'email de l'utilisateur")
    public boolean isValidEmail(){
        EmailValidator emailValidato = EmailValidator.getInstance();
        return emailValidato.isValid(this.getEmail());

    }
    @Usage(description = "Comparaison de du mot de passe et de sa confirmatation")
    public boolean pwdEqual(){
        return this.getMdp().equals(this.getMdpc());
    }
}
