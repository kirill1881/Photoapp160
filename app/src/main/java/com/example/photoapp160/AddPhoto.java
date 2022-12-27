package com.example.photoapp160;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.photoapp160.Threads.AddPhotoThread;
import com.example.photoapp160.helpers.PhotoBody;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;

public class AddPhoto extends AppCompatActivity {

    private static final int CODE = 101;
    private ImageView imageView;
    private TextView textView;
    private Button button;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        imageView = findViewById(R.id.imageView);
        button = findViewById(R.id.button);
        textView = findViewById(R.id.textView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/**");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), CODE);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(AddPhoto.this);
                PhotoBody photoBody = new PhotoBody(textView.getText().toString(), url);
                AddPhotoThread addPhotoThread = new AddPhotoThread(photoBody, sharedPreferences.getAll()
                        .get("login").toString());
                addPhotoThread.start();
                finish();
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