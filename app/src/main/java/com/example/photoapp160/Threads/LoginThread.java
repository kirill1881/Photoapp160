package com.example.photoapp160.Threads;

import android.os.StrictMode;
import android.util.Log;

import com.example.photoapp160.helpers.Constants;
import com.example.photoapp160.helpers.UserBody;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginThread extends Thread {

    private UserBody userBody;
    private UserBody userBodyFromServer;
    private String postResult = null;
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    public UserBody isIfSuccses() {
        return userBodyFromServer;
    }

    public LoginThread(UserBody userBody){
        this.userBody = userBody;
    }

    @Override
    public void run(){
        StrictMode.ThreadPolicy gfgPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(gfgPolicy);

        Charset charset = Charset.forName(StandardCharsets.UTF_8.name());

        OkHttpClient client = new OkHttpClient();
        try {
            RequestBody formBody = new FormBody.Builder(charset)
                    .add("login", userBody.getLogin())
                    .add("password", userBody.getPassword())
                    .build();
            Request request = new Request.Builder()
                    .url(Constants.loginUrl)
                    .post(formBody)
                    .build();

            Response response = client.newCall(request).execute();
            postResult =  response.body().string();
            Log.e("post result", postResult);
        } catch (Exception e) {
            Log.d("Tag", e.toString());
            e.printStackTrace();
        }

        if (postResult!=null){
            if (!postResult.equals("")) {
                try {
                    JSONObject jsonObject = new JSONObject(postResult);
                    userBodyFromServer = new UserBody(
                            jsonObject.get("name").toString()
                            ,jsonObject.get("lastName").toString()
                            ,jsonObject.get("login").toString()
                            ,jsonObject.get("password").toString()
                            ,jsonObject.get("disc").toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

