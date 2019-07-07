package core.http.profil;

import com.google.gson.Gson;
import core.model.AuthService;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

public class HttpProfil {


    private static final String apiUrl = "http://localhost:3000";

    public static Profil getProfil() throws IOException {
        try{
            URL url = new URL(apiUrl+"/auth/verify");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty ("x-access-token", AuthService.getAuthUser().getToken());
            con.setConnectTimeout(60000); //60 secs
            con.setReadTimeout(60000); //60 secs
            int status = con.getResponseCode();
            if(status == 200){
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(con.getInputStream()));
                Profil answer =(new Gson()).fromJson(in.readLine(), Profil.class);
                in.close();
                con.disconnect();
                return answer;
            }

        }catch(ConnectException e ){
            System.out.println("error");
        }
        return null;
    }

    public static int updateProfilEmail(String id, String email/*, String password*/)
    {
        try{
            URL url = new URL(apiUrl+"/user");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setDoOutput(true);
            con.setConnectTimeout(60000); //60 secs
            con.setReadTimeout(60000); //60 secs
            con.setRequestMethod("PUT");
            String urlParameters  = "{\"id\":\""+id+"\",\"email\":\""+email+"\"}";
            con.setRequestProperty ("x-access-token", AuthService.getAuthUser().getToken());
            con.setRequestProperty("Content-Type", "application/json");
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.flush();
            wr.close();
            int status = con.getResponseCode();
            if(status == 200){
                con.disconnect();
                //end  Add user to logged user
                return 1;
            }
        } catch (ProtocolException | MalformedURLException e) {
            return -1;
        } catch (IOException e){
            return -1;
        }
        return -1;
    }

}
