package core.Http.Nuage;

import com.google.gson.Gson;
import core.Model.AuthService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
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
                    String inputLine;
                    StringBuffer content = new StringBuffer();
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
}
