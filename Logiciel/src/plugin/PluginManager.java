package plugin;

import javax.swing.*;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.jar.JarFile;

public class PluginManager {
    private File f;
    public String mydoc;
    public File[] listPlugins;
    public URLClassLoader load;

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

    private void runJar(){

    }

    private void openJarFile(File name) throws IOException {
        URL extra = name.toURL();
        load = new URLClassLoader(new URL[] {extra});
        JarFile jar = new JarFile(name.getAbsolutePath());
        jar.getClass();
    }
}
