package com.example.photoapp160.Threads;

import android.os.StrictMode;
import android.util.Log;

import com.example.photoapp160.helpers.Constants;
import com.example.photoapp160.helpers.UserBody;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegistrationThread extends Thread{

    private UserBody userBody;
    private boolean ifSuccses;

    public boolean isIfSuccses() {
        return ifSuccses;
    }

    public RegistrationThread(UserBody userBody){
        this.userBody = userBody;
    }

    @Override
    public void run(){
        /*HttpUriRequest httpUriRequest =
                RequestBuilder.post(Constants.registerUrl)
                        .addParameter("login", userBody.getLogin())
                        .addParameter("password", userBody.getPassword())
                        .addParameter("name", userBody.getName())
                        .addParameter("lastName", userBody.getLastName())
                        .addParameter("disc", userBody.getDisc())
                        .addParameter("mainPhoto", "0")
                        .setCharset(StandardCharsets.UTF_8)
                        .build();

        HttpClient httpClient = HttpClients.createDefault();*/

        StrictMode.ThreadPolicy gfgPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(gfgPolicy);

        Charset charset = Charset.forName(StandardCharsets.UTF_8.name());

        OkHttpClient client = new OkHttpClient();
        try {
            RequestBody requestBody = new FormBody.Builder(charset)
                    .add("login", userBody.getLogin())
                    .add("password", userBody.getPassword())
                    .add("name", userBody.getName())
                    .add("lastName", userBody.getLastName())
//                    .add("disc", userBody.getDisc())
                    .add("mainPhoto", "0")
                    .build();

            Request request = new Request.Builder()
                    .url(Constants.registerUrl)
                    .post(requestBody)
                    .build();

            Response response = client.newCall(request).execute();
            ifSuccses = Boolean.parseBoolean(response.body().toString());
            Log.e("if", String.valueOf(ifSuccses));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
