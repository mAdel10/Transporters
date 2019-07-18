package com.memaro.transporter.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.memaro.transporter.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SendProblemActivity extends AppCompatActivity {

    @BindView(R.id.problem_phone_editText)
    EditText problemPhoneEditText;
    @BindView(R.id.problem_description_editText)
    EditText problemDescriptionEditText;
    @BindView(R.id.problem_send_button)
    Button problemSendButton;

    String phone;
    String discription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_problem);
        ButterKnife.bind(this);
    }


    @OnClick(R.id.problem_send_button)
    public void onViewClicked() {
        phone = problemPhoneEditText.getText().toString().trim();
        discription = problemDescriptionEditText.getText().toString().trim();
        if(phone.isEmpty()){
            problemPhoneEditText.setError("Required");
            problemPhoneEditText.requestFocus();
        }
        else if(discription.isEmpty()){
            problemDescriptionEditText.setError("Required");
            problemDescriptionEditText.requestFocus();
        }else
        Toast.makeText(this, "Thanks", Toast.LENGTH_SHORT).show();
    }
}

