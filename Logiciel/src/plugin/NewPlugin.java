package plugin;

public class NewPlugin extends Service {
        public String name = "plugin";

        @Override
        public String getId() {
            return name;
        }
}
