package com.example.photoapp160.Threads;

import com.example.photoapp160.helpers.Constants;
import com.example.photoapp160.helpers.UserBody;
import com.google.firebase.crashlytics.internal.model.CrashlyticsReport;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class LoginThread extends Thread{

    private UserBody userBody;
    private String user;
    private UserBody userBody1;

    public UserBody isIfSuccses() {
        return userBody1;
    }

    public LoginThread(UserBody userBody){
        this.userBody = userBody;
    }

    @Override
    public void run(){
        HttpUriRequest httpUriRequest =
                RequestBuilder.post(Constants.loginUrl)
                        .addParameter("login", userBody.getLogin())
                        .addParameter("password", userBody.getPassword())
                        .setCharset(StandardCharsets.UTF_8)
                        .build();

        HttpClient httpClient = HttpClients.createDefault();
        try {
            HttpResponse httpResponse = httpClient.execute(httpUriRequest);
            user = (new BufferedReader(new
                    InputStreamReader(httpResponse.getEntity().getContent()))
                    .readLine());
            if (user!=null){
                if (!user.equals("")) {
                    JSONObject jsonObject = new JSONObject(user);
                    userBody1 = new UserBody(jsonObject.get("name").toString(), jsonObject.get("lastName").toString(),
                            jsonObject.get("login").toString(), jsonObject.get("password").toString(),
                            jsonObject.get("disc").toString());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
