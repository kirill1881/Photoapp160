package com.example.photoapp160.Threads;

import com.example.photoapp160.helpers.Constants;
import com.example.photoapp160.helpers.UserBody;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.HttpClients;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

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
        HttpUriRequest httpUriRequest =
                RequestBuilder.post(Constants.registerUrl)
                        .addParameter("login", userBody.getLogin())
                        .addParameter("password", userBody.getPassword())
                        .addParameter("name", userBody.getName())
                        .addParameter("lastName", userBody.getLastName())
                        .addParameter("disc", userBody.getDisc())
                        .addParameter("mainPhoto", "0")
                        .setCharset(StandardCharsets.UTF_8)
                        .build();

        HttpClient httpClient = HttpClients.createDefault();
        try {
            HttpResponse httpResponse = httpClient.execute(httpUriRequest);
            ifSuccses = Boolean.parseBoolean(new BufferedReader(new
                    InputStreamReader(httpResponse.getEntity().getContent()))
                    .readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
