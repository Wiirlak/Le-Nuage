package annotation;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;

public class ParsingProcess {
    public void processParse(ArrayList<AnnotatedClass> ac){
        //ac.stream().map(n -> processParse(n)).findAny();
        for (AnnotatedClass a : ac) {
            processParse(a);
        }
    }

    public boolean processParse(AnnotatedClass ac)
    {
        /*
        CLASS
        */
        System.out.println(" --- Class: " + ac.getClass().getName());
        //Annotation[] a = ac.getClass().getAnnotations();
        if (ac.getClass().isAnnotationPresent(Status.class)) {
            Status a = ac.getClass().getAnnotation(Status.class);
            try
            {
                DecimalFormat v = new DecimalFormat("0.00");

                System.out.println("   - Auteur:        " + a.author());
                System.out.println("   - Finished:      " + (a.finished() ? "Yes" : "No"));
                if (!a.finished())
                    System.out.println("   - Progression:   " + a.progression() + "%");
                System.out.println("   - Verion:        v" + v.format(a.version()));
            }
            catch (SecurityException e)
            {
                e.printStackTrace();
            }
            System.out.println();

            /*
            FIELD
            */
            System.out.println(" --- Fields ");
            Field[] f = ac.getClass().getDeclaredFields();
            for (Field field : f) {
                if(field.isAnnotationPresent(Usage.class)){
                    Usage fa = field.getAnnotation(Usage.class);
                    System.out.println("  -- Field:        " + field.getName());
                    System.out.println("   - Description:  " + fa.description());
                }
            }
            System.out.println();

            /*
            METHOD
            */
            System.out.println(" --- Methods ");
            Method[] m = ac.getClass().getMethods();
            for (Method method : m) {
                if(method.isAnnotationPresent(Usage.class)){
                    Usage fa = method.getAnnotation(Usage.class);
                    System.out.println("  -- Method:       " + method.getName());
                    System.out.println("   - Description:  " + fa.description());
                }
            }
            System.out.println();
        }
        return true;
    }
}
