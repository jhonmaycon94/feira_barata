package com.jhonmaycon.feirabarata;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ProdutosActivity extends AppCompatActivity {

    private static final String TAG = "ProdutosActivity";

    private ArrayList<String> productsImagesURL = new ArrayList<>();
    private ArrayList<String> productsDescription = new ArrayList<>();
    private ArrayList<String> productsPrice = new ArrayList<>();
    private String productsSupermercado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: started");
        setContentView(R.layout.activity_produtos);
        Toolbar appToolbar = findViewById(R.id.app_toolbar);
        setSupportActionBar(appToolbar);
        loadImagesBitmap();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_login:
                Intent startLoginActivity = new Intent(ProdutosActivity.this, LoginActivity.class);
                ProdutosActivity.this.startActivity(startLoginActivity);
                return true;
            case R.id.menu_cadastro:
                Intent startCadastroActivity = new Intent(ProdutosActivity.this, CadastroActivity.class);
                ProdutosActivity.this.startActivity(startCadastroActivity);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void loadImagesBitmap(){
        initRecyclerView();
    }

    public void initRecyclerView(){
        Log.d(TAG, "initrecyclerView: called");

        RecyclerView recyclerView = findViewById(R.id.produtos_reclycler_view);
        ProdutosRecyclerViewAdapter adapter = new ProdutosRecyclerViewAdapter(productsDescription, productsPrice, productsImagesURL, productsSupermercado, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), recyclerView.getLayoutManager().getLayoutDirection());
        recyclerView.addItemDecoration(dividerItemDecoration);
    }
}
