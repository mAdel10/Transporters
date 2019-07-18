package com.memaro.transporter.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.memaro.transporter.R;

public class CompanyHomeActivity extends AppCompatActivity {
    private Button addTransporterButton;
    private Button showAllDriversButton;
    private Button showRequestsBtn;
    private Button logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_home);

        addTransporterButton = findViewById(R.id.company_home_add_driver_button);
        addTransporterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CompanyHomeActivity.this ,AddTransporter.class);
                startActivity(i);

            }
        });

        showAllDriversButton = findViewById(R.id.company_home_show_drivers_button);
        showAllDriversButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CompanyHomeActivity.this ,DriverInfo.class);
                startActivity(i);

            }
        });

        showRequestsBtn = findViewById(R.id.open_request_activity);
        showRequestsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CompanyHomeActivity.this ,GetRequestActivity.class);
                startActivity(i);

            }
        });

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
               startActivity(new  Intent(CompanyHomeActivity.this,IntroActivity.class));
               finish();
               break;
       }
        return true;
    }
}
