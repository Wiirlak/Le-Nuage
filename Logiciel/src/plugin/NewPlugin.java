package plugin;

public class NewPlugin implements Service {
        public String name = "plugin";

        public String getId() {
            return name;
        }

    public String getName() {
        return name;
    }
}
