package com.example.photoapp160.Threads;

import android.os.StrictMode;
import android.util.Log;

import com.example.photoapp160.EditProfile;
import com.example.photoapp160.helpers.Constants;
import com.example.photoapp160.helpers.UserBody;


import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class EditProfileThread extends Thread{

    private UserBody userBody;

    public EditProfileThread(UserBody userBody){
        this.userBody = userBody;
    }

    @Override
    public void run(){
        /*HttpUriRequest httpUriRequest =
                RequestBuilder.post(Constants.loginUrl)
                        .addParameter("name", userBody.getName())
                        .addParameter("lastName", userBody.getLastName())
                        .addParameter("disc", userBody.getDisc())
                        .addParameter("login", userBody.getLogin())
                        .addParameter("mainPhoto", userBody.getMainPhoto())
                        .setCharset(StandardCharsets.UTF_8)
                        .build();


        HttpClient httpClient = HttpClients.createDefault();*/

        StrictMode.ThreadPolicy gfgPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(gfgPolicy);

        Charset charset = Charset.forName(StandardCharsets.UTF_8.name());

        OkHttpClient client = new OkHttpClient();
        try {
            RequestBody formBody = new FormBody.Builder(charset)
                    .add("name", userBody.getName())
                    .add("lastName", userBody.getLastName())
                    .add("login", userBody.getLogin())
                    .add("mainPhoto", userBody.getMainPhoto())
                    .build();

            Request request = new Request.Builder()
                    .url(Constants.editUrl)
                    .post(formBody)
                    .build();

            Response response = client.newCall(request).execute();
        } catch (Exception e) {
            Log.d("Tag", e.toString());
            e.printStackTrace();
        }
    }
}
