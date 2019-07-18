package com.memaro.transporter.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.memaro.transporter.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ClientActivity extends AppCompatActivity {
    @BindView(R.id.client_contact_us_button)
    Button clientContactUsButton;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);
        ButterKnife.bind(this);

    }

    public void openClientMapActivity(View view) {
        startActivity(new Intent(ClientActivity.this, ClientMapActivity.class));
    }

    @OnClick(R.id.client_contact_us_button)
    public void onViewClicked() {
        Intent i = new Intent(ClientActivity.this, SendProblemActivity.class);
        startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.logout_popup_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout_item:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(ClientActivity.this, IntroActivity.class));
                finish();
                break;
        }
        return true;
    }
}
