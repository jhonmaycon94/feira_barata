package com.jhonmaycon.feirabarata;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class SupermercadoReclyclerViewAdapter extends RecyclerView.Adapter<SupermercadoReclyclerViewAdapter.ViewHolder>{
    private static final String TAG = "SupermercadoReclyclerVi";

    ArrayList<String> supermercadosImagesList;
    ArrayList<String> supermercadosNamesList;
    Context context;

    public SupermercadoReclyclerViewAdapter(ArrayList<String> supermercadosImagesList, ArrayList<String> supermercadosNamesList, Context context) {
        this.supermercadosImagesList = supermercadosImagesList;
        this.supermercadosNamesList = supermercadosNamesList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.supermercado_item_view, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called");

        //Glide.with(context).asBitmap().load(supermercadosImagesList.get(position)).into(holder.supermercadoImage);
        holder.supermercadoName.setText(supermercadosNamesList.get(position));
        holder.supermercadoItemCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "OnClick: clicked on: "+ supermercadosNamesList.get(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        return supermercadosNamesList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView supermercadoImage;
        TextView supermercadoName;
        CardView supermercadoItemCardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            supermercadoItemCardView = itemView.findViewById(R.id.cardview_supermercado_item);
            supermercadoImage = itemView.findViewById(R.id.supermercado_image);
            supermercadoName = itemView.findViewById(R.id.supermercado_name);
        }
    }
}
