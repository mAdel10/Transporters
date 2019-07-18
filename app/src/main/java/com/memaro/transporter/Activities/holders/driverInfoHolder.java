package com.memaro.transporter.Activities.holders;

import android.view.View;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.memaro.transporter.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class driverInfoHolder  extends RecyclerView.ViewHolder {

    public CircleImageView imageView;
    public TextView nameTextView;
    public TextView phoneNumberTextView;
    public TextView emailTextView;
    public TextView driverIdTextView;
    public TextView passwordTextView;
    public TextView carType;
    public TextView carNumber;

    public driverInfoHolder(View view) {
        super(view);
        {
            imageView = view.findViewById(R.id.driver_info_img_view);
            nameTextView = view.findViewById(R.id.driver_info_name_textView);
            phoneNumberTextView = view.findViewById(R.id.driver_info_phone_number_textView);
            emailTextView = view.findViewById(R.id.driver_info_email_textView);
            driverIdTextView = view.findViewById(R.id.driver_info_driver_id_textView);
            passwordTextView = view.findViewById(R.id.driver_info_password_textView);
            carType = view.findViewById(R.id.driver_info_car_type_textView);
            carNumber = view.findViewById(R.id.driver_info_car_number_textView);
        }
    }
}