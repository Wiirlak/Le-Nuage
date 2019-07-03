package plugin;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class PluginConfig {
    public List<File> listExecutedPlugins;
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
                if (tmp.exists()) {
                    System.out.println(tmp.getPath());
                    listExecutedPlugins.add(tmp);
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
        }
    }

    public void addPlugin(File file){
        System.out.println(file.getPath());
        listExecutedPlugins.add(file);
    }

    public void removePlugin(String path){
        File tmp = new File(path);
        if (tmp.exists()) {
            listExecutedPlugins.remove(tmp);
        }
    }

    public void removePlugin(File file){
        listExecutedPlugins.remove(file);
    }

    private boolean checkConfigFile(String s) {
        File temp = new File(this.pluginPath.getPath() + "\\" + s);
        if(!temp.exists()){
            if(!temp.mkdirs()){
                return false;
            }
        }
        this.pluginConfPath = temp;
        return true;
    }

    public void reloadPluginsConfig(){
        listExecutedPlugins = null;
        this.checkConfig();
    }

}
