package core.Model;

public class AuthService {
    private AuthService(){

    }

    private static AuthService AUTH  =  new AuthService();
    //private static User user = new User();
    private static AuthUser user = new AuthUser();

    public static AuthService getAUTH(){
        return AUTH;
    }

    /*public static User getUser() {
        return user;
    }*/

    public static  AuthUser getAuthUser(){
        return user;
    }
}
