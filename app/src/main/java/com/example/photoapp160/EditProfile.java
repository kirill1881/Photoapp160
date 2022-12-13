package com.example.photoapp160;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.photoapp160.Threads.EditProfileThread;
import com.example.photoapp160.helpers.UserBody;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfile extends AppCompatActivity {

    private ImageView imageView;
    private EditText name, lastName, disc;
    private Button save;
    private String url = "";

    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;

    private static final int CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        imageView = findViewById(R.id.imageView);
        name = findViewById(R.id.name);
        lastName = findViewById(R.id.lastName);
        disc = findViewById(R.id.disc);

        save = findViewById(R.id.save);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(EditProfile.this);
/*
        disc.setText(sharedPreferences.getAll().get("disc").toString());
*/
        name.setText(sharedPreferences.getAll().get("name").toString());
        lastName.setText(sharedPreferences.getAll().get("lastName").toString());

        if (sharedPreferences.getAll().get("mainPhoto")!=null){
            downloadBytes(sharedPreferences.getAll().get("mainPhoto").toString(), imageView);
        }

        SharedPreferences.Editor editor = sharedPreferences.edit();

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/**");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), CODE);
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserBody userBody;
                if (!url.equals("")) {
                    userBody = new UserBody(name.getText().toString(), lastName.getText().toString(),
                            sharedPreferences.getAll().get("login").toString(), disc.getText().toString(), url);
                    editor.putString("mainPhoto",url);
                }else {
                    userBody = new UserBody(name.getText().toString(), lastName.getText().toString(),
                            sharedPreferences.getAll().get("login").toString(), disc.getText().toString(),
                            "1");
                }
                editor.putString("name", name.getText().toString());
                editor.putString("lastName", lastName.getText().toString());
                editor.putString("disc",disc.getText().toString());

                editor.apply();

                EditProfileThread editProfileThread = new EditProfileThread(userBody);
                editProfileThread.start();

                while (editProfileThread.isAlive());

                Intent intent = new Intent(EditProfile.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void downloadBytes(String url, ImageView circleImageView){
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
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==CODE && data!=null){
            Uri selectImageUri = data.getData();
            if (selectImageUri!=null){
                imageView.setImageURI(selectImageUri);
                FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();

                Bitmap bitmap = ((BitmapDrawable) (imageView.getDrawable())).getBitmap();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 10, baos);
                byte[] bytes = baos.toByteArray();
                StorageReference storageReference = FirebaseStorage.getInstance().getReference();
                StorageReference storageReference1 = storageReference.
                        child(System.currentTimeMillis()+" ");
                UploadTask uploadTask = storageReference1.putBytes(bytes);
                Task<Uri> task = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        return storageReference1.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        url = task.getResult().toString().substring(76,89);
                    }
                });
            }
        }
    }
}