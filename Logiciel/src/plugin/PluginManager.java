package plugin;

import com.sun.org.apache.bcel.internal.classfile.ClassParser;
import com.sun.org.apache.bcel.internal.classfile.JavaClass;

import javax.swing.*;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class PluginManager {
    private File f;
    public String mydoc;
    public File[] listPlugins;
    public URLClassLoader load;
    public JarFile[] jars;

    public PluginManager() {
        mydoc = new JFileChooser().getFileSystemView().getDefaultDirectory().getPath();
        checkPluginFolder("Le-Nuage");
        findAllJar(this.f);
    }

    public boolean checkPluginFolder(String name){
        File test = new File(mydoc+'\\'+name+"\\plugins");
        if(!test.exists()){
            if(!test.mkdirs()){
                return false;
            }
        }
        this.f = test;
        return true;
    }

    public void findAllJar(File path){
        listPlugins = path.listFiles((dir, name) -> name.endsWith(".jar"));

        for(File file : listPlugins) {
            System.out.println(file.getName());
        }
    }

    public void runJar2(JarFile jf) throws IOException {
        Enumeration<JarEntry> je = jf.entries();
        while (je.hasMoreElements()) {
            JarEntry entry = je.nextElement();
            if (entry.getName().endsWith(".class")) {
                System.out.println("ok");
                if (entry.getName() == "start.class"){
                }
            }
        }
    }

    public void runJar(String fp) throws IOException {
        JarFile jar = new JarFile(fp);
        jar.getClass();
        //runJar(jar);
        Runtime r = Runtime.getRuntime();
        r.exec("java -jar "+ fp );
    }

    public void openJarFile(File name) throws IOException {
        URL extra = name.toURL();
        load = new URLClassLoader(new URL[] {extra});
        runJar(name.getAbsolutePath());
    }

    public void openJarUrl(String name) throws IOException {
        runJar(name);
    }




}
