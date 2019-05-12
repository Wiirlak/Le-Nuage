package plugin;

import java.util.ServiceLoader;

public class UserService {
    public void useService() {
        ServiceLoader<Service> loader = ServiceLoader.load(Service.class);
        for (Service plugin : loader) {
            System.out.println(plugin.toString());

        }
    }
}
