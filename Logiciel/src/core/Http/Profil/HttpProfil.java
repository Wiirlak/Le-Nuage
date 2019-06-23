package core.Http.Profil;

import com.google.gson.Gson;
import core.Model.AuthService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;

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
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            Profil answer =(new Gson()).fromJson(in.readLine(), Profil.class);
            in.close();
            con.disconnect();
            return answer;
        }catch(ConnectException e ){
            System.out.println("error");
        }
        return null;
    }

    public static boolean updateProfil(String name, String email, String password){
        return true;
    }

}
