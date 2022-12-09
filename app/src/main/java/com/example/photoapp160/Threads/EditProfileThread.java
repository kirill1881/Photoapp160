package com.example.photoapp160.Threads;

import com.example.photoapp160.EditProfile;
import com.example.photoapp160.helpers.Constants;
import com.example.photoapp160.helpers.UserBody;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class EditProfileThread extends Thread{

    private UserBody userBody;

    public EditProfileThread(UserBody userBody){
        this.userBody = userBody;
    }

    @Override
    public void run(){
        HttpUriRequest httpUriRequest =
                RequestBuilder.post(Constants.loginUrl)
                        .addParameter("name", userBody.getName())
                        .addParameter("lastName", userBody.getLastName())
                        .addParameter("disc", userBody.getDisc())
                        .addParameter("login", userBody.getLogin())
                        .addParameter("mainPhoto", userBody.getMainPhoto())
                        .setCharset(StandardCharsets.UTF_8)
                        .build();

        HttpClient httpClient = HttpClients.createDefault();
    }
}
