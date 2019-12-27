package com.jhonmaycon.feirabarata;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class SupermercadoReclyclerViewAdapter extends RecyclerView.Adapter<SupermercadoReclyclerViewAdapter.ViewHolder>{
    private static final String TAG = "SupermercadoReclyclerVi";

    ArrayList<String> supermercadosImagesList;
    ArrayList<String> supermercadosNamesList;
    Context context;
    OnSupermercadoCardViewListener monSupermercadoCardViewListener;

    public SupermercadoReclyclerViewAdapter(ArrayList<String> supermercadosImagesList, ArrayList<String> supermercadosNamesList, Context context, OnSupermercadoCardViewListener monSupermercadoCardViewListener) {
        this.supermercadosImagesList = supermercadosImagesList;
        this.supermercadosNamesList = supermercadosNamesList;
        this.context = context;
        this.monSupermercadoCardViewListener = monSupermercadoCardViewListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.supermercado_item_view, parent, false);
        ViewHolder holder = new ViewHolder(view, monSupermercadoCardViewListener);
        return holder;
    }

    @Override
    public void onBindViewHolder (@NonNull ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called");

        //Glide.with(context).asBitmap().load(supermercadosImagesList.get(position)).into(holder.supermercadoImage);
        holder.supermercadoName.setText(supermercadosNamesList.get(position));
    }

    @Override
    public int getItemCount() {
        return supermercadosNamesList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView supermercadoImage;
        TextView supermercadoName;
        OnSupermercadoCardViewListener onSupermercadoCardViewListener;

        public ViewHolder (@NonNull View itemView, OnSupermercadoCardViewListener onSupermercadoCardViewListener) {
            super(itemView);

            supermercadoImage = itemView.findViewById(R.id.supermercado_image);
            supermercadoName = itemView.findViewById(R.id.supermercado_name);
            this.onSupermercadoCardViewListener = onSupermercadoCardViewListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onSupermercadoCardViewListener.onSupermercadoCardViewClick(getAdapterPosition());
        }
    }

    public interface OnSupermercadoCardViewListener {
        void onSupermercadoCardViewClick(int position);
    }
}
