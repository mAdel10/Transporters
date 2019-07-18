package com.memaro.transporter.Activities;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.memaro.transporter.Activities.Helpers.CompanyInputValidator;
import com.memaro.transporter.Activities.Models.Company;
import com.memaro.transporter.R;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickCancel;
import com.vansuita.pickimage.listeners.IPickResult;

import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;


public class CompanySignUpActivity extends AppCompatActivity {

    @BindView(R.id.sign_up_company_img_view)
    CircleImageView signUpCompanyImgView;
    @BindView(R.id.signUp_company_name_editText)
    EditText signUpCompanyNameEditText;
    @BindView(R.id.signUp_company_email_editText)
    EditText signUpCompanyEmailEditText;
    @BindView(R.id.signUp_company_password_editText)
    EditText signUpCompanyPasswordEditText;
    @BindView(R.id.signUp_company_confirm_password_editText)
    EditText signUpCompanyConfirmPasswordEditText;
    @BindView(R.id.signUp_company_market_number_editText)
    EditText signUpCompanyMarketNumberEditText;
    @BindView(R.id.signUp_company_address_editText)
    EditText signUpCompanyAddressEditText;
    @BindView(R.id.signUp_company_button)
    Button signUpCompanyButton;
    @BindView(R.id.signUp_company_select_location_editText)
    TextView signUpCompanySelectLocationEditText;

    private String companyName, companyEmail, password, address, markerNo;
    private Uri photoUri;
    private ProgressDialog progressDialog;
    private AlertDialog.Builder alertDialog;
    public static double signUpLatitude = 0, signUpLongitude = 0;
    public static Company staticCompanyModel;

    //FireBase Authentication
    FirebaseAuth mAuth;

    //FireBase Database
    FirebaseDatabase database;
    DatabaseReference databaseReference;

    //FireBase Storage
    FirebaseStorage storage;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_sign_up);
        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();

    }

    @OnClick({R.id.sign_up_company_img_view, R.id.signUp_company_button, R.id.signUp_company_select_location_editText})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.sign_up_company_img_view:
                checkAndroidVersion();
                break;
            case R.id.signUp_company_select_location_editText:
                Intent i = new Intent(CompanySignUpActivity.this, CompanySelectLocation.class);
                startActivity(i);
                break;
            case R.id.signUp_company_button:
                if (getInputData())
                    register(companyEmail, password);
                break;
        }
    }

    public void checkAndroidVersion() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            try {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 555);
            } catch (Exception e) {

            }
        } else {
            pickUpImage();
        }
    }

    private void pickUpImage() {
        CropImage.startPickImageActivity(this);
        Picasso.get()
                .load(photoUri)
                .into(signUpCompanyImgView);
    }

    private void cropRequest(Uri photoUri) {
        CropImage.activity(photoUri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setMultiTouchEnabled(true)
                .start(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            photoUri = CropImage.getPickImageResultUri(this, data);
            cropRequest(photoUri);
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), result.getUri());
                    signUpCompanyImgView.setImageBitmap(bitmap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 555 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            pickUpImage();
        } else {
            checkAndroidVersion();
        }
    }

    public boolean getInputData() {
        if (photoUri == null || Uri.EMPTY.equals(photoUri)) {
            Toast.makeText(this, "Please Select Image", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!CompanyInputValidator.signUpValidation(getApplicationContext(), signUpCompanyNameEditText, signUpCompanyEmailEditText, signUpCompanyPasswordEditText, signUpCompanyConfirmPasswordEditText, signUpCompanyMarketNumberEditText, signUpCompanyAddressEditText))
            return false;

        companyName = signUpCompanyNameEditText.getText().toString();
        companyEmail = signUpCompanyEmailEditText.getText().toString().trim();
        password = signUpCompanyPasswordEditText.getText().toString().trim();
        address = signUpCompanyAddressEditText.getText().toString().trim();
        markerNo = signUpCompanyMarketNumberEditText.getText().toString().trim();
        return true;
    }

    private void register(String email, String password) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait ....");
        progressDialog.show();
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    progressDialog.dismiss();
                    FirebaseUser user = task.getResult().getUser();
                    saveUser(user, photoUri);
                    signUpVerification(user);
                } else {
                    Toast.makeText(CompanySignUpActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();

                }
            }
        });

    }

    private void saveUser(final FirebaseUser user, final Uri uri) {

        final String imageName = UUID.randomUUID().toString() + ".jpg";
        final String userId = user.getUid();

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        storageReference.child("Company").child("Images").child(userId).child(imageName).putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                storageReference.child("Company").child("Images").child(userId + "/" + imageName).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        database = FirebaseDatabase.getInstance();
                        databaseReference = database.getReference();
                        String downUrl = uri.toString();
                        Company newCompany = new Company(userId, companyName, companyEmail, address, markerNo, downUrl, signUpLatitude, signUpLongitude);
                        databaseReference = FirebaseDatabase.getInstance().getReference().child("Company");
                        databaseReference.child(userId).setValue(newCompany);
                    }
                });
            }
        });

    }

    private void signUpVerification(final FirebaseUser user) {
        user.sendEmailVerification()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            progressDialog.dismiss();
                            Toast.makeText(CompanySignUpActivity.this, "Success", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), CompanyLogInActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Log.e("sendEmailVerification", "sendEmailVerification failed!", task.getException());
                        }
                    }
                });
    }
}
