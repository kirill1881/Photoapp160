package com.example.photoapp160;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {


    private TextView name, disc;
    private Button editProfile, myPhotos, othersPhotos;
    private CircleImageView circleImageView;

    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        if (sharedPreferences.getAll().get("name")==null){
            Intent intent = new Intent(MainActivity.this, ChooseType.class);
            startActivity(intent);
            finish();
        }else {

            name = findViewById(R.id.name);
            disc = findViewById(R.id.disc);

            editProfile = findViewById(R.id.editProfile);
            myPhotos = findViewById(R.id.myPhotos);
            othersPhotos = findViewById(R.id.othersPhotos);

            circleImageView = findViewById(R.id.circleImageView);

            name.setText(sharedPreferences.getAll().get("name") + " " + sharedPreferences.getAll().get("lastName"));
/*
            disc.setText(sharedPreferences.getAll().get("disc").toString());
*/

            if (sharedPreferences.getAll().get("mainPhoto") != null) {
                downloadBytes(sharedPreferences.getAll().get("mainPhoto").toString(), circleImageView);
            }

            editProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, EditProfile.class);
                    startActivity(intent);
                }
            });
        }

    }

    public void downloadBytes(String url, CircleImageView circleImageView){
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        StorageReference storageReference1 = storageReference.child(url);

        long MAXBYTES = 10240*10240;
        storageReference1.getBytes(MAXBYTES).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                circleImageView.setImageBitmap(bitmap);
            }
        });
    }
}