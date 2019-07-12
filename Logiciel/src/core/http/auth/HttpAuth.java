package core.http.auth;

import com.google.gson.Gson;
import core.data.GlobalData;
import core.model.AuthService;

import java.io.*;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpAuth {

    public static int login(String email, String password) throws IOException {
        try{
            URL url = new URL(GlobalData.url+"/auth/login");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setDoOutput(true);
            con.setConnectTimeout(GlobalData.timeout); //60 secs
            con.setReadTimeout(GlobalData.timeout); //60 secs
            con.setRequestMethod("POST");
            String urlParameters  = "{\"email\":\""+email+"\",\"password\":\""+password+"\"}";
            con.setRequestProperty("Content-Type", "application/json");
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(con.getOutputStream(), "UTF-8"));
            bw.write(urlParameters);
            bw.flush();
            bw.close();
            int status = con.getResponseCode();
            if(status == 200){

                // Add user to logged user
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(con.getInputStream()));
                StringBuffer content = new StringBuffer();
                Auth answer =(new Gson()).fromJson(in.readLine(), Auth.class);
                in.close();
                con.disconnect();
                System.out.println(answer.getToken());
                AuthService.getAuthUser().setToken(answer.getToken());

            //end  Add user to logged user
                return 1;
            }else
                return 0;
        }catch (ConnectException e){
            return -1;
        }
    }

    public static int register(String email, String password, String passwordConfirmed,  String name, String surname, String birthday, boolean cgu) throws IOException {
        try{
            URL url = new URL(GlobalData.url+"/auth/register");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setDoOutput(true);
            con.setConnectTimeout(GlobalData.timeout); //60 secs
            con.setReadTimeout(GlobalData.timeout); //60 secs
            con.setRequestMethod("POST");
            String urlParameters  = "{\"email\":\""+email+"\",\"password\":\""+password+"\",\"name\":\""+name+"\",\"firstname\":\""+surname+"\",\"date\":\""+birthday+"\"}";
            //
            con.setRequestProperty("Content-Type", "application/json");
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(con.getOutputStream(), "UTF-8"));
            bw.write(urlParameters);
            bw.flush();
            bw.close();
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
