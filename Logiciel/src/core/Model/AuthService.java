package core.Model;

public class AuthService {
    private AuthService(){

    }

    private static AuthService AUTH  =  new AuthService();
    private static User user = new User();

    public static AuthService getAUTH(){
        return AUTH;
    }

    public static User getUser() {
        return user;
    }
}
