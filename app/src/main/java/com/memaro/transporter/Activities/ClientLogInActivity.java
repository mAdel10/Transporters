package com.memaro.transporter.Activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.memaro.transporter.Activities.Helpers.InputValidator;
import com.memaro.transporter.Activities.Models.Client;
import com.memaro.transporter.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ClientLogInActivity extends AppCompatActivity {

    @BindView(R.id.logIn_client_email_editText)
    EditText logInClientEmailEditText;
    @BindView(R.id.logIn_client_password_editText)
    EditText logInClientPasswordEditText;
    @BindView(R.id.logIn_client_forget_password_text_view)
    TextView logInClientForgetPasswordTextView;
    @BindView(R.id.logIn_client_sign_in_button)
    Button logInClientSignInButton;
    @BindView(R.id.logIn_client_signUp_TextView)
    TextView logInClientSignUpTextView;



    private String email, password;

    private ProgressDialog progressDialog;
    private AlertDialog.Builder alertDialog;

    //Firebase Authentication
    FirebaseAuth mAuth;

    //Firebase Database
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_log_in);
        ButterKnife.bind(this);
        mAuth = FirebaseAuth.getInstance();
    }

    @OnClick({R.id.logIn_client_forget_password_text_view, R.id.logIn_client_sign_in_button, R.id.logIn_client_signUp_TextView})
    public void onViewClicked(View view) {
        Intent i;
        switch (view.getId()) {
            case R.id.logIn_client_forget_password_text_view:
                break;
            case R.id.logIn_client_sign_in_button:
                if (getInputData())
                    signIn(email, password);
                break;
            case R.id.logIn_client_signUp_TextView:
                i = new Intent(ClientLogInActivity.this, ClientSignUpActivity.class);
                startActivity(i);
                // don't finish it  finish();
                break;
        }
    }

    private boolean getInputData() {
        if (!InputValidator.signInValidation(getApplicationContext(),
                logInClientEmailEditText, logInClientPasswordEditText))
            return false;

        email = logInClientEmailEditText.getText().toString().trim();
        password = logInClientPasswordEditText.getText().toString().trim();
        return true;
    }

    private void signIn(String email, String password) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait ....");
        progressDialog.setCancelable(false);
        progressDialog.show();

        mAuth.signInWithEmailAndPassword(email, password).
                addOnCompleteListener(this,
                        new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Intent i;
                                if (task.isSuccessful()) {
                                    progressDialog.dismiss();
                                    Toast.makeText(ClientLogInActivity.this,
                                            "LogIn Success", Toast.LENGTH_SHORT).show();
                                    i = new Intent(ClientLogInActivity.this, ClientActivity.class);
                                    startActivity(i);
                                    finish();
                                } else {
                                    Toast.makeText(ClientLogInActivity.this, task.getException()
                                            .getMessage(), Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                }
                            }
                        });
    }
}
