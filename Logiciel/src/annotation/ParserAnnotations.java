package annotation;

import core.controller.*;
import core.Main;
import core.model.*;
import core.preloader.Preload;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ParserAnnotations
{
    public ParserAnnotations() {
        System.out.println("---- Annotation Parse ----\n");
        ArrayList<AnnotatedClass> test = new ArrayList<>();

        //Modele
        test.add(new AuthService());
        test.add(new AuthUser());
        test.add(new NuageModel("Test","test","test","test"));
        test.add(new PluginFxml(new File("t"),true));
        test.add(new SynchroFxml());
        test.add(new User());

        //Preloader
        test.add(new Preload());

        //Main
        test.add(new Main());

        //controller
        test.add(new ControllerFile());
        test.add(new ControllerIndex());
        test.add(new ControllerLoading());
        test.add(new ControllerPlugin());
        test.add(new ControllerProfil());
        test.add(new ControllerSignUp());
        test.add(new ControllerSynchro());



        ParsingProcess process = null;
        try {
            process = new ParsingProcess();
            process.processParse(test);
        } catch (IOException e) {
            e.printStackTrace();
        }


        System.out.println("\n---- END of Parsing ----");
    }

}
