package com.jhonmaycon.feirabarata;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ProdutosRecyclerViewAdapter extends RecyclerView.Adapter<ProdutosRecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "ProdutosRecyclerViewAda";

    private ArrayList<String> productsDescriptionList = new ArrayList<>();
    private ArrayList<String> productsPriceList = new ArrayList<>();
    private ArrayList<String> productsImagesList = new ArrayList<>();
    private String productsSupermercado;
    private Context context;

    public ProdutosRecyclerViewAdapter(ArrayList<String> productsDescriptionList, ArrayList<String> productsPriceList, ArrayList<String> productsImagesList, String productsSupermercado, Context context) {
        this.productsDescriptionList = productsDescriptionList;
        this.productsPriceList = productsPriceList;
        this.productsImagesList = productsImagesList;
        this.productsSupermercado = productsSupermercado;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.produtos_item_view, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called");

        Glide.with(context).asBitmap().load(productsImagesList.get(position)).into(holder.productImage);
        holder.productSupermercado.setText(productsSupermercado);
        holder.prdocutDescription.setText(productsDescriptionList.get(position));
        holder.prdocutPrice.setText(productsPriceList.get(position));
        holder.productsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG,"onClick: clicked on: " + productsDescriptionList.get(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        return productsDescriptionList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView productImage;
        TextView prdocutDescription;
        TextView productSupermercado;
        TextView prdocutPrice;
        ConstraintLayout productsLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            productImage = itemView.findViewById(R.id.product_image);
            prdocutDescription =itemView.findViewById(R.id.product_description);
            prdocutPrice = itemView.findViewById(R.id.product_price);
            productSupermercado = itemView.findViewById(R.id.product_supermercado);
            productsLayout = itemView.findViewById(R.id.produtos_item_layout);
        }
    }
}
