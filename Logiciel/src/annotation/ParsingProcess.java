package annotation;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ParsingProcess {

    private FileWriter fileWriter;


    public void processParse(ArrayList<AnnotatedClass> ac) throws IOException {
        //ac.stream().map(n -> processParse(n)).findAny();

        String str = new JFileChooser().getFileSystemView().getDefaultDirectory().getPath()+"\\Le-nuage\\Parser";
        System.out.println("To see the result go to : "+ new File(str));
        File f = new File(str);
        if(! f.exists())
            f.mkdirs();

        fileWriter = new FileWriter(str+"\\log.log");

        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        //System.out.println(formatter.format(date));
        printInFile(formatter.format(date)+"\n");

        for (AnnotatedClass a : ac) {
            processParse(a);
        }
        fileWriter.close();
    }

    public boolean processParse(AnnotatedClass ac) throws IOException {
        /*
        CLASS
        */
        //System.out.println(" --- Class: " + ac.getClass().getName());
        printInFile("\n --- Class: " + ac.getClass().getName()+"\n");
        //Annotation[] a = ac.getClass().getAnnotations();
        if (ac.getClass().isAnnotationPresent(Status.class)) {
            Status a = ac.getClass().getAnnotation(Status.class);
            try
            {
                DecimalFormat v = new DecimalFormat("0.00");

                //System.out.println("   - Auteur:        " + a.author());
                printInFile("   - Auteur:        " + a.author()+"\n");
                //System.out.println("   - Finished:      " + (a.finished() ? "Yes" : "No"));
                printInFile("   - Finished:      " + (a.finished() ? "Yes" : "No")+"\n");
                if (!a.finished()){
                    //System.out.println("   - Progression:   " + a.progression() + "%");
                    printInFile("   - Progression:   " + a.progression() + "%"+"\n");
                }
                //System.out.println("   - Verion:        v" + v.format(a.version()));
                printInFile("   - Verion:        v" + v.format(a.version())+"\n");
            }
            catch (SecurityException e)
            {
                e.printStackTrace();
            }
            //System.out.println();

            /*
            FIELD
            */
            //System.out.println(" --- Fields ");
            printInFile(" --- Fields "+"\n");
            Field[] f = ac.getClass().getDeclaredFields();
            for (Field field : f) {
                if(field.isAnnotationPresent(Usage.class)){
                    Usage fa = field.getAnnotation(Usage.class);
                    //System.out.println("  -- Field:        " + field.getName());
                    printInFile("  -- Field:        " + field.getName()+"\n");
                    //System.out.println("   - Description:  " + fa.description());
                    printInFile("   - Description:  " + fa.description()+"\n");
                }
            }
            //System.out.println();

            /*
            METHOD
            */
            //System.out.println(" --- Methods ");
            printInFile("\n --- Methods "+"\n");
            Method[] m = ac.getClass().getMethods();
            for (Method method : m) {
                if(method.isAnnotationPresent(Usage.class)){
                    Usage fa = method.getAnnotation(Usage.class);
                    //System.out.println("  -- Method:       " + method.getName());
                    printInFile("\n  -- Method:       " + method.getName()+"\n");
                    //System.out.println("   - Description:  " + fa.description());
                    printInFile("   - Description:  " + fa.description()+"\n");
                }
            }
            //System.out.println();
        }
        return true;
    }

    public void printInFile(String text) throws IOException {
        fileWriter.write(text);
    }
}
