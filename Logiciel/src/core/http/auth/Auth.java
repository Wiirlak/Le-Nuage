package core.http.auth;

public class Auth {
    //private String id;
    private boolean auth;
    private String token;


    public Auth(boolean auth,String token) {
        //this.id = id;
        this.auth = auth;
        this.token = token;
    }


    /*public String getId() {
        return id;
    }*/

    public String getToken() {
        return token;
    }

    public boolean getAuth() {
        return auth;
    }
}
