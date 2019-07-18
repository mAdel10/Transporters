package com.memaro.transporter.Activities;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.memaro.transporter.Activities.Models.Request;
import com.memaro.transporter.Activities.adapters.GetRequestAdapter;
import com.memaro.transporter.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GetRequestActivity extends AppCompatActivity {

    DatabaseReference myRef;
    List<Request> requests = new ArrayList<>();
    GetRequestAdapter adapter;

    String compid;
    FirebaseAuth auth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_request);

        myRef = FirebaseDatabase.getInstance().getReference();

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        compid = user.getUid();

        myRef.child("Company").child(compid).child("Requests").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                requests.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Request request = snapshot.getValue(Request.class);
                    requests.add(request);
                    adapter.notifyDataSetChanged();
                }

                Collections.reverse(requests);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        RecyclerView recyclerView = findViewById(R.id.request_recyclerView);
        adapter = new GetRequestAdapter(requests, GetRequestActivity.this);
        recyclerView.setLayoutManager(new LinearLayoutManager(GetRequestActivity.this));
        recyclerView.setAdapter(adapter);

    }
}
