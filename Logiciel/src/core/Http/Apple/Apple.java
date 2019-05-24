package core.Http.Apple;

public class Apple {
    private String _id ;
    private String name ;
    private String pepins ;

    public Apple(String _id, String name, String pepins) {
        this._id = _id;
        this.name = name;
        this.pepins = pepins;
    }

    public String get_id() {
        return _id;
    }

    public String getName() {
        return name;
    }

    public String getPepins() {
        return pepins;
    }
}
