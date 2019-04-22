package annotation;

import sample.Model.User;

public class ParserAnnotations
{
    public static void main(String[] args) {

        ParsingProcess test = new ParsingProcess();
        test.processParse(new User());

    }
}
