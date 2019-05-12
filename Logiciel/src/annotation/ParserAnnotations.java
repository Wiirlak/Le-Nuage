package annotation;

import core.Controller.ControllerFile;
import core.Model.User;

import java.util.ArrayList;

public class ParserAnnotations
{
    public static void main(String[] args) {

        System.out.println("---- Annotation Parse ----\n");
        ArrayList<AnnotatedClass> test = new ArrayList<>();
        test.add(new User());
        test.add(new ControllerFile());


        ParsingProcess process = new ParsingProcess();
        process.processParse(test);

        System.out.println("\n---- END of Parsing ----");

    }
}
