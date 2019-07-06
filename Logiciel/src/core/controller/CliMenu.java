package core.controller;

import org.apache.commons.cli.*;

public class CliMenu {
    private Options options;
    private static CommandLine cmd;

    public CliMenu(String[] args) {
        this.options = new Options();
        createOptions(options);
        parseCmd(args);
        checkLogin();
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

    private boolean createOptions(Options o){
        Option cmd = new Option("cmd", "cmd", false, "Activate the cmd mode");
        cmd.setRequired(true);
        o.addOption(cmd);

        Option login = new Option("l", "login", true, "Login id");
        login.setRequired(true);
        o.addOption(login);

        Option password = new Option("p", "password", true, "Login password");
        password.setRequired(true);
        o.addOption(password);

        Option getNuage = new Option("g", "getNuage", true, "get the list of all your Nuages");
        o.addOption(getNuage);

        Option upload = new Option("u", "u", true, "upload file to the current repertory");
        o.addOption(upload);

        Option download = new Option("d", "download", true, "download the named file");
        o.addOption(download);

        Option navigate = new Option("n", "navigate", true, "move in your nuage");
        o.addOption(navigate);

        return true;
    }

    public static boolean checkLogin(){
        String email = cmd.getOptionValue("login");
        String password = cmd.getOptionValue("password");
//        appelApi
//        logged = token
//        if(logged != null)
        return true;
//        else
//            return false;
    }



}