package com.memaro.transporter.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.memaro.transporter.Activities.Models.TransporterModel;
import com.memaro.transporter.R;

public class DriverLogInActivity extends AppCompatActivity {
    private EditText idEditText;
    private EditText passwordEditText;
    private Button loginButton;

    private FirebaseAuth auth;
    private DatabaseReference databaseReference;

    public  static TransporterModel driverData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_log_in);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        idEditText = findViewById(R.id.driver_logIn_id_editText);
        passwordEditText = findViewById(R.id.driver_logIn_password_editText);
        loginButton = findViewById(R.id.driver_logIn_sign_in_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkLogin();

            }
        });
    }

    private void checkLogin() {
        final String id = idEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

//        if (!TextUtils.isEmpty(id) && !TextUtils.isEmpty(password)) {
//
//        }
        if (id.isEmpty() || id.length() < 6 || id.length() > 6 || password.isEmpty()) {

            if (id.isEmpty())
                idEditText.setError("ID required", null);

            if (id.length() < 6 || id.length() > 6)
                idEditText.setError("ID must be 6 numbers", null);

            if (password.isEmpty())
                passwordEditText.setError("Password required", null);
        }

        databaseReference.child("DriversLogin").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //Toast.makeText(DriverLogInActivity.this , id + " " , Toast.LENGTH_LONG).show();

                if (!dataSnapshot.hasChild(id)) {
                    idEditText.setError("error id exist");
                    //Toast.makeText(DriverLogInActivity.this, "id false", Toast.LENGTH_LONG).show();

                } else {
                    //Toast.makeText(DriverLogInActivity.this, "id true", Toast.LENGTH_LONG).show();
                    driverData = dataSnapshot.child(id).getValue(TransporterModel.class);
                    if (driverData.getPassword().equals(password)) {
                        //Toast.makeText(DriverLogInActivity.this, "Password True", Toast.LENGTH_SHORT).show();
                        // Intent here
                        Intent i = new Intent(DriverLogInActivity.this, DriverHome.class);
                        startActivity(i);
                        finish();
                    }
                    else
                        Toast.makeText(DriverLogInActivity.this, "Password wrong", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

//        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
//        String userID = currentUser.getUid();
//
//        databaseReference = FirebaseDatabase.getInstance().getReference()
//                .child("Company").child(userID);
//
//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//                if (dataSnapshot.getValue() == null) {
//                    idEditText.setError("ID ");
//                } else {
//
//                    databaseReference.signInWithEmailAndPassword(id, password).
//                            addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                                @Override
//                                public void onComplete(@NonNull Task<AuthResult> task) {
//
//                                    if (task.isSuccessful()) {
//                                        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
//                                        String RegisteredUserID = currentUser.getUid();
//
//                                        databaseReference = FirebaseDatabase.getInstance().getReference()
//                                                .child("Driver").child(RegisteredUserID);
//
//                                        databaseReference.addValueEventListener(new ValueEventListener() {
//                                            @Override
//                                            public void onDataChange(DataSnapshot dataSnapshot) {
//
//                                                if (dataSnapshot.getValue() == null) {
////                            String userType = dataSnapshot.child("companyEmail").getValue().toString();
//
//                                                    Toast.makeText(DriverLogInActivity.this, "ID Not Found"
//                                                            , Toast.LENGTH_SHORT).show();
//                                                } else {
//
//                                                    Toast.makeText(DriverLogInActivity.this, "Login Success"
//                                                            , Toast.LENGTH_SHORT).show();
//                                                    Intent i = new Intent(DriverLogInActivity.this, CompanyHomeActivity.class);
//                                                    startActivity(i);
//                                                    finish();
//                                                }
//                                            }
//
//                                            @Override
//                                            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                            }
//
//                                        });
//
//                                    } else {
//                                        Toast.makeText(DriverLogInActivity.this, "Login Error"
//                                                , Toast.LENGTH_SHORT).show();
//                                    }
//                                }
//                            });
//
//                }
//            }

    }

    
}