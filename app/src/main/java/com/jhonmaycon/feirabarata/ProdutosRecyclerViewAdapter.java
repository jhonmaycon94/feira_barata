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

public class ProdutosRecyclerViewAdapter extends RecyclerView.Adapter<ProdutosRecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "ProdutosRecyclerViewAda";

    private ArrayList<String> productsDescriptionList = new ArrayList<>();
    private ArrayList<String> productsPriceList = new ArrayList<>();
    private ArrayList<String> productsImagesList = new ArrayList<>();
    private String productsSupermercado;
    private Context context;
    OnProductCardViewListener monProductCardViewListener;

    public ProdutosRecyclerViewAdapter(ArrayList<String> productsDescriptionList, ArrayList<String> productsPriceList, ArrayList<String> productsImagesList, String productsSupermercado, Context context, OnProductCardViewListener monProductCardViewListener) {
        this.productsDescriptionList = productsDescriptionList;
        this.productsPriceList = productsPriceList;
        this.productsImagesList = productsImagesList;
        this.productsSupermercado = productsSupermercado;
        this.context = context;
        this.monProductCardViewListener = monProductCardViewListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.produtos_item_view, parent, false);
        ViewHolder holder = new ViewHolder(view, monProductCardViewListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called");

        //Glide.with(context).asBitmap().load(productsImagesList.get(position)).into(holder.productImage);
        holder.productSupermercado.setText(productsSupermercado);
        holder.prdocutDescription.setText(productsDescriptionList.get(position));
        holder.prdocutPrice.setText(productsPriceList.get(position));
    }

    @Override
    public int getItemCount() {
        return productsPriceList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView productImage;
        TextView prdocutDescription;
        TextView productSupermercado;
        TextView prdocutPrice;
        OnProductCardViewListener onProductCardViewListener;

        public ViewHolder(@NonNull View itemView, OnProductCardViewListener onProductCardViewListener) {
            super(itemView);

            productImage = itemView.findViewById(R.id.product_image);
            prdocutDescription =itemView.findViewById(R.id.product_description);
            prdocutPrice = itemView.findViewById(R.id.product_price);
            productSupermercado = itemView.findViewById(R.id.product_supermercado);
            this.onProductCardViewListener = onProductCardViewListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onProductCardViewListener.onProductCardViewClick(getAdapterPosition());
        }
    }

    public interface OnProductCardViewListener {
        void onProductCardViewClick(int position);
    }
}
