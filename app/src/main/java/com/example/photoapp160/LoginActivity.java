package com.example.photoapp160;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.photoapp160.Threads.LoginThread;
import com.example.photoapp160.helpers.UserBody;

public class LoginActivity extends AppCompatActivity {

    private TextView result;
    private EditText login, password;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = findViewById(R.id.login);
        password = findViewById(R.id.password);
        result = findViewById(R.id.textView);
        button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String textLogin = login.getText().toString();
               String textPassword = password.getText().toString();

                UserBody userBody = new UserBody(textLogin, textPassword);

                LoginThread loginThread = new LoginThread(userBody);
                loginThread.start();

                while (loginThread.isAlive());

                UserBody userBodyFin = loginThread.isIfSuccses();
                if (userBodyFin==null){
                    result.setText(R.string.not_correct_loginization);
                }else {

                }
            }
        });

    }
}