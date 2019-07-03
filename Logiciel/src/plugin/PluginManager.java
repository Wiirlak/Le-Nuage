package plugin;

import com.google.gson.Gson;
import core.data.PluginData;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
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
    public String mydoc;
    public File pluginPath;
    public ClassLoader loader;
    public Thread load;
    public File[] listPlugins;
    public List<String> classPlugin;
    public PluginConfig conf;

    public PluginManager() {
        mydoc = new JFileChooser().getFileSystemView().getDefaultDirectory().getPath();
        checkPluginFolder("Le-nuage");
        conf = new PluginConfig(pluginPath,"plugins.conf");
        findAllJar(this.pluginPath);
        classPlugin = new LinkedList<>();
    }


    public boolean checkPluginFolder(String name){
        File temp = new File(mydoc+'\\'+name+"\\plugins");
        if(!temp.exists()){
            try{
                String content = "[\n\t\"pluginName.jar\"\n]";
                temp.createNewFile();
                FileOutputStream tmp= new FileOutputStream(temp);
                tmp.write(content.getBytes());
                tmp.flush();
                tmp.close();
            }catch (IOException e){
                e.printStackTrace();
            }
            if(!temp.mkdirs()){
                return false;
            }
        }
        this.pluginPath = temp;
        return true;
    }

    public void findAllJar(File path){
        listPlugins = path.listFiles((dir, name) -> name.endsWith(".jar"));

        //Comment under
        for(File file : listPlugins) {
            //System.out.println(file.getName());
        }
    }

    public void runAllJar(String methodName) throws Exception {
        for(File file : listPlugins) {
            runJar2(file,methodName);
        }
    }

    public void runSelectedJar(String methodName) throws Exception {
        for(File file : conf.listExecutedPlugins) {
            runJar2(file,methodName);
        }
    }

    public void runJar2(File name, String methodName) throws Exception {
        String fp = name.getAbsolutePath();
        URL ur = name.toURL();


        classPlugin.clear();
        loader = URLClassLoader.newInstance(new URL[] { ur }, getClass().getClassLoader());

        JarFile jar = new JarFile(fp);
        Enumeration<JarEntry> je = jar.entries();
        String convertedName;
        while (je.hasMoreElements()) {
            JarEntry entry = je.nextElement();
            if (entry.getName().endsWith(".class")) {
                convertedName = entry.getName().substring(0,entry.getName().length() - 6).replace('/','.'); // remove ".class" and format
                classPlugin.add(convertedName);

                if (convertedName.equals("Runnable")){
                    Class<?> subClass = Class.forName(convertedName, true, loader);
                    Constructor<?> subConst = subClass.getConstructor();
                    Object doRun = subConst.newInstance();
                    Method[] methodes = subClass.getMethods();
                    for(Method m: methodes){
                        if(m.getName().equals(methodName)){
                            if( methodName.equals("returnNewStage") ){
                                Stage s = (Stage) m.invoke(doRun);
                                s.show();
                            }else if(methodName.equals("returnNuageName")){
                                PluginData.nuageName = (String) m.invoke((doRun));
                            }else{
                                m.invoke(doRun);
                            }
                            break;
                        }
                    }
                }
            }
        }
        //System.out.println(classPlugin);
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
            //System.out.println(p.exitValue());
        }, 1, TimeUnit.SECONDS);

    }

    public static void openJarUrl(String name) throws IOException {
        runJar(name);
    }

}
