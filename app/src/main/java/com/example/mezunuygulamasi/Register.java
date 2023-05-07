package com.example.mezunuygulamasi;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.navigation.ui.AppBarConfiguration;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;

import org.checkerframework.checker.units.qual.A;

import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class Register extends AppCompatActivity{

    private AppBarConfiguration appBarConfiguration;
    int day, month, year, hour, minute;
    int myday, myMonth, myYear, myHour, myMinute;

    Uri resultUri;
    boolean isPhotoUploaded;
    ImageView photo;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(getApplicationContext(), Profil.class);
            startActivity(intent);
            finish();

        }
    }
    @Override
        protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                if (resultCode == RESULT_OK) {
                     resultUri = result.getUri();
                    try{
                        isPhotoUploaded=true;
                        InputStream stream= getContentResolver().openInputStream(resultUri);
                        Bitmap bitmap = BitmapFactory.decodeStream(stream);
                        photo.setImageBitmap(bitmap);
    
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
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();


        isPhotoUploaded=false;

         db = FirebaseFirestore.getInstance();

        mAuth = FirebaseAuth.getInstance();

        setContentView(R.layout.activity_register);
        EditText editname = (EditText) findViewById(R.id.name);
        EditText editsurname = (EditText) findViewById(R.id.surname);
        EditText editmail= (EditText) findViewById(R.id.mail);
        EditText editpassword = (EditText) findViewById(R.id.password);
        Button dateEnter = (Button) findViewById(R.id.date_enter);
        Button dateFinish = (Button) findViewById(R.id.date_finish);
        EditText editphone = (EditText) findViewById(R.id.phone);
        Button signupButton = (Button) findViewById(R.id.signupButton);
        TextView goToLogin = (TextView) findViewById(R.id.goLogin);
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
         photo = (ImageView) findViewById(R.id.photo);


        String mail,password,name, surname,phone,dateLicenceEnter,dateLicenceFinish;




        signupButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                final String mail,password,name, surname,phone,dateLicenceEnter,dateLicenceFinish;
                mail= String.valueOf(editmail.getText());
                password= String.valueOf(editpassword.getText());
                name = String.valueOf(editname.getText());
                surname = String.valueOf(editsurname.getText());
                phone = String.valueOf(editphone.getText());
                dateLicenceEnter=String.valueOf(dateEnter.getText());
                dateLicenceFinish=String.valueOf(dateFinish.getText());
                progressBar.setVisibility(View.VISIBLE);
                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(surname) || TextUtils.isEmpty(mail)
                        || TextUtils.isEmpty(password) || TextUtils.isEmpty(dateLicenceEnter)
                        || TextUtils.isEmpty(dateLicenceFinish) || TextUtils.isEmpty(phone)||!isPhotoUploaded) {
                    Toast.makeText(Register.this, "Please fill in all required fields", Toast.LENGTH_SHORT).show();
                }
                else{



                    mAuth.createUserWithEmailAndPassword(mail, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                FirebaseUser user = mAuth.getCurrentUser();
                                String uid = user.getUid();
                                User newUser = new User(uid, name, surname, mail,  dateLicenceEnter, dateLicenceFinish,phone);

                                StorageReference photoRef = storageRef.child("users/" + newUser.getUid() + "/profilePhoto.jpg");
                                photoRef.putFile(resultUri).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                                    @Override
                                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                                        if (!task.isSuccessful()) {
                                            throw task.getException();
                                        }

                                        return photoRef.getDownloadUrl();
                                    }
                                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Uri> task) {
                                        if (task.isSuccessful()) {
                                            Uri photoUrl = task.getResult();
                                            newUser.setPhotoUrl(photoUrl.toString());

                                            db.collection("users").document(newUser.getEmail())
                                                    .set(newUser)
                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {

                                                            Toast.makeText(Register.this, "Eklendi",
                                                                    Toast.LENGTH_SHORT).show();
                                                            Intent intent = new Intent(Register.this, Announcements.class);
                                                            intent.putExtra("user", newUser);
                                                            startActivity(intent);
                                                            finish();
                                                        }
                                                    })
                                                    .addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {

                                                            Toast.makeText(Register.this, "Hata: " + e.getMessage(),
                                                                    Toast.LENGTH_SHORT).show();
                                                        }
                                                    });
                                        } else {

                                            Toast.makeText(Register.this, "Hata: " + task.getException().getMessage(),
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            } else {

                                Toast.makeText(Register.this, "Hata: " + task.getException().getMessage(),
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });


                }
                progressBar.setVisibility(View.GONE);
            }

        });

        goToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }
        });
        dateEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(Register.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                dateEnter.setText(day + "/" + month + "/" + year);
                                System.out.print(dateEnter.getText());
                            }
                        }, year, month, dayOfMonth);
                datePickerDialog.show();
            }
        });
        dateFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(Register.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                dateFinish.setText(day + "/" + month + "/" + year);
                                Toast.makeText(Register.this,dateFinish.getText(),Toast.LENGTH_SHORT).show();
                                System.out.print(dateEnter.getText());
                            }
                        }, year, month, dayOfMonth);
                datePickerDialog.show();
            }
        });
        photo.setOnClickListener(new View.OnClickListener() {
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

                CropImage.activity().start(Register.this);
            }

            private void requestStoragePermission() {
                requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},100);


            }

            private void requestCameraPermission() {
                requestPermissions(new String[]{android.Manifest.permission.CAMERA,android.Manifest.permission.WRITE_EXTERNAL_STORAGE},100);
            }

            private boolean checkStoragePermission() {
                boolean res2 = ContextCompat.checkSelfPermission(Register.this,android.Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED;
                return res2;
            }
            private boolean checkCameraPermission() {
                boolean res1 = ContextCompat.checkSelfPermission(Register.this,android.Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED;
                boolean res2 = ContextCompat.checkSelfPermission(Register.this,android.Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED;
                return res1&&res2;
            }
        });





    }

}