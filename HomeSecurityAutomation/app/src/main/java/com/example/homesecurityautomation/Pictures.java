package com.example.homesecurityautomation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class Pictures extends AppCompatActivity implements View.OnClickListener{
    private ProgressBar progress;
    FirebaseStorage storage;
    StorageReference storageRef;
    DatabaseReference databaseReference;
    private GridView gridview;
    private Button back;
    List<Photo> photos;
    CustomAdapter adapter;
    private ProgressBar mProgressCircle;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pictures);
        databaseReference = FirebaseDatabase.getInstance().getReference("Stored_Images");

        /*
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference("Stored_Images");
        */
        photos = new ArrayList<>();
        gridview = findViewById(R.id.gridview);
        back = findViewById(R.id.back);
        mProgressCircle = findViewById(R.id.progressCircle);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Photo pic = postSnapshot.getValue(Photo.class);
                    photos.add(pic);
                }

                //adapter = new CustomAdapter(Pictures.this, photos);

                //mRecyclerView.setAdapter(mAdapter);

                //mAdapter.setOnItemClickListener(Pictures.this);

                mProgressCircle.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(Pictures.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                mProgressCircle.setVisibility(View.INVISIBLE);
            }
        });


        //adapter = new CustomAdapter(this, R.layout.photo_pic, photos);
        //gridview.setAdapter(adapter);

        back.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        if(view == back)
        {
            finish();
            startActivity(new Intent(this, AccessCamera.class));
        }
    }
}
