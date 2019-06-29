package core.Http.Entite;

import com.google.gson.Gson;
import core.Http.Apple.Apple;
import core.Http.Nuage.Nuage;
import core.Http.Profil.Profil;
import core.Model.AuthService;
import core.Model.Entity;
import javafx.scene.control.TreeItem;
import okhttp3.*;
import java.io.*;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * This utility class provides an abstraction layer for sending multipart HTTP
 * POST requests to a web server.
 * @author www.codejava.net
 *
 */
public class HttpEntite {

    private static final String apiUrl = "http://localhost:3000";



    public static void upload(String file, String parentId) throws IOException {
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("somefile", file,RequestBody.create(MediaType.parse("text/plain"), new File(file)))
                .addFormDataPart("parentId", parentId)
                .build();

        Request request = new Request.Builder()
                .url("http://localhost:3000/entity/upload")
                .addHeader("x-access-token", AuthService.getAuthUser().getToken())
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();
        response.close();
        //System.out.println(response);
    }

    public static void threadT(String file, String parentId) {
        final String threatname = String.format("%.3f",  System.currentTimeMillis() / 1000.0);;
        Thread t = new Thread() {
            public void run() {
                try {
                    upload(file, parentId);
                    Set<Thread> setOfThread = Thread.getAllStackTraces().keySet();
                    for(Thread thread : setOfThread){
                        if(thread.getName()==threatname){
                            System.out.println("End : "+threatname);
                            thread.interrupt();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        t.setName(threatname);
        System.out.println("Start :  "+threatname);
        t.start();
    }


    public static Entity[] getTreeByParentId(String parentId){
        try{
            URL url = new URL(apiUrl+"/tree/"+parentId);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            Entity[] answer =  new Entity[0];
            con.setConnectTimeout(60000); //60 secs
            con.setReadTimeout(60000); //60 secs
            con.setRequestProperty ("x-access-token", AuthService.getAuthUser().getToken());
            int status = con.getResponseCode();
            if ( status != 200)
                throw new IOException();
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
                answer =  new  Gson().fromJson(inputLine, Entity[].class);
            }
            in.close();
            con.disconnect();
            return answer;
        }catch(IOException e ){
            return new Entity[0];
        }
    }

    public static void download(String fileId,String filename){
        try{
            URL url = new URL(apiUrl+"/entity/download?e="+fileId);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty ("x-access-token", AuthService.getAuthUser().getToken());
            con.setConnectTimeout(60000); //60 secs
            con.setReadTimeout(60000); //60 secs
            int status = con.getResponseCode();
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            FileWriter fw = new FileWriter("E:\\Autre\\Ecole\\ESGI\\3AL\\Projet_Annuel\\"+filename);
            fw.write(in.lines().collect(Collectors.joining("\r\n")));
            fw.close();
            in.close();
            con.disconnect();
        }catch(IOException e ){
            System.out.println("error");
        }
    }
}