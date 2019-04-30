package sample.Model;

import annotation.AnnotatedClass;
import annotation.Status;
import annotation.Usage;
import org.apache.commons.validator.routines.EmailValidator;

@Status(author = "Krishan Class",
        progression = 50,
        version = 2.3)
public class User implements AnnotatedClass {

    @Usage(description = "Declaration du nom de l'User")
    private String nom;
    private String prenom;
    private String email;
    private String date;
    private String mdp;
    private String mdpc;
    private Boolean check;

    public User(String nom, String prenom, String email, String date, String mdp, String mdpc, Boolean check) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.date = date;
        this.mdp = mdp;
        this.mdpc = mdpc;
        this.check = check;
    }

    public User() {
        this.nom = "nom";
        this.prenom = "prenom";
        this.email = "email@free.fr";
        this.date = "2019-02-12";
        this.mdp = "mdp";
        this.mdpc = "mdpc";
        this.check = true;
    }

    @Usage(description = "Recuperation du nom de l'utilisateur")
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public String getMdpc() {
        return mdpc;
    }

    public void setMdpc(String mdpc) {
        this.mdpc = mdpc;
    }

    public Boolean getCheck() {
        return check;
    }

    public void setCheck(Boolean check) {
        this.check = check;
    }

    public boolean isValidEmail(){
        EmailValidator emailValidato = EmailValidator.getInstance();
        return emailValidato.isValid(this.getEmail());

    }

    public boolean pwdEqual(){
        return this.getMdp().equals(this.getMdpc());
    }
}
