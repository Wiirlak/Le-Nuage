package plugin;

import java.io.File;

public class Plugin {
    public String name;
    public File jar;

    public Plugin(File jar) {
        this.jar = jar;
        this.name = jar.getName().substring(0, jar.getName().length() - 4);
    }
}
