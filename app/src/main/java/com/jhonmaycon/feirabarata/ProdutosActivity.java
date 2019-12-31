package com.jhonmaycon.feirabarata;

import android.app.ProgressDialog;
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

public class ProdutosActivity extends AppCompatActivity implements ProdutosRecyclerViewAdapter.OnProductCardViewListener {

    private static final String TAG = "ProdutosActivity";

    private ArrayList<String> productsImagesURL = new ArrayList<>();
    private ArrayList<String> productsDescription = new ArrayList<>();
    private ArrayList<String> productsPrice = new ArrayList<>();
    private String productsSupermercado;
    private String supermercadoId;
    private String URL_DATA;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: started");
        setContentView(R.layout.activity_produtos);
        Toolbar appToolbar = findViewById(R.id.app_toolbar);
        setSupportActionBar(appToolbar);

        Intent intent = getIntent();
        supermercadoId = intent.getStringExtra("supermercadoId");

        URL_DATA = "http://feirabarata.jhonmaycon.com/api/products/read.php?supermercadoId="+supermercadoId;

        sessionManager = new SessionManager(this);
        getProducts();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem logOut = menu.findItem(R.id.menu_logout);
        MenuItem logIn = menu.findItem(R.id.menu_login);
        MenuItem cadastro = menu.findItem(R.id.menu_cadastro);
        MenuItem produto = menu.findItem(R.id.menu_produto);

        logOut.setVisible(sessionManager.isLoged());
        produto.setVisible(sessionManager.isLoged());
        logIn.setVisible(!(sessionManager.isLoged()));
        cadastro.setVisible(!(sessionManager.isLoged()));

        return true;
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
            case R.id.menu_logout:
                sessionManager.logout();
            case R.id.menu_produto:
               /* Intent startNovoProdutoActivity = new Intent(ProdutosActivity.this, NovoProduto.class);
                startNovoProdutoActivity.putExtra("user_email", emailsession);
                startNovoProdutoActivity.putExtra("supermercadosNames", supermercadoName);
                startNovoProdutoActivity.putExtra("supermercadosId", supermercadoId);
                ProdutosActivity.this.startActivity(startNovoProdutoActivity);*/
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void getProducts(){
        Log.d(TAG, "getProducts: called");

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Carregando dados...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_DATA, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                Log.d(TAG, "onResponse: "+response);
                try {
                    JSONObject productsListJson = new JSONObject(response);
                    JSONArray records = productsListJson.getJSONArray("records");

                    for(int i=0; i < records.length(); i++){
                        JSONObject product = records.getJSONObject(i);
                        productsImagesURL.add(product.getString("foto"));
                        productsPrice.add(product.getString("preco"));
                        String productDescription = product.getString("nome").toUpperCase() + " " + product.getString("marca").toUpperCase()+ " " + product.getString("quantidade");
                        productsDescription.add(productDescription);
                    }
                }
                catch (JSONException e){
                    Log.d(TAG, "onResponse: "+e.getMessage());
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Log.d(TAG, "onErrorResponse: "+error.getMessage());
            }
        });

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);

        initRecyclerView();
    }

    public void initRecyclerView(){
        Log.d(TAG, "initrecyclerView: called");

        RecyclerView recyclerView = findViewById(R.id.produtos_reclycler_view);
        ProdutosRecyclerViewAdapter adapter = new ProdutosRecyclerViewAdapter(productsDescription, productsPrice, productsImagesURL, productsSupermercado, this, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), recyclerView.getLayoutManager().getLayoutDirection());
        recyclerView.addItemDecoration(dividerItemDecoration);
    }

    @Override
    public void onProductCardViewClick(int position) {

    }
}
