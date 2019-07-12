package core.http.entite;

import com.google.gson.Gson;
import core.controller.ControllerFile;
import core.data.GlobalData;
import core.model.AuthService;
import core.model.Entity;
import javafx.application.Platform;
import okhttp3.*;
import org.apache.commons.io.FileDeleteStrategy;
import plugin.PluginManager;

import java.io.*;
import java.net.*;
import java.util.Set;

public class HttpEntite {


    public static boolean upload(String path, String parentId) throws IOException {
        System.out.println(path);
        OkHttpClient client = new OkHttpClient();
        File file = new File(path);
        if (!file.exists())
            return false;
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("somefile", path,RequestBody.create(MediaType.parse("text/plain"), file))
                .addFormDataPart("parentId", parentId)
                .build();

        Request request = new Request.Builder()
                .url(GlobalData.url+"/entity/upload")
                .addHeader("x-access-token", AuthService.getAuthUser().getToken())
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();
        response.close();
        //System.out.println(response);
        return true;
    }

    public static void threadT(String file, String parentId, ControllerFile c) {
        final String threatname = String.format("%.3f",  System.currentTimeMillis() / 1000.0);
        Thread t = new Thread() {
            public void run() {
                try {
                    upload(file, parentId);
                    PluginManager a = new PluginManager();
                    a.runSelectedJar("lnOpen");
                    Set<Thread> setOfThread = Thread.getAllStackTraces().keySet();
                    for(Thread thread : setOfThread){
                        if(thread.getName()==threatname){
                            System.out.println("End : "+threatname);
                            Platform.runLater( () ->c.reload());

                            thread.interrupt();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
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
            URL url = new URL(GlobalData.url+"/entity/all?parentid="+parentId);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            Entity[] answer =  new Entity[0];
            con.setConnectTimeout(GlobalData.timeout); //60 secs
            con.setReadTimeout(GlobalData.timeout); //60 secs
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

    public static void download(String fileId,String filename, String output ,ControllerFile c){
        if (download(fileId, filename, output))
            Platform.runLater( () ->c.reload());
    }

    public static void threadDownload(String fileId,String filename, String output ,ControllerFile c) {
        final String threatname = String.format("%.3f",  System.currentTimeMillis() / 1000.0);
        Thread t = new Thread() {
            public void run() {
            download(fileId,filename,output,c);
                PluginManager a = new PluginManager();
                try {
                    a.runSelectedJar("download");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        t.setName(threatname);
        System.out.println("Start :  "+threatname);
        t.start();
    }

    public static boolean download(String fileId,String filename, String output){
        try{
            System.out.println(output);
            if(output.equals(""))
                return false;
            URL url = new URL(GlobalData.url+"/entity/download?e="+fileId);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty ("x-access-token", AuthService.getAuthUser().getToken());
            con.setConnectTimeout(GlobalData.timeout); //60 secs
            con.setReadTimeout(GlobalData.timeout); //60 secs
            int status = con.getResponseCode();
            if ( status == 200) {
                byte[] buffer = new byte[4096];
                int n;

                OutputStream t = new FileOutputStream(output + "\\" + filename);
                while ((n = con.getInputStream().read(buffer)) != -1) {
                    t.write(buffer, 0, n);
                }
                t.close();
            }
        }catch(IOException e ){
            e.printStackTrace();
            System.out.println("error");
            return false;
        }
        return true;
    }

    public static StringBuffer getOne(String fileId){
        try{
            URL url = new URL(GlobalData.url+"/entity/search?e="+fileId);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestProperty ("x-access-token", AuthService.getAuthUser().getToken());
            con.setRequestMethod("GET");
            con.setConnectTimeout(GlobalData.timeout); //20 secs
            con.setReadTimeout(GlobalData.timeout); //20 secs
            if (con.getResponseCode() != 200)
                return null;
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
        }catch(IOException e ){
            return null;
        }
    }


    public static int createFolder(String name, String parentId){
        try{
            URL url = new URL(GlobalData.url+"/entity");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setDoOutput(true);
            con.setConnectTimeout(GlobalData.timeout); //60 secs
            con.setReadTimeout(GlobalData.timeout); //60 secs
            con.setRequestMethod("POST");
            String urlParameters  = "{\"name\":\""+name+"\",\"type\":\"folder\",\"parentId\": \""+parentId+"\"}";
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




    public static StringBuffer getLastEntityByNameAndParentId(String parentId, String name) {
        try {
            name= name.replace(" ","%20");
            URL url = new URL(GlobalData.url + "/entity/last?parentid=" + parentId + "&name=" + name);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setDoOutput(true);
            con.setConnectTimeout(GlobalData.timeout); //60 secs
            con.setReadTimeout(GlobalData.timeout); //60 secs
            con.setRequestMethod("GET");
            con.setRequestProperty("x-access-token", AuthService.getAuthUser().getToken());
            if (con.getResponseCode() == 200) {
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
        } catch (IOException e) {
            return  null;
        }
        return null;
    }

}