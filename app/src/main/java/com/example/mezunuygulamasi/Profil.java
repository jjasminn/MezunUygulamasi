package com.example.mezunuygulamasi;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class Profil extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseUser firebaseUser;

    User user;
    ImageView userPhoto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        firebaseUser= mAuth.getCurrentUser();
        setContentView(R.layout.activity_profil);
        TextView nameSurname = (TextView) findViewById(R.id.name);
        TextView linkedn = (TextView) findViewById(R.id.linkedn);
        TextView mail = (TextView) findViewById(R.id.mail);
        TextView phone = (TextView) findViewById(R.id.phone);
        TextView licenceCollage = (TextView) findViewById(R.id.licence);
        TextView licenceDepartment = (TextView) findViewById(R.id.licenceDepartment);
        TextView enterLicence = (TextView) findViewById(R.id.licenceEnter);
        TextView finishLicence = (TextView) findViewById(R.id.licenceFinish);
        TextView degreeCollage = (TextView) findViewById(R.id.degreeCollage);
        TextView degreeDepartment = (TextView) findViewById(R.id.degreeDepartment);
        TextView degreeenter = (TextView) findViewById(R.id.degreeEnter);
        TextView degreefinish = (TextView) findViewById(R.id.degreeFinish);
        TextView doctorateCollage = (TextView) findViewById(R.id.doctorateCollage);
        TextView doctorateDepartment = (TextView) findViewById(R.id.doctorateDepartment);
        TextView doctorateenter = (TextView) findViewById(R.id.doctorateEnter);
        TextView doctoratefinish = (TextView) findViewById(R.id.doctorateFinish);
        TextView company = (TextView) findViewById(R.id.company);
        TextView companyCountry = (TextView) findViewById(R.id.companyCountry);
        TextView companyCity = (TextView) findViewById(R.id.companyCity);
        userPhoto= (ImageView) findViewById(R.id.image);
        ImageButton logoutButton= (ImageButton) findViewById(R.id.logoutButton);
        ImageButton settingsButton  = (ImageButton) findViewById(R.id.settingsButton);
        ImageButton backButton = (ImageButton) findViewById(R.id.backIcon);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(getApplicationContext(), ProfilSettings.class);
                intent.putExtra("user", user);
                startActivity(intent);
                finish();
            }});

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }
        });
        backButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Announcements.class);
                startActivity(i);
                finish();


            }
        });
         if(firebaseUser==null||firebaseUser.getEmail()==null){
            Intent i = new Intent(getApplicationContext(), Login.class);
            startActivity(i);
            finish();
        }
        else {
            mail.setText(firebaseUser.getEmail());
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("users")
                    .document(firebaseUser.getEmail())
                    .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                        @Override
                        public void onEvent(@Nullable DocumentSnapshot document, @Nullable FirebaseFirestoreException error) {
                            if (document != null && document.exists()) {
                                String uid = document.getString("uid");
                                String nameF = document.getString("name");
                                String surnameF = document.getString("surname");
                                String mailF = document.getString("email");
                                String phoneF = document.getString("phone");
                                String linkednF = document.getString("linkedn")!= null ? document.getString("linkedn") : "";
                                String licenceCollageF = document.getString("licenceCollage")!= null ? document.getString("licenceCollage") : "";
                                String licenceDepartmentF = document.getString("licenceDepartment")!= null ? document.getString("licenceDepartment") : "";
                                String enterLicenceF = document.getString("dateLicenceEnter");
                                String finishLicenceF = document.getString("dateLicenceFinish");
                                String degreeCollageF = document.getString("degreeCollage")!= null ? document.getString("degreeCollage") : "";
                                String degreeDepartmentF = document.getString("degreeDepartment")!= null ? document.getString("degreeDepartment") : "";
                                String enterDegreeF = document.getString("degreeEnter")!= null ? document.getString("degreeEnter") : "";
                                String finishDegreeF = document.getString("degreeFinish")!= null ? document.getString("degreeFinish") : "";
                                String doctorateCollageF = document.getString("doctorateCollage")!= null ? document.getString("doctorateCollage") : "";
                                String doctorateDepartmentF = document.getString("doctorateDepartment")!= null ? document.getString("doctorateDepartment") : "";
                                String enterDoctorateF = document.getString("doctorateEnter")!= null ? document.getString("doctorateEnter") : "";
                                String finishDoctorateF = document.getString("doctorateFinish")!= null ? document.getString("doctorateFinish") : "";
                                String companyF = document.getString("company")!= null ? document.getString("company") : "";
                                String companyCountryF = document.getString("companyCountry")!= null ? document.getString("companyCountry") : "";
                                String companyCityF = document.getString("companyCity")!= null ? document.getString("companyCity") : "";
                                String imageF = document.getString("photoUrl");
                                 user = new User(nameF,surnameF,mailF,phoneF,linkednF,licenceCollageF,licenceDepartmentF,enterLicenceF,finishLicenceF,degreeCollageF,degreeDepartmentF,enterDegreeF,finishDegreeF,doctorateCollageF,doctorateDepartmentF,enterDoctorateF,finishDoctorateF,companyF,companyCountryF,companyCityF,imageF);
                                 user.setUid(uid);

                                nameSurname.setText(nameF + " " + surnameF);
                                // mail.setText(mail);
                                phone.setText(phoneF);
                                linkedn.setText(linkednF);
                                licenceCollage.setText(licenceCollageF);
                                licenceDepartment.setText(licenceDepartmentF);
                                enterLicence.setText(enterLicenceF);
                                finishLicence.setText(finishLicenceF);
                                degreeCollage.setText(degreeCollageF);
                                degreeDepartment.setText(degreeDepartmentF);
                                degreeenter.setText(enterDegreeF);
                                degreefinish.setText(finishDegreeF);
                                doctorateCollage.setText(doctorateCollageF);
                                doctorateDepartment.setText(doctorateDepartmentF);
                                doctorateenter.setText(enterDoctorateF);
                                doctoratefinish.setText(finishDoctorateF);
                                company.setText(companyF);
                                companyCountry.setText(companyCountryF);
                                companyCity.setText(companyCityF);
                                FirebaseStorage storage = FirebaseStorage.getInstance();
                                StorageReference storageRef = storage.getReference();
                                StorageReference photoRef = storageRef.child("users/" + firebaseUser.getUid() + "/profilePhoto.jpg");


                                photoRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri downloadUrl) {

                                        Picasso.get().load(downloadUrl).into(userPhoto);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {

                                        Toast.makeText(Profil.this,"Fotograf Yuklenemedi",Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } else {

                            }
                        }
                    });



        }


    }
    }
