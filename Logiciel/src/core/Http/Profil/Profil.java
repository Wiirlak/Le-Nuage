package core.Http.Profil;

public class Profil {
    private boolean is_deleted;
    private int capacity_max;
    private int capacity_usage ;
    private String[] nuages;
    private String _id;
    private String name;
    private String firstname;
    private String email;
    private String password;
    private String birthdate;

    public Profil(boolean is_deleted, int capacity_max, int capacity_usage, String[] nuages, String _id, String name, String firstname, String email, String password, String birthdate) {
        this.is_deleted = is_deleted;
        this.capacity_max = capacity_max;
        this.capacity_usage = capacity_usage;
        this.nuages = nuages;
        this._id = _id;
        this.name = name;
        this.firstname = firstname;
        this.email = email;
        this.password = password;
        this.birthdate = birthdate;
    }

    public boolean isIs_deleted() {
        return is_deleted;
    }

    public int getCapacity_max() {
        return capacity_max;
    }

    public int getCapacity_usage() {
        return capacity_usage;
    }

    public String[] getNuages() {
        return nuages;
    }

    public String get_id() {
        return _id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getBirthdate() {
        return birthdate;
    }
}
