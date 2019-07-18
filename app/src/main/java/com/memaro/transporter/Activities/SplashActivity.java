package com.memaro.transporter.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.memaro.transporter.R;


public class SplashActivity extends Activity {
    private TextView splashTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        int secondsDelayed = 1;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                splashTextView = findViewById(R.id.splash_logo_textVie);
                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade);
                splashTextView.startAnimation(animation);
                Intent i = new Intent(SplashActivity.this, IntroActivity.class);
                startActivity(i);
                finish();
            }
        }, secondsDelayed * 2000 );

    }
}
