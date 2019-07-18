package com.memaro.transporter.Activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.memaro.transporter.Activities.Helpers.InputValidator;
import com.memaro.transporter.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CompanyLogInActivity extends Activity {

    @BindView(R.id.logIn_company_email_editText)
    EditText logInCompanyEmailEditText;
    @BindView(R.id.logIn_company_password_editText)
    EditText logInCompanyPasswordEditText;
    @BindView(R.id.logIn_company_forget_password_text_view)
    TextView logInCompanyForgetPasswordTextView;
    @BindView(R.id.logIn_company_sign_in_button)
    Button logInCompanySignInButton;
    @BindView(R.id.logIn_company_signUp_TextView)
    TextView logInCompanySignUpTextView;

    private String email, password;

    private ProgressDialog progressDialog;
    private AlertDialog.Builder alertDialog;

    //Firebase Authentication
    FirebaseAuth mAuth;

    //Firebase Database
    FirebaseDatabase database;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_login);
        ButterKnife.bind(this);
        mAuth = FirebaseAuth.getInstance();

    }

    @OnClick({R.id.logIn_company_sign_in_button, R.id.logIn_company_signUp_TextView})
    public void onViewClicked(View view) {
        Intent i;
        switch (view.getId()) {
            case R.id.logIn_company_sign_in_button:
                if (getInputData())
                    signIn(email, password);
                break;
            case R.id.logIn_company_signUp_TextView:
                i = new Intent(CompanyLogInActivity.this, CompanySignUpActivity.class);
                startActivity(i);
                break;
        }
    }

    private boolean getInputData() {
        if (!InputValidator.signInValidation(getApplicationContext(),
                logInCompanyEmailEditText, logInCompanyPasswordEditText))
            return false;

        email = logInCompanyEmailEditText.getText().toString().trim();
        password = logInCompanyPasswordEditText.getText().toString().trim();
        return true;
    }

    private void signIn(final String email, String password) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait ....");
        progressDialog.setCancelable(false);
        progressDialog.show();

        mAuth.signInWithEmailAndPassword(email, password).
                addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            progressDialog.dismiss();
//                    Toast.makeText(CompanyLogInActivity.this,
//                     "LogIn Success", Toast.LENGTH_SHORT).show();
                            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                            String RegisteredUserID = currentUser.getUid();

                            databaseReference = FirebaseDatabase.getInstance().getReference()
                                    .child("Company").child(RegisteredUserID);

                            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {

                                    if (dataSnapshot.getValue() == null) {
//                            String userType = dataSnapshot.child("companyEmail").getValue().toString();

                                        Toast.makeText(CompanyLogInActivity.this, "Not Found in Categry"
                                                , Toast.LENGTH_SHORT).show();
                                        progressDialog.dismiss();
                                    } else {
                                        progressDialog.dismiss();
                                        Toast.makeText(CompanyLogInActivity.this, "Login Success"
                                                , Toast.LENGTH_SHORT).show();
                                        Intent i = new Intent(CompanyLogInActivity.this, CompanyHomeActivity.class);
                                        startActivity(i);
                                        finish();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }

                            });

                        } else {
                            Toast.makeText(CompanyLogInActivity.this, "Login Error"
                                    , Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                });

    }

}
