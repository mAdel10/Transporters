package com.memaro.transporter.Activities;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.memaro.transporter.Activities.Models.TransporterModel;
import com.memaro.transporter.R;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickCancel;
import com.vansuita.pickimage.listeners.IPickResult;

import java.util.ArrayList;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;


public class AddTransporter extends AppCompatActivity {

    private CircleImageView driverImage;
    private EditText nameEditText;
    private EditText phoneNumberEditText;
    private EditText emailEditText;
    private EditText driverIdEditText;
    private EditText passwordEditText;
    private EditText carTypeEditText;
    private EditText carNumberdEditText;
    private Button addTransporterButton;

    public static double driverLatitude = 0;
    public static double driverLongitude = 0;

    private ProgressDialog progressDialog;

    public static double signUpLatitude, signUpLongitude;

    private String name, phoneNumber, email, driverId, password, carType, carNumber;
    private Uri photoUri;
    private ArrayList<String> imagesUrl;

    FirebaseStorage storage;
    StorageReference storageReference;

    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transporter);
        myRef = FirebaseDatabase.getInstance().getReference();
        imagesUrl = new ArrayList<>();
        driverImage = findViewById(R.id.add_transporter_driver_img_view);
        driverImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAndroidVersion();
            }
        });
        nameEditText = findViewById(R.id.add_transporter_name_editText);
        phoneNumberEditText = findViewById(R.id.add_transporter_phone_number_editText);
        driverIdEditText = findViewById(R.id.add_transporter_driver_id_editText);
        carTypeEditText = findViewById(R.id.add_transporter_car_type_editText);
        carNumberdEditText = findViewById(R.id.add_transporter_car_number_editText);
        passwordEditText = findViewById(R.id.add_transporter_password_editText);
        emailEditText = findViewById(R.id.add_transporter_email_editText);
        addTransporterButton = findViewById(R.id.add_transporter_add_transporter_button);
        addTransporterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addTransporter();
            }
        });


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
                .into(driverImage);
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
                    driverImage.setImageBitmap(bitmap);
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

    private void addTransporter() {
        name = nameEditText.getText().toString().trim();
        phoneNumber = phoneNumberEditText.getText().toString().trim();
        email = emailEditText.getText().toString().trim();
        driverId = driverIdEditText.getText().toString().trim();
        password = passwordEditText.getText().toString().trim();
        carType = carTypeEditText.getText().toString().trim();
        carNumber = carNumberdEditText.getText().toString().trim();

        if (photoUri == null || Uri.EMPTY.equals(photoUri) || name.isEmpty() || carNumber.isEmpty()
                || phoneNumber.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches() || carType.isEmpty()
                || password.isEmpty() || password.length() < 6 || driverId.isEmpty() || driverId.length() < 6) {

            if (photoUri == null || Uri.EMPTY.equals(photoUri)) {
                Toast.makeText(this, "Please Select Image", Toast.LENGTH_SHORT).show();
            }

            if (name.isEmpty()) {
                nameEditText.setError("Name is require");
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                emailEditText.setError("Enter Valid Email");
            }
            if (email.isEmpty()) {
                emailEditText.setError("Email is require");
            }
            if (phoneNumber.isEmpty()) {
                phoneNumberEditText.setError("Phone number is require");
            }
            if (password.length() < 6 || password.length() > 6) {
                passwordEditText.setError("Password must be 6 numbers");
            }
            if (password.isEmpty()) {
                passwordEditText.setError("Password is require");
            }
            if (carType.isEmpty()) {
                driverIdEditText.setError("Car Type is require");
            }

            if (carNumber.isEmpty()) {
                driverIdEditText.setError("Car Number is require");
            }
            if (driverId.isEmpty()) {
                driverIdEditText.setError("Driver ID is require");
            }
            if (driverId.length() < 6 || driverId.length() > 6) {
                driverIdEditText.setError("Driver ID must be 6 numbers");
            }

            if (driverLatitude == 0 || driverLongitude == 0) {
                Toast.makeText(this, "Please select transporter location", Toast.LENGTH_SHORT).show();
                return;
            }

            myRef.child("driverId").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    if (dataSnapshot.getValue() != null) {
                        driverIdEditText.setError("error id exist");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        } else {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Please Wait ....");
            progressDialog.setCancelable(false);
            progressDialog.show();

            final String imageName = UUID.randomUUID().toString() + ".jpg";
            storage = FirebaseStorage.getInstance();
            storageReference = storage.getReference();
            storageReference.child("Drivers").child("Images").child(driverId).child(imageName).putFile(photoUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    storageReference.child("Drivers").child("Images").child(driverId).child(imageName).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            String downUrl = uri.toString();
                            imagesUrl.add(downUrl);
                            photoUri = null;
                            FirebaseAuth auth = FirebaseAuth.getInstance();
                            FirebaseUser user = auth.getCurrentUser();
                            String compid = user.getUid();

                            TransporterModel transporterModel = new
                                    TransporterModel(compid, name, phoneNumber, driverId, email, password,
                                    carType, carNumber, driverLatitude, driverLongitude, downUrl);
                            myRef.child("Company").child(compid)
                                    .child("Drivers").child(driverId).setValue(transporterModel);

                            myRef.child("DriversLogin").child(driverId).setValue(transporterModel);

                            progressDialog.dismiss();
                            Toast.makeText(AddTransporter.this, "TransporterModel added", Toast.LENGTH_LONG).show();

                            finish();
                        }
                    });


                }

            });
        }
    }

    public void openSelectDriverLocation(View view) {
        startActivity(new Intent(AddTransporter.this, SelectDriverLocation.class));
    }
}