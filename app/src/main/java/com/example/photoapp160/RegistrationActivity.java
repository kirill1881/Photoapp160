package com.example.photoapp160;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.photoapp160.Threads.RegistrationThread;
import com.example.photoapp160.helpers.UserBody;

public class RegistrationActivity extends AppCompatActivity {

    private EditText name, lastName, password, passwordAgain, login;
    private Button register;
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        name = findViewById(R.id.name);
        lastName = findViewById(R.id.lastName);
        password = findViewById(R.id.password);
        passwordAgain = findViewById(R.id.password_again);
        login = findViewById(R.id.login);

        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(RegistrationActivity.this);

        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = sharedPreferences.edit();


        register = findViewById(R.id.register);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (password.getText().toString().equals(passwordAgain.getText().toString())){
                    if (ifPassword(password.getText().toString())) {
                        UserBody userBody = new UserBody(name.getText().toString(), lastName.getText().toString(),
                                password.getText().toString(), login.getText().toString());

                        RegistrationThread registrationThread = new RegistrationThread(userBody);
                        registrationThread.start();
                        while (registrationThread.isAlive());
                        if (registrationThread.isIfSuccses()){
                            editor.putString("name", userBody.getName());
                            editor.putString("lastName", userBody.getLastName());
                            editor.putString("disc", userBody.getDisc());
                            editor.putString("login", userBody.getLogin());
                            editor.apply();

                            Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }else {
                            textView.setText(R.string.not_correct_login);
                        }
                    }else {
                        textView.setText(R.string.not_correct_passwords);
                    }
                }else {
                    textView.setText(R.string.not_equal_passwords);
                }
            }
        });

    }

    public boolean ifPassword(String str){
        return ifLength(str) && ifLetters(str) && ifNumbers(str) && ifSpChar(str);
    }

    public boolean ifLength(String str){
        return str.length() > 7 && str.length() < 21;
    }

    public boolean ifLetters(String str){
        for (int i = 0; i < str.length(); i++) {
            if (Character.isAlphabetic(str.charAt(i))){
                return true;
            }
        }
        return false;
    }

    public boolean ifNumbers(String str){
        for (int i = 0; i < str.length(); i++) {
            if (Character.isDigit(str.charAt(i))){
                return true;
            }
        }
        return false;
    }

    public boolean ifSpChar(String str){
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))&&!Character.isAlphabetic(str.charAt(i))){
                return false;
            }
        }
        return true;
    }

}