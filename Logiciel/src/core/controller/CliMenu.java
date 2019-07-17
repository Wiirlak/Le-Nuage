package core.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import core.http.auth.HttpAuth;
import core.http.entite.HttpEntite;
import core.http.nuage.HttpNuage;
import core.http.nuage.Nuage;
import core.model.AuthService;
import core.model.Entity;
import org.apache.commons.cli.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CliMenu {
    private Options options;
    private static CommandLine cmd;
//    Desktop.getDesktop().browse(new URL("https://stackoverflow.com/questions/23032253/how-to-change-the-current-scene-to-another-in-javafx").toURI());

    public CliMenu(String[] args) {
        this.options = new Options();
        createOptions(options);
        parseCmd(args);
        if (!checkLogin()){
            System.out.println("Auth echouée, sortie...");
            System.exit(1);
        }
        if (checkOptions()){
            System.out.println("Merci d'avoir utilisé notre application !");
        }else{
            System.out.println("Problèmes avec les paramètres, sortie...");
        }
    }

    private void parseCmd(String[] args){
        try{
            CommandLineParser parser = new DefaultParser();
            cmd = parser.parse(options, args);
        }catch (ParseException e){
            e.printStackTrace();
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("Le Nuage", options);
            System.exit(1);
        }
    }

    private void createOptions(Options o){
        Option login = new Option("l", "login", true, "Login id");
        login.setRequired(true);
        o.addOption(login);

        Option password = new Option("p", "password", true, "Login password");
        password.setRequired(true);
        o.addOption(password);

        Option getNuage = new Option("g", "getNuage");
        getNuage.setDescription("get the list of all your Nuages");
        o.addOption(getNuage);

        Option upload = new Option("u", "u", true, "upload file to the current repertory");
        o.addOption(upload);

        Option download = new Option("d", "download", true, "download the named file");
        o.addOption(download);

        Option navigate = new Option("n", "navigate", true, "move in your nuage");
        o.addOption(navigate);

        Option create = new Option("c", "create", true, "create a new nuage");
        o.addOption(navigate);
    }

    public static boolean checkLogin(){
        try {
            return HttpAuth.login(cmd.getOptionValue("login"), cmd.getOptionValue("password")) == 1;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }


    private boolean checkOptions() {
        int err = 0;
        String[] args;
        if (cmd.hasOption("g")){
            err += gOption();
        }
        if (cmd.hasOption("u")){
            args = cmd.getOptionValues("u");
            if (args.length == 2)
                err += uOption(args);
            else{
                System.out.println("l'upload necessite 2 arguments :" +
                        "\n - le chemin du fichier (local)" +
                        "\n - l'id du parent cible (nuage)");
                err += 1;
            }
        }
        if (cmd.hasOption("d")){
            err += dOption(cmd.getOptionValue("d"));
        }
        if (cmd.hasOption("n")){
            err += nOption(cmd.getOptionValue("n"));
        }
        if (cmd.hasOption("c")){
            err += cOption(cmd.getOptionValue("c"));
        }

        return err == 0;
    }

    private int gOption(){
        try {
            Nuage[] nuageArray = HttpNuage.getNuages();
            for(Nuage nuage : nuageArray)
                System.out.println(nuage.getName()+ " - " +nuage.get_id());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private int uOption(String[] ids){
        String id = ids[0];
        String parentId = ids[1];
        try {
            if (HttpEntite.upload(id, parentId)){
                System.out.println("upload réussi !");
            }
            else{
                System.out.println("Erreur lors de l'upload");
            }
        } catch (IOException e) {
            System.out.println("Erreur lors de l'upload");
        }
        return 0;
    }

    private boolean hasNOption(){
        if (cmd.hasOption("n"))
            return cmd.getOptionValue("n") != null;
        return false;
    }

    private int nOption(String path) {
        Entity[] liste = HttpEntite.getTreeByParentId(path);
        TablePrint print = new TablePrint();

        System.out.println("\"" + path + "\":");
        print.setHeaders("Type", "Name", "ID");
        print.setShowVerticalLines(true);
        for(Entity item : liste){
            if(item.getType().getName().equals("file"))
                print.addRow("", item.getName(), item.get_id());
            else
                print.addRow("|>", item.getName(), item.get_id());
        }
        print.print();
        return 0;
    }

    private int dOption(String id) {
        String name = getName(Objects.requireNonNull(HttpEntite.getOne(id)));
        if (HttpEntite.download(id, name, "./")){
            System.out.println("download réussi !");
        }
        else{
            System.out.println("Erreur lors du download");
        }
        return 0;
    }
    
    private int cOption(String name) {
        HttpNuage.createNuage(name);
        return 0;
    }

    private String getName(StringBuffer content){
        Gson gson = new Gson();
        JsonObject temp = gson.fromJson(content.toString(), JsonObject.class);
        return temp.get("name").getAsString();
    }

}