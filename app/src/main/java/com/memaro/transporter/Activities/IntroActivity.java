package com.memaro.transporter.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.memaro.transporter.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class IntroActivity extends AppCompatActivity {

    @BindView(R.id.into_sign_up_company_button)
    Button intoSignUpCompanyButton;
    @BindView(R.id.into_sign_up_client_button)
    Button intoSignUpClientButton;
    @BindView(R.id.into_sign_in_driver_button)
    Button intoSignInDriverButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.into_sign_up_company_button, R.id.into_sign_up_client_button, R.id.into_sign_in_driver_button})
    public void onViewClicked(View view) {
        Intent i;
        switch (view.getId()) {
            case R.id.into_sign_up_company_button:
                i = new Intent(IntroActivity.this, CompanyLogInActivity.class);
                startActivity(i);
                // Missing code
                break;
            case R.id.into_sign_up_client_button:
                i = new Intent(IntroActivity.this, ClientLogInActivity.class);
                startActivity(i);
                // Missing code
                break;
            case R.id.into_sign_in_driver_button:
                i = new Intent(IntroActivity.this, DriverLogInActivity.class);
                startActivity(i);
                break;
        }
    }
}
