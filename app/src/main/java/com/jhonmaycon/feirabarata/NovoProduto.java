package com.jhonmaycon.feirabarata;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class NovoProduto extends AppCompatActivity {

    private EditText nomeProduto, marcaProduto, quantidadeProduto, precoProduto;
    private ImageView imagemproduto;
    private Spinner supermercadoSpinner;
    private Button btn_cadastrar;
    private String userEmail;
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

        btn_cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        Intent intent = getIntent();
        userEmail = intent.getStringExtra("email");
    }
}
