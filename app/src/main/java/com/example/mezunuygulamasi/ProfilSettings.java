package com.example.mezunuygulamasi;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.InputStream;

public class ProfilSettings extends AppCompatActivity {
    private FirebaseAuth mAuth;
    Uri resultUri;
    ImageView image;
    private FirebaseFirestore db;
    private FirebaseUser user;
    User currentUser;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                resultUri = result.getUri();
                try{

                    InputStream stream= getContentResolver().openInputStream(resultUri);
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
        Intent intent = getIntent();
         currentUser = (User) intent.getSerializableExtra("user");
        mAuth = FirebaseAuth.getInstance();
        user= mAuth.getCurrentUser();
        db=FirebaseFirestore.getInstance();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();



        setContentView(R.layout.activity_profil_settings);
        Button save = findViewById(R.id.save);
         image = (ImageView) findViewById(R.id.image);
        ImageButton imageChange =(ImageButton) findViewById(R.id.imageChange);
        TextView nameSurname = (TextView) findViewById(R.id.name);
        EditText linkedn = (EditText) findViewById(R.id.linkedn);
        TextView mail = (TextView) findViewById(R.id.mail);
        EditText phone = (EditText) findViewById(R.id.phone);
        EditText licenceCollage = (EditText) findViewById(R.id.licence);
        EditText licenceDepartment = (EditText) findViewById(R.id.licenceDepartment);
        EditText enterLicence = (EditText) findViewById(R.id.licenceEnter);
        EditText finishLicence = (EditText) findViewById(R.id.licenceFinish);
        EditText company = (EditText) findViewById(R.id.company);
        EditText companyCountry = (EditText) findViewById(R.id.companyCountry);
        EditText companyCity = (EditText) findViewById(R.id.companyCity);
        EditText degreeCollage = (EditText) findViewById(R.id.degreeCollage);
        EditText degreeDepartment = (EditText) findViewById(R.id.degreeDepartment);
        EditText degreeenter = (EditText) findViewById(R.id.degreeEnter);
        EditText degreefinish = (EditText) findViewById(R.id.degreeFinish);
        EditText doctorateCollage = (EditText) findViewById(R.id.doctorateCollage);
        EditText doctorateDepartment = (EditText) findViewById(R.id.doctorateDepartment);
        EditText doctorateenter = (EditText) findViewById(R.id.doctorateEnter);
        EditText doctoratefinish = (EditText) findViewById(R.id.doctorateFinish);
        ImageButton backButton =(ImageButton) findViewById(R.id.backIcon);
        backButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Profil.class);
                startActivity(i);
                finish();

            }
        });
        if(user==null||currentUser==null){
            mAuth.signOut();
            Intent i = new Intent(getApplicationContext(), Login.class);
            startActivity(i);
            finish();
        }
        else{
            mail.setText(user.getEmail());
            nameSurname.setText(currentUser.getName()+currentUser.getSurname());
            phone.setText(currentUser.getPhone());
            linkedn.setText(currentUser.getLinkedn());
            licenceCollage.setText(currentUser.getLicenceCollage());
            licenceDepartment.setText(currentUser.getLicenceDepartment());
            enterLicence.setText(currentUser.getDateLicenceEnter());
            finishLicence.setText(currentUser.getDateLicenceFinish());
            degreeCollage.setText(currentUser.getDegreeCollage());
            degreeDepartment.setText(currentUser.getDegreeDepartment());
            degreeenter.setText(currentUser.getDegreeEnter());
            degreefinish.setText(currentUser.getDegreeFinish());
            doctorateCollage.setText(currentUser.getDoctorateCollage());
            doctorateDepartment.setText(currentUser.getDoctorateCollage());
            doctorateenter.setText(currentUser.getDoctorateEnter());
            doctoratefinish.setText(currentUser.getDoctorateFinish());
            company.setText(currentUser.getCompany());
            companyCountry.setText(currentUser.getCompanyCountry());
            companyCity.setText(currentUser.getCompanyCity());
            linkedn.setText(currentUser.getLinkedn());


            StorageReference photoRef = storageRef.child("users/" + user.getUid() + "/profilePhoto.jpg");


            photoRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri downloadUrl) {

                    Picasso.get().load(downloadUrl).into(image);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    Toast.makeText(ProfilSettings.this,"Fotograf Yuklenemedi",Toast.LENGTH_SHORT).show();
                }
            });


        }
        imageChange.setOnClickListener(new View.OnClickListener() {
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

                CropImage.activity().start(ProfilSettings.this);
            }

            private void requestStoragePermission() {
                requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},100);


            }

            private void requestCameraPermission() {
                requestPermissions(new String[]{android.Manifest.permission.CAMERA,android.Manifest.permission.WRITE_EXTERNAL_STORAGE},100);
            }

            private boolean checkStoragePermission() {
                boolean res2 = ContextCompat.checkSelfPermission(ProfilSettings.this,android.Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED;
                return res2;
            }
            private boolean checkCameraPermission() {
                boolean res1 = ContextCompat.checkSelfPermission(ProfilSettings.this,android.Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED;
                boolean res2 = ContextCompat.checkSelfPermission(ProfilSettings.this,android.Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED;
                return res1&&res2;
            }
        });
        save.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                //kaydetme
                if(resultUri==null){
                    currentUser.setCompany(company.getText().toString());
                    currentUser.setCompanyCity(companyCity.getText().toString());
                    currentUser.setCompanyCountry(companyCountry.getText().toString());
                    currentUser.setDegreeCollage(degreeCollage.getText().toString());
                    currentUser.setDegreeDepartment(degreeDepartment.getText().toString());
                    currentUser.setDegreeEnter(degreeenter.getText().toString());
                    currentUser.setDegreeFinish(degreefinish.getText().toString());
                    currentUser.setDoctorateCollage(doctorateCollage.getText().toString());
                    currentUser.setDoctorateDepartment(doctorateDepartment.getText().toString());
                    currentUser.setDoctorateEnter(doctorateenter.getText().toString());
                    currentUser.setDoctorateFinish(doctoratefinish.getText().toString());
                    currentUser.setLicenceCollage(licenceCollage.getText().toString());
                    currentUser.setLicenceDepartment(licenceDepartment.getText().toString());
                    currentUser.setDateLicenceEnter(enterLicence.getText().toString());
                    currentUser.setDateLicenceFinish(finishLicence.getText().toString());
                    currentUser.setLinkedn(linkedn.getText().toString());
                    currentUser.setPhone(phone.getText().toString());


                    db.collection("users").document(user.getEmail()).set(currentUser)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                    Intent intent = new Intent(getApplicationContext(), Profil.class);
                                    intent.putExtra("user", currentUser);
                                    startActivity(intent);
                                    finish();




                                    Toast.makeText(ProfilSettings.this, "Profil güncellendi", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // Kaydetme hatası
                                    Toast.makeText(ProfilSettings.this, "Profil güncellenirken bir hata oluştu", Toast.LENGTH_SHORT).show();
                                }
                            });


                }
                else{
                    StorageReference photoRef = storageRef.child("users/" + currentUser.getUid() + "/profilePhoto.jpg");
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

                                currentUser.setCompany(company.getText().toString());
                                currentUser.setCompanyCity(companyCity.getText().toString());
                                currentUser.setCompanyCountry(companyCountry.getText().toString());
                                currentUser.setDegreeCollage(degreeCollage.getText().toString());
                                currentUser.setDegreeDepartment(degreeDepartment.getText().toString());
                                currentUser.setDegreeEnter(degreeenter.getText().toString());
                                currentUser.setDegreeFinish(degreefinish.getText().toString());
                                currentUser.setDoctorateCollage(doctorateCollage.getText().toString());
                                currentUser.setDoctorateDepartment(doctorateDepartment.getText().toString());
                                currentUser.setDoctorateEnter(doctorateenter.getText().toString());
                                currentUser.setDoctorateFinish(doctoratefinish.getText().toString());
                                currentUser.setLicenceCollage(licenceCollage.getText().toString());
                                currentUser.setLicenceDepartment(licenceDepartment.getText().toString());
                                currentUser.setDateLicenceEnter(enterLicence.getText().toString());
                                currentUser.setDateLicenceFinish(finishLicence.getText().toString());
                                currentUser.setLinkedn(linkedn.getText().toString());
                                currentUser.setPhone(phone.getText().toString());
                                currentUser.setPhotoUrl(task.getResult().toString());
                                db.collection("users").document(currentUser.getEmail()).set(currentUser)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {

                                                Intent intent = new Intent(getApplicationContext(), Profil.class);
                                                intent.putExtra("user", currentUser);
                                                startActivity(intent);
                                                finish();




                                                Toast.makeText(ProfilSettings.this, "Profil güncellendi", Toast.LENGTH_SHORT).show();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {

                                                Toast.makeText(ProfilSettings.this, "Profil güncellenirken bir hata oluştu", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            } else {

                            }
                        }
                    });

                }

            }
        });

    }
}