package com.jhonmaycon.feirabarata;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.hardware.camera2.CameraDevice;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private ArrayList<String> supermercadoImageUrl = new ArrayList<>();
    private  ArrayList<String> supermercadoName = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: started");
        setContentView(R.layout.activity_main);
        Toolbar appToolbar = findViewById(R.id.app_toolbar);
        setSupportActionBar(appToolbar);
        loadImagesBitmaps();
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
                Intent startLoginActivity = new Intent(MainActivity.this, LoginActivity.class);
                MainActivity.this.startActivity(startLoginActivity);
                return true;
            case R.id.menu_cadastro:
                Intent startCadastroActivity = new Intent(MainActivity.this, CadastroActivity.class);
                MainActivity.this.startActivity(startCadastroActivity);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void loadImagesBitmaps(){

        supermercadoImageUrl.add("https://www.ocaminho.net.br/fl/normal/47-5c49e5f44a221_bombom.jpg?node_id=47");
        supermercadoName.add("Bombom");

        initReclyclerView();
    }

    private void initReclyclerView(){
        Log.d(TAG, "initReclyclerView: called");

        RecyclerView recyclerView = findViewById(R.id.reclycler_vew_mercardos);
        SupermercadoReclyclerViewAdapter adapter = new SupermercadoReclyclerViewAdapter(supermercadoImageUrl, supermercadoName, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                recyclerView.getLayoutManager().getLayoutDirection());
        recyclerView.addItemDecoration(mDividerItemDecoration);
    }
}
