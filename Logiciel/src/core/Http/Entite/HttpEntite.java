package core.Http.Entite;

import core.Model.AuthService;
import okhttp3.*;
import java.io.*;

/**
 * This utility class provides an abstraction layer for sending multipart HTTP
 * POST requests to a web server.
 * @author www.codejava.net
 *
 */
public class HttpEntite {

    public static void upload(String file, String parentId) throws IOException {
        OkHttpClient client = new OkHttpClient();
        /*MediaType mediaType = MediaType.parse("multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW");
        RequestBody body = RequestBody.create(mediaType, "------WebKitFormBoundary7MA4YWxkTrZu0gW\r\nContent-Disposition: form-data; name=\"somefile\"; filename=\""+file+"\"\r\n\r\n------WebKitFormBoundary7MA4YWxkTrZu0gW\r\nContent-Disposition: form-data; name=\"parentId\"\r\n\r\n"+parentId+"\r\n------WebKitFormBoundary7MA4YWxkTrZu0gW--");
        Request request = new Request.Builder()
                .url("http://localhost:3000/entity/upload")
                .post(body)
                .addHeader("content-type", "multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW")
                .addHeader("x-access-token", AuthService.getAuthUser().getToken())
                .addHeader("Cache-Control", "no-cache")
                .addHeader("Connection", "keep-alive")
                .build();*/
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("someFile", file)
                .addFormDataPart("parentId", parentId)
                .build();

        Request request = new Request.Builder()
                .url("http://localhost:3000/entity/upload")
                .addHeader("x-access-token", AuthService.getAuthUser().getToken())
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();
    }
}