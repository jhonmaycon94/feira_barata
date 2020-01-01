package com.jhonmaycon.feirabarata;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NovoProduto extends AppCompatActivity implements View.OnClickListener {

    private EditText nomeProduto, marcaProduto, quantidadeProduto, precoProduto;
    private ImageView imagemproduto;
    private Spinner supermercadoSpinner;
    private Button btn_cadastrar, btn_upload;
    private String userEmail;
    private List<String> supermercados = new ArrayList<>();
    private List<String> supermercadosIds = new ArrayList<>();
    private String selectedSupermercadoId;
    private ProgressBar progressBar;
    private static String CADASTROPRODUTO_URL = "http://feirabarata.jhonmaycon.com/api/products/register.php";
    private static final int IMAGE_REQUEST = 2206;
    private Bitmap bitmap;

    private static final String TAG = "NovoProduto";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: called");
        setContentView(R.layout.activity_novo_produto);
        Toolbar toolbar = findViewById(R.id.app_toolbar);
        setSupportActionBar(toolbar);

        nomeProduto = findViewById(R.id.edt_nome_produto);
        marcaProduto = findViewById(R.id.edt_marca_produto);
        quantidadeProduto = findViewById(R.id.edt_volume_produto);
        precoProduto = findViewById(R.id.edt_preco_produto);
        imagemproduto = findViewById(R.id.product_image);
        supermercadoSpinner = findViewById(R.id.supermercado_spinner);
        btn_cadastrar = findViewById(R.id.bt_cadastrar_produto);
        btn_upload = findViewById(R.id.btn_upload_product_img);
        progressBar = findViewById(R.id.upload_product);

        btn_cadastrar.setOnClickListener(this);
        btn_upload.setOnClickListener(this);

        supermercadoSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedSupermercadoId = supermercadosIds.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        Intent intent = getIntent();
        userEmail = intent.getStringExtra("user_email");
        supermercados = intent.getStringArrayListExtra("supermercadosNames");
        supermercadosIds = intent.getStringArrayListExtra("supermercadosId");

        ArrayAdapter<String> supermercadosAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, supermercados);
        supermercadoSpinner.setAdapter(supermercadosAdapter);
    }

    
    public void cadastrarProduto(){
        Log.d(TAG, "cadastrarProduto: called");
        
        final String pNome = nomeProduto.getText().toString().trim();
        final String pMarca = marcaProduto.getText().toString().trim();
        final String pQuantidade = quantidadeProduto.getText().toString().trim();
        final String pPreco = precoProduto.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, CADASTROPRODUTO_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "onResponse: " + response);
                if (response.contains("success")){
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        Character success = jsonObject.getString("success").charAt(0);

                        if (success == '1'){
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(NovoProduto.this, "Cadastrado com sucesso!!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(NovoProduto.this, MainActivity.class);
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(NovoProduto.this, "Erro ao cadastrar!", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "onResponse: " + response);
                        }
                    }catch (JSONException e){
                        e.printStackTrace();
                        Toast.makeText(NovoProduto.this, "Erro ao cadastrar! " + e.toString(), Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                        btn_cadastrar.setVisibility(View.VISIBLE);
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(NovoProduto.this, "Erro ao cadastrar! " + error.toString(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                btn_cadastrar.setVisibility(View.VISIBLE);
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("nome", pNome);
                params.put("marca", pMarca);
                params.put("preco", pPreco);
                params.put("quantidade", pQuantidade);
                params.put("userEmail", userEmail);
                params.put("supermercadoId", selectedSupermercadoId);
                if (bitmap != null) {
                    params.put("image", imageToString(bitmap));
                }
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_cadastrar_produto:
                Log.d(TAG, "onClick btn_cadastrar called");
                cadastrarProduto();
                break;
            case R.id.btn_upload_product_img:
                Log.d(TAG, "onClick: btn_upload called");
                selectImage();
                break;
        }
    }

    public void selectImage(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == IMAGE_REQUEST && resultCode == RESULT_OK && data != null){
            Uri path = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
                imagemproduto.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            Log.d(TAG, "onActivityResult: erro");
        }
    }

    public String imageToString(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imgBytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgBytes, Base64.DEFAULT);
    }
}
