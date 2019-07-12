package core.http.profil;

import com.google.gson.Gson;
import core.data.GlobalData;
import core.model.AuthService;

import java.io.*;
import java.net.*;

public class HttpProfil {


    public static Profil getProfil() throws IOException {
        try{
            URL url = new URL(GlobalData.url+"/auth/verify");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty ("x-access-token", AuthService.getAuthUser().getToken());
            con.setConnectTimeout(GlobalData.timeout); //60 secs
            con.setReadTimeout(GlobalData.timeout); //60 secs
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
            URL url = new URL(GlobalData.url+"/user");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setDoOutput(true);
            con.setConnectTimeout(GlobalData.timeout); //60 secs
            con.setReadTimeout(GlobalData.timeout); //60 secs
            con.setRequestMethod("PUT");
            String urlParameters  = "{\"id\":\""+id+"\",\"email\":\""+email+"\"}";
            con.setRequestProperty ("x-access-token", AuthService.getAuthUser().getToken());
            con.setRequestProperty("Content-Type", "application/json");
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(con.getOutputStream(), "UTF-8"));
            bw.write(urlParameters);
            bw.flush();
            bw.close();
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
