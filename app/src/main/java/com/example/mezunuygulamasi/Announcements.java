package com.example.mezunuygulamasi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Announcements extends AppCompatActivity {
    private RecyclerView recyclerView;
    private AnnouncementAdapter adapter;
    private List<Announcement> announcementList;
    private ImageButton profileButton;
    private ImageButton announcementAdButton;


    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcements);

        recyclerView = findViewById(R.id.recyclerViewAnnouncement);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        profileButton=findViewById(R.id.profileButton);
        announcementAdButton = findViewById(R.id.addAnnouncement);
        profileButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Profil.class);
                startActivity(intent);
                finish();
            }
        });
        announcementAdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AnnouncementAdd.class);
                startActivity(intent);
                finish();
            }
        });


        RecyclerView recyclerViewAnnouncement = findViewById(R.id.recyclerViewAnnouncement);
        List<Announcement> announcementList = new ArrayList<>();



        AnnouncementAdapter adapter = new AnnouncementAdapter(announcementList, this);
        recyclerViewAnnouncement.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewAnnouncement.setAdapter(adapter);

        db = FirebaseFirestore.getInstance();
        db.collection("Announcement")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            Announcement announcement = documentSnapshot.toObject(Announcement.class);
                            announcementList.add(announcement);
                        }
                        adapter.notifyDataSetChanged();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Announcements.this, "hata", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
