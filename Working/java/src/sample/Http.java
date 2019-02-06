package sample;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.net.URL;

public class Http {
    private static final String apiUrl = "http://localhost:3000";

    public Http() {

    }

    public StringBuffer getApple(String id) throws IOException {
        URL url = new URL(apiUrl+"/pommes/"+id);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setConnectTimeout(60000); //60 secs
        con.setReadTimeout(60000); //60 secs
        int status = con.getResponseCode();
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        con.disconnect();
        return content;
    }

    public StringBuffer getApples() throws IOException {
        URL url = new URL(apiUrl+"/pommes/");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setConnectTimeout(60000); //60 secs
        con.setReadTimeout(60000); //60 secs
        int status = con.getResponseCode();
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        con.disconnect();
        return content;
    }

    public boolean deleteApple(String id) throws IOException {
        URL url = new URL(apiUrl+"/pommes/"+id);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("DELETE");
        int status = con.getResponseCode();
        if(status == 200)
            return true;
        else
            return false;
    }


    public boolean createApple(String name, int pepins) throws IOException {
        URL url = new URL(apiUrl+"/pommes/new");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setDoOutput(true);
        con.setConnectTimeout(60000); //60 secs
        con.setReadTimeout(60000); //60 secs
        con.setRequestMethod("POST");
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes("name="+name+"&pepins="+pepins);
        wr.flush();
        wr.close();
        int status = con.getResponseCode();
        System.out.println(status);
        if(status == 200)
            return true;
        else
            return false;
    }

    public boolean updateApple(String id, String name, int pepins) throws IOException{
        URL url = new URL(apiUrl+"/pommes/"+id);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setDoOutput(true);
        con.setConnectTimeout(60000); //60 secs
        con.setReadTimeout(60000); //60 secs
        con.setRequestMethod("PUT");
        //con.setRequestProperty("Content-Type", "application/json");
        //con.setRequestProperty("Accept", "application/json");
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes("{\"name\":\""+name+"\",\"pepins\":"+pepins+"}");
        wr.flush();
        wr.close();
        System.out.println(con.getInputStream());
        int status = con.getResponseCode();
        System.out.println(status);
        if(status == 200)
            return true;
        else
            return false;
    }






}
