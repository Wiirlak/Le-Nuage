package plugin;

import com.sun.org.apache.bcel.internal.classfile.ClassParser;
import com.sun.org.apache.bcel.internal.classfile.JavaClass;

import javax.swing.*;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class PluginManager {
    public File pluginPath;
    public String mydoc;
    public ClassLoader loader;
    public Thread load;
    public File[] listPlugins;
    public List<String> classPlugin;

    public PluginManager() {
        mydoc = new JFileChooser().getFileSystemView().getDefaultDirectory().getPath();
        checkPluginFolder("Le-NuageModel");
        findAllJar(this.pluginPath);
        classPlugin = new LinkedList<>();
    }

    public boolean checkPluginFolder(String name){
        File test = new File(mydoc+'\\'+name+"\\plugins");
        if(!test.exists()){
            if(!test.mkdirs()){
                return false;
            }
        }
        this.pluginPath = test;
        return true;
    }

    public void findAllJar(File path){
        listPlugins = path.listFiles((dir, name) -> name.endsWith(".jar"));

        for(File file : listPlugins) {
            System.out.println(file.getName());
        }
    }

    public void runJar2(String fp, URL ur) throws Exception {
        loader = URLClassLoader.newInstance(new URL[] { ur }, getClass().getClassLoader());

        JarFile jar = new JarFile(fp);
        Enumeration<JarEntry> je = jar.entries();
        String convertedName;
        while (je.hasMoreElements()) {
            JarEntry entry = je.nextElement();
            if (entry.getName().endsWith(".class")) {
                convertedName = entry.getName().substring(0,entry.getName().length() - 6).replace('/','.'); // remove ".class" and format
                classPlugin.add(convertedName);
                if (convertedName.equals("sample.Model.User")){
                    Class<?> subClass = Class.forName(convertedName, true, loader);
                    Constructor<?> subConst = subClass.getConstructor();
                    Object doRun = subConst.newInstance();

                    Method testaccess = subClass.getMethod("getNom");
                    System.out.println(testaccess.invoke(doRun));

                }else if (convertedName.equals("sample.Main")){
                    Class<?> subClass = Class.forName(convertedName, true, loader);
                    Constructor<?> subConst = subClass.getConstructor();
                    Object doRun = subConst.newInstance();

                    Method[] testsaccess = subClass.getMethods();
                    for(Method m: testsaccess){
                        //System.out.println(m.getName());
                        if(m.getName().equals("main")){
                            String[] args = new String[] {};
                            //m.invoke(doRun, args);
                        }
                    }
                }
            }
        }
        System.out.println(classPlugin);
    }

    public static void runJar(String fp) throws IOException {
        JarFile jar = new JarFile(fp);
        jar.getClass();
        ScheduledThreadPoolExecutor exec = new ScheduledThreadPoolExecutor(1);

        exec.schedule(() -> {
            Runtime r = Runtime.getRuntime();
            Process p = null;
            try {
                p = r.exec("java -jar "+ fp );
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                p.waitFor();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(p.exitValue());
        }, 1, TimeUnit.SECONDS);

    }

    public void openJarFile(File name) throws Exception {
        URL tmp = name.toURL();
        //runJar(name.getAbsolutePath());
        runJar2(name.getAbsolutePath(), tmp);
    }

    public static void openJarUrl(String name) throws IOException {
        runJar(name);
    }

    public void openJarFiles() throws Exception {
        for(File f: listPlugins){
            openJarFile(f);
        }
    }




}
