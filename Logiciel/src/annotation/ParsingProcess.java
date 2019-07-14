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
        //
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
        //
        printInFile("\n --- Class: " + ac.getClass().getName()+"\n");
        //Annotation[] a = ac.getClass().getAnnotations();
        if (ac.getClass().isAnnotationPresent(Status.class)) {
            Status a = ac.getClass().getAnnotation(Status.class);
            try
            {
                DecimalFormat v = new DecimalFormat("0.00");

                //
                printInFile("   - Auteur:        " + a.author()+"\n");
                //
                printInFile("   - Finished:      " + (a.finished() ? "Yes" : "No")+"\n");
                if (!a.finished()){
                    //
                    printInFile("   - Progression:   " + a.progression() + "%"+"\n");
                }
                //
                printInFile("   - Verion:        v" + v.format(a.version())+"\n");
            }
            catch (SecurityException e)
            {
                e.printStackTrace();
            }
            //

            /*
            FIELD
            */
            //
            printInFile(" --- Fields "+"\n");
            Field[] f = ac.getClass().getDeclaredFields();
            for (Field field : f) {
                if(field.isAnnotationPresent(Usage.class)){
                    Usage fa = field.getAnnotation(Usage.class);
                    //
                    printInFile("  -- Field:        " + field.getName()+"\n");
                    //
                    printInFile("   - Description:  " + fa.description()+"\n");
                }
            }
            //

            /*
            METHOD
            */
            //
            printInFile("\n --- Methods "+"\n");
            Method[] m = ac.getClass().getMethods();
            for (Method method : m) {
                if(method.isAnnotationPresent(Usage.class)){
                    Usage fa = method.getAnnotation(Usage.class);
                    //
                    printInFile("\n  -- Method:       " + method.getName()+"\n");
                    //
                    printInFile("   - Description:  " + fa.description()+"\n");
                }
            }
            //
        }
        return true;
    }

    public void printInFile(String text) throws IOException {
        fileWriter.write(text);
    }
}
