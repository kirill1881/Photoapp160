package com.example.photoapp160.Threads;

import com.example.photoapp160.helpers.UserBody;

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

    }
}
