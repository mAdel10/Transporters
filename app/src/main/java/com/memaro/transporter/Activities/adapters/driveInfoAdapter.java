package com.memaro.transporter.Activities.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.memaro.transporter.Activities.Models.TransporterModel;
import com.memaro.transporter.Activities.holders.driverInfoHolder;
import com.memaro.transporter.R;
import com.squareup.picasso.Picasso;

import java.util.List;


public class driveInfoAdapter extends RecyclerView.Adapter<driverInfoHolder> {

    private List<TransporterModel> transporterModels;
    private Context context;

    public driveInfoAdapter(List<TransporterModel> transporterModels, Context context) {
        this.transporterModels = transporterModels;
        this.context = context;
    }

    @NonNull
    @Override
    public driverInfoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_driver_info, parent, false);
        return new driverInfoHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull driverInfoHolder holder, int position) {
        final TransporterModel transporterModel = transporterModels.get(position);

        if (transporterModels.get(position) != null) {
            Picasso.get()
                    .load(transporterModels.get(position).getDriverImageUrl())
                    .into(holder.imageView);
        }

        holder.nameTextView.setText(transporterModel.getName());
        holder.driverIdTextView.setText(transporterModel.getDriverId())   ;
        holder.phoneNumberTextView.setText(transporterModel.getPhoneNumber());
        holder.emailTextView.setText(transporterModel.getEmail());
        holder.passwordTextView.setText(transporterModel.getPassword());
        holder.carType.setText(transporterModel.getCarType());
        holder.carNumber.setText(transporterModel.getCarNumber());

    }

    @Override
    public int getItemCount() {
        return transporterModels.size();
    }
}







