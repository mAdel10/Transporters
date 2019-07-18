package com.memaro.transporter.Activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.memaro.transporter.Activities.Models.Request;
import com.memaro.transporter.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ServiceRequestActivity extends AppCompatActivity {

    @BindView(R.id.service_Product_type_editText)
    EditText serviceProductTypeEditText;
    @BindView(R.id.service_Product_weight_editText)
    EditText serviceProductWeightEditText;
    @BindView(R.id.service_Product_payment_editText)
    TextView serviceProductPaymentEditText;
    @BindView(R.id.service_Product_distance_editText)
    EditText serviceProductDistanceEditText;
    @BindView(R.id.service_request_button)
    Button serviceRequestButton;

    public static int distance;

    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_request);
        ButterKnife.bind(this);

        serviceProductDistanceEditText.setText(distance + " K.M");

    }

    @OnClick({R.id.service_Product_payment_editText, R.id.service_request_button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.service_Product_payment_editText:
                PopupMenu popupMenu = new PopupMenu(ServiceRequestActivity.this, serviceProductDistanceEditText);
                popupMenu.getMenuInflater().inflate(R.menu.payment_popup_menu,popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        serviceProductPaymentEditText.setText(item.getTitle());
                        return true;
                    }
                });
                popupMenu.show();
                break;
            case R.id.service_request_button:
                sendRequest();
                break;
        }
    }

    private void sendRequest() {
        final String goodType = serviceProductTypeEditText.getText().toString();
        final String goodWeight = serviceProductWeightEditText.getText().toString();
        final String payment = serviceProductPaymentEditText.getText().toString();

        if (goodType.equals("") || goodWeight.equals("")) {
            Toast.makeText(this, "Please fill all data", Toast.LENGTH_SHORT).show();
            return;
        }

        AlertDialog alertDialog = new AlertDialog.Builder(ServiceRequestActivity.this)
                .setTitle("Order cost")
                .setMessage("Order price = " + calculatePrice(goodWeight))
                .setPositiveButton("Confirm order", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String requestId = reference.push().getKey();

                        Request request = new Request();
                        request.setGoodType(goodType);
                        request.setGoodWeight(goodWeight + " " + "Ton");
                        request.setDistance(distance + " " + " K.M");
                        request.setPrice(calculatePrice(goodWeight));
                        request.setPayment(payment);
                        request.setUserLatitude(ClientMapActivity.originLocation.getLatitude());
                        request.setUserLongitude(ClientMapActivity.originLocation.getLongitude());
                        request.setOrderLatitude(ClientMapActivity.destinationPosition.latitude());
                        request.setOrderLongitude(ClientMapActivity.destinationPosition.latitude());
                        request.setRequestId(requestId);

                        reference.child("Company").child(ClientMapActivity.staticCompanyModel.getCompanyId())
                                .child("Requests").child(requestId).setValue(request);

                        finish();

                    }
                })
                .setNegativeButton("Cancel", null)
                .create();
        alertDialog.show();


    }

    private int calculatePrice(String weight) {
        return (5 * Integer.parseInt(weight)) + (10 * (distance / 1000));
    }
}
