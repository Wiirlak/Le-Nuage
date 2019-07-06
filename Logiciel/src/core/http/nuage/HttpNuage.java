package core.http.nuage;

import com.google.gson.Gson;
import core.http.auth.Auth;
import core.model.AuthService;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.ArrayList;

public class HttpNuage {

    private static final String apiUrl = "http://localhost:3000";

    public static ArrayList<Nuage> getNuages(String [] nuages) throws IOException {
        try {
            ArrayList<Nuage> nuageList = new ArrayList<>();
            for (String nuageId : nuages) {
                URL url = new URL(apiUrl + "/nuage/" + nuageId);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestProperty ("x-access-token", AuthService.getAuthUser().getToken());
                con.setRequestMethod("GET");
                con.setConnectTimeout(60000); //60 secs
                con.setReadTimeout(60000); //60 secs
                int status = con.getResponseCode();
                if (status == 200) {
                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(con.getInputStream()));
                    nuageList.add((new Gson()).fromJson(in.readLine(), Nuage.class));
                    in.close();
                    con.disconnect();
                    return nuageList;
                } else {
                    return new ArrayList<>();
                }


            }

        } catch (ConnectException e) {
            return new ArrayList<>();
        }
        return new ArrayList<>();
    }


    public static boolean deleteNuage(String id){
        return true;
    }



    public static int rename(String id, String name){
        try{
            URL url = new URL(apiUrl+"/nuage");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setDoOutput(true);
            con.setConnectTimeout(60000); //60 secs
            con.setReadTimeout(60000); //60 secs
            con.setRequestMethod("PUT");
            String urlParameters  = "{\"id\":\""+id+"\",\"name\":\""+name+"\"}";
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
        }catch (ConnectException e){
            return -1;
        } catch (ProtocolException | MalformedURLException e) {
            return -1;
        } catch (IOException e) {
            return -1;
        }
        return -1;
    }
}
