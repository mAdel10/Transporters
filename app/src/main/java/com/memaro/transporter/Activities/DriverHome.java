package com.memaro.transporter.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.memaro.transporter.Activities.Models.Request;
import com.memaro.transporter.Activities.Models.TransporterModel;
import com.memaro.transporter.R;

public class DriverHome extends AppCompatActivity {
    final private TransporterModel data = DriverLogInActivity.driverData;

    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_home);
    }

    public void checkRequest(View view) {
        reference.child("Company").child(data.getId()).child("Drivers").child(data.getDriverId()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild("SingleRequest")) {
                    Request request = dataSnapshot.child("SingleRequest").getValue(Request.class);
                    openDialog(request);
                } else {
                    Toast.makeText(DriverHome.this, "No request", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void openDialog(Request request) {
        DriverTrip.request = request;
        AlertDialog alertDialog = new AlertDialog.Builder(DriverHome.this)
                .setTitle("Request order")
                .setMessage("Type : " + request.getGoodType()
                        + "\nWeight : " + request.getGoodWeight()
                        + "\nDistance : " + request.getDistance()
                        + "\nPrice : " + request.getPrice()
                )
                .setPositiveButton("Accept Request", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(DriverHome.this, DriverTrip.class));
                        //finish();
                    }
                })
                .setNegativeButton("Cancel", null)
                .create();
        alertDialog.show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.logout_popup_menu , menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout_item:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(DriverHome.this,IntroActivity.class));
                finish();
                break;
        }
        return true;
    }


}
