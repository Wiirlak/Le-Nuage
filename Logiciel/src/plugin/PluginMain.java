package plugin;

import java.io.File;

public class PluginMain {
    public static void main(String[] args) throws Exception {
        PluginManager a = new PluginManager();
        for(File file : a.listPlugins) {
            System.out.println("\nFile : " + file.getName());
            a.openJarFile(file);
        }
    }
}
