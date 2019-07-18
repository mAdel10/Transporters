package com.memaro.transporter.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.memaro.transporter.Activities.Models.TransporterModel;
import com.memaro.transporter.Activities.adapters.driveInfoAdapter;
import com.memaro.transporter.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DriverInfo extends AppCompatActivity {

    DatabaseReference myRef;
    List<TransporterModel> transporterModels = new ArrayList<>();
    driveInfoAdapter adapter;

    String compid;
    FirebaseAuth auth;
    FirebaseUser user;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_onfo);

        myRef = FirebaseDatabase.getInstance().getReference();

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        compid = user.getUid();

        myRef.child("Company").child(compid).child("Drivers").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                transporterModels.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    TransporterModel transporterModel = snapshot.getValue(TransporterModel.class);
                    transporterModels.add(transporterModel);
                    adapter.notifyDataSetChanged();
                }

                Collections.reverse(transporterModels);
              }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        RecyclerView recyclerView = findViewById(R.id.driver_info_recyclerView);
        adapter = new driveInfoAdapter(transporterModels, DriverInfo.this);
        recyclerView.setLayoutManager(new LinearLayoutManager(DriverInfo.this));
        recyclerView.setAdapter(adapter);

    }
}