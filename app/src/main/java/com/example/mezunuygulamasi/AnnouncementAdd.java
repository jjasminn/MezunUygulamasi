package com.example.mezunuygulamasi;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.InputStream;


public class AnnouncementAdd extends AppCompatActivity {
    private EditText titleE;

    Uri photoUri;
    private EditText contentE;
    private Button addButton;
    private ImageView image;
    private FirebaseAuth mAuth;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                photoUri = result.getUri();
                try{

                    InputStream stream= getContentResolver().openInputStream(photoUri);
                    Bitmap bitmap = BitmapFactory.decodeStream(stream);
                    image.setImageBitmap(bitmap);

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcement_add);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        image=findViewById(R.id.photo);
        titleE = findViewById(R.id.title);
        contentE = findViewById(R.id.description);
        addButton = findViewById(R.id.addButton);
        mAuth = FirebaseAuth.getInstance();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = titleE.getText().toString().trim();
                String description = contentE.getText().toString().trim();

                if (title.isEmpty() || description.isEmpty()) {

                    Toast.makeText(AnnouncementAdd.this, "Bos birakmayiniz.", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    String filename = title+".jpg";
                    StorageReference imageRef = storageRef.child("announcementImages/" + filename); // images klasörü altına kaydedilecek


                    imageRef.putFile(photoUri)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                    Announcement announcement = new Announcement(photoUri.toString(), title, description);
                                    db.collection("Announcement")
                                            .add(announcement)
                                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                @Override
                                                public void onSuccess(DocumentReference documentReference) {
                                                    Toast.makeText(AnnouncementAdd.this, "Duyuru eklendi!", Toast.LENGTH_SHORT).show();
                                                    Intent intent = new Intent(AnnouncementAdd.this, Announcements.class);
                                                    startActivity(intent);
                                                    finish();
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(AnnouncementAdd.this, "Haber eklenirken bir hata oluştu: " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {

                                    Toast.makeText(AnnouncementAdd.this,"Basarisiz",Toast.LENGTH_SHORT).show();
                                }
                            });


                }
            }
        });



        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean pick = true;
                if (pick) {
                    if (!checkCameraPermission()) {
                        requestCameraPermission();
                    } else {
                        PickImage();
                    }
                } else {
                    if (pick) {
                        if (!checkStoragePermission()) {
                            requestStoragePermission();
                        } else {
                            PickImage();
                        }
                    }

                }
            }
            private void PickImage() {

                CropImage.activity().start(AnnouncementAdd.this);
            }

            private void requestStoragePermission() {
                requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},100);


            }

            private void requestCameraPermission() {
                requestPermissions(new String[]{android.Manifest.permission.CAMERA,android.Manifest.permission.WRITE_EXTERNAL_STORAGE},100);
            }

            private boolean checkStoragePermission() {
                boolean res2 = ContextCompat.checkSelfPermission(AnnouncementAdd.this,android.Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED;
                return res2;
            }
            private boolean checkCameraPermission() {
                boolean res1 = ContextCompat.checkSelfPermission(AnnouncementAdd.this,android.Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED;
                boolean res2 = ContextCompat.checkSelfPermission(AnnouncementAdd.this,android.Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED;
                return res1&&res2;
            }
        });
    }
}