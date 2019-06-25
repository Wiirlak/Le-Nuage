package core.Http.Entite;

import core.Model.AuthService;
import okhttp3.*;
import java.io.*;
import java.util.Set;

/**
 * This utility class provides an abstraction layer for sending multipart HTTP
 * POST requests to a web server.
 * @author www.codejava.net
 *
 */
public class HttpEntite {

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
}