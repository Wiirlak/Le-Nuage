package plugin;

public class ServiceImpl implements Service{
    public String name = "lol";

    @Override
    public String getId() {
        return name;
    }


}
