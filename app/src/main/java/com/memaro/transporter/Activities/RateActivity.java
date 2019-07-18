package com.memaro.transporter.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import com.memaro.transporter.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RateActivity extends AppCompatActivity {

    @BindView(R.id.ratingBar)
    RatingBar ratingBar;
    @BindView(R.id.driver_create_button)
    Button driverCreateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);
        ButterKnife.bind(this);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                Toast.makeText(RateActivity.this, "Stars " + rating, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick(R.id.driver_create_button)
    public void onViewClicked() {
        Toast.makeText(this, "Thanks For this Rating " + ratingBar.getRating(), Toast.LENGTH_SHORT).show();
    }
}
