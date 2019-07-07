package core.http.nuage;


import com.google.gson.Gson;
import core.model.AuthService;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class HttpNuage {

    private static final String apiUrl = "http://localhost:3000";

    public static Nuage[] getNuages() throws IOException {
        try {
            ArrayList<Nuage> nuageList = new ArrayList<>();
            URL url = new URL(apiUrl + "/nuage/");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestProperty ("x-access-token", AuthService.getAuthUser().getToken());
            con.setRequestMethod("GET");
            con.setConnectTimeout(60000); //60 secs
            con.setReadTimeout(60000); //60 secs
            int status = con.getResponseCode();
            if (status == 200) {
                /*BufferedReader in = new BufferedReader(
                        new InputStreamReader(con.getInputStream()));
                String t =in.readLine();
                System.out.println(t);
                nuageList.add((new Gson()).fromJson(t, Nuage.class));
                in.close();*/
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String output;
                StringBuilder sb = new StringBuilder();
                while ((output = bufferedReader.readLine()) != null) {
                    sb.append(output);
                }
                System.out.println("CONTENT / " + sb.toString());
                Gson gson = new Gson();
                Nuage[] json = gson.fromJson(sb.toString(), Nuage[].class);
                con.disconnect();
                return json;
            } else {
                return new Nuage[0];
            }
        } catch (ConnectException e) {
            return new Nuage[0];
        }
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


    public static int createNuage(String name){
        try{
            URL url = new URL(apiUrl+"/nuage/");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setDoOutput(true);
            con.setConnectTimeout(60000); //60 secs
            con.setReadTimeout(60000); //60 secs
            con.setRequestMethod("POST");
            String urlParameters  = "{\"name\":\""+name+"\"}";
            con.setRequestProperty ("x-access-token", AuthService.getAuthUser().getToken());
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
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 1;
    }
}
