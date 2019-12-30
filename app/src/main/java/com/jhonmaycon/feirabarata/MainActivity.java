package com.jhonmaycon.feirabarata;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements SupermercadoReclyclerViewAdapter.OnSupermercadoCardViewListener {

    private static final String TAG = "MainActivity";

    private ArrayList<String> supermercadoImageUrl = new ArrayList<>();
    private  ArrayList<String> supermercadoName = new ArrayList<>();
    private String URL_DATA = "http://feirabarata.jhonmaycon.com/api/supermercados/read.php";
    private TextView username;
    private String emailsession;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: started");
        setContentView(R.layout.activity_main);
        Toolbar appToolbar = findViewById(R.id.app_toolbar);
        setSupportActionBar(appToolbar);
        username = findViewById(R.id.tv_username_session);

        sessionManager = new SessionManager(this);
        if(sessionManager.isLoged()) {
            HashMap<String, String> user = sessionManager.getUserDetail();
            String userName = user.get(sessionManager.NAME);
            emailsession = user.get(sessionManager.EMAIL);
            username.setVisibility(View.VISIBLE);
            username.setText("Bem-vindo(a) " + userName);
        }
        loadRecyclerViewData();


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
            case R.id.menu_logout:
                sessionManager.logout();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void loadRecyclerViewData(){

        Log.d(TAG, "loadRecyclerViewData: called");

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Carregando dados...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_DATA, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject supermercadosListJson = new JSONObject(response);
                    JSONArray records = supermercadosListJson.getJSONArray("records");

                    for (int i = 0; i < records.length(); i++){
                        JSONObject supermercado = records.getJSONObject(i);
                        supermercadoImageUrl.add(supermercado.getString("foto"));
                        supermercadoName.add(supermercado.getString("nome"));
                    }
                } catch (JSONException e) {
                    Log.d(TAG, e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Log.d(TAG, error.toString());
            }
        });

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);

        initRecyclerView();
    }

    private void initRecyclerView(){
        Log.d(TAG, "initRecyclerView: called");

        RecyclerView recyclerView = findViewById(R.id.reclycler_vew_mercardos);
        SupermercadoReclyclerViewAdapter adapter = new SupermercadoReclyclerViewAdapter(supermercadoImageUrl, supermercadoName, this, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onSupermercadoCardViewClick(int position) {
        supermercadoName.get(position);
        Intent intent = new Intent(this, ProdutosActivity.class);
        startActivity(intent);
    }
}
