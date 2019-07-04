package plugin;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class PluginConfig {
    public List<File> listExecutedPlugins;
    public List<String> listExecutedPluginsName;
    private File pluginConfPath;
    private File pluginPath;

    public PluginConfig(File pluginPath) {
        this.pluginPath = pluginPath;
        listExecutedPlugins = new LinkedList<>();
        checkConfigFile("plugins.conf");
    }

    public PluginConfig(File pluginPath, String s) {
        this.pluginPath = pluginPath;
        listExecutedPlugins = new LinkedList<>();
        listExecutedPluginsName = new LinkedList<>();
        checkConfigFile(s);
        checkConfig();
    }

    private void checkConfig() {
        try {
            Gson gson = new Gson();
            String[] items = gson.fromJson(new FileReader(pluginConfPath.getPath()), String[].class);
            for (String i: items) {
                //System.out.println(this.pluginPath.getPath() + "\\" + i);
                String tmpName = this.pluginPath.getPath() + "\\" + i;
                File tmp = new File(tmpName);
                if (tmp.exists() && !listExecutedPlugins.contains(tmp)) {
                    System.out.println(tmp.getPath());
                    listExecutedPlugins.add(tmp);
                    listExecutedPluginsName.add(tmp.getName());
                }
            }
//          System.out.println(gson.toJson(items));
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void addPlugin(String path){
        File tmp = new File(path);
        if (tmp.exists()) {
            System.out.println(tmp.getPath());
            listExecutedPlugins.add(tmp);
            listExecutedPluginsName.add(tmp.getName());
        }
    }

    public void addPlugin(File file){
        System.out.println(file.getPath());
        listExecutedPlugins.add(file);
        listExecutedPluginsName.add(file.getName());
    }

    public void removePlugin(String path){
        File tmp = new File(path);
        if (tmp.exists()) {
            listExecutedPlugins.remove(tmp);
            listExecutedPluginsName.remove(tmp.getName());
        }
    }

    public void removePlugin(File file){
        listExecutedPlugins.remove(file);
        listExecutedPluginsName.remove(file.getName());
    }

    private boolean checkConfigFile(String s) {
        File temp = new File(this.pluginPath.getPath() + "\\" + s);
        if(!temp.exists()){
            try{
                String content = "[\n\t\"pluginName.jar\"\n]";
                if(!temp.createNewFile())
                    return false;
                FileOutputStream tmp = new FileOutputStream(temp);
                tmp.write(content.getBytes());
                tmp.flush();
                tmp.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        this.pluginConfPath = temp;
        return true;
    }

    public void reloadPluginsConfig(){
        listExecutedPlugins = null;
        this.checkConfig();
    }

    public boolean updateConfigFile(){
        try{
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String content = gson.toJson(listExecutedPluginsName);
            System.out.println(content);
            FileOutputStream tmp= new FileOutputStream(this.pluginConfPath);
            tmp.write(content.getBytes());
            tmp.flush();
            tmp.close();
        }catch(IOException e){
            return false;
        }
        return true;
    }

}
