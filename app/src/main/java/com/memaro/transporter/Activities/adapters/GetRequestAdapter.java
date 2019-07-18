package com.memaro.transporter.Activities.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.memaro.transporter.Activities.CompanySendRequest;
import com.memaro.transporter.Activities.GetRequestActivity;
import com.memaro.transporter.Activities.Models.Request;
import com.memaro.transporter.Activities.holders.GetRequestHolder;
import com.memaro.transporter.R;

import java.util.List;


public class GetRequestAdapter extends RecyclerView.Adapter<GetRequestHolder> {
    private List<Request> requests;
    private Context context;

    public GetRequestAdapter(List<Request> requests, Context context) {
        this.requests = requests;
        this.context = context;
    }

    @NonNull
    @Override
    public GetRequestHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_request, parent, false);
        return new GetRequestHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull GetRequestHolder holder, int position) {

        final Request request = requests.get(position);

        holder.goodType.setText(String.valueOf(request.getGoodType()));
        holder.goodWeight.setText(String.valueOf(request.getGoodWeight()));
        holder.payment.setText(String.valueOf(request.getPayment()));
        holder.distance.setText(String.valueOf(request.getDistance()));
        holder.price.setText(String.valueOf(request.getPrice()));

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CompanySendRequest.request = request;
                context.startActivity(new Intent(v.getContext(), CompanySendRequest.class));
            }
        });

    }

    @Override
    public int getItemCount() {
        return requests.size();
    }
}
