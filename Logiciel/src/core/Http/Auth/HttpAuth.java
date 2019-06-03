package core.Http.Auth;

import java.io.*;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class HttpAuth {

    private static final String apiUrl = "http://localhost:3000";

    public HttpAuth() {

    }

    public static int login(String email, String password) throws IOException {
        try{
            URL url = new URL(apiUrl+"/auth/login");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setDoOutput(true);
            con.setConnectTimeout(60000); //60 secs
            con.setReadTimeout(60000); //60 secs
            con.setRequestMethod("POST");
            String urlParameters  = "{\"email\":\""+email+"\",\"password\":\""+password+"\"}";
            //System.out.println(urlParameters);
            con.setRequestProperty("Content-Type", "application/json");
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.flush();
            wr.close();
            int status = con.getResponseCode();
            if(status == 200)
                return 1;
            else
                return 0;
        }catch (ConnectException e){
            return -1;
        }
    }

    public static int register(String email, String password, String passwordConfirmed,  String name, String surname, String birthday, boolean cgu) throws IOException {
        try{
            URL url = new URL(apiUrl+"/auth/register");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setDoOutput(true);
            con.setConnectTimeout(60000); //60 secs
            con.setReadTimeout(60000); //60 secs
            con.setRequestMethod("POST");
            String urlParameters  = "{\"email\":\""+email+"\",\"password\":\""+password+"\",\"name\":\""+name+"\"}";
            //System.out.println(urlParameters);
            con.setRequestProperty("Content-Type", "application/json");
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.flush();
            wr.close();
            int status = con.getResponseCode();
            if(status == 201)
                return 1;
            else
                return 0;
        }catch (ConnectException e){
            return -1;
        }
    }

}
