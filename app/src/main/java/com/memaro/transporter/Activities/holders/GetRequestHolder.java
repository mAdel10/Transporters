package com.memaro.transporter.Activities.holders;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.memaro.transporter.R;

public class GetRequestHolder extends RecyclerView.ViewHolder {

    public CardView layout;
    public TextView goodType;
    public TextView goodWeight;
    public TextView payment;
    public TextView distance;
    public TextView price;

    public GetRequestHolder(@NonNull View view) {
        super(view);
        layout = view.findViewById(R.id.item_request_cardView);
        goodType = view.findViewById(R.id.item_get_good_type_request);
        goodWeight = view.findViewById(R.id.item_get_good_weight_request);
        payment = view.findViewById(R.id.item_get_good_payment_request);
        distance = view.findViewById(R.id.item_get_good_distance_request);
        price = view.findViewById(R.id.item_get_good_price_request);
    }
}
