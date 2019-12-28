package com.jhonmaycon.feirabarata;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CadastroActivity extends AppCompatActivity {

    private EditText name, password, confirmPassword, email, telefone, dataNascimento;
    private Button btnCadastrar;
    private ProgressBar progressBar;
    private static String REGISTER_URL = "http://feirabarata.jhonmaycon.com/api/users/register.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        name = findViewById(R.id.edt_nome_cadastro);
        password = findViewById(R.id.edt_senha_cadastro);
        confirmPassword = findViewById(R.id.edt_conf_senha_cadastro);
        email = findViewById(R.id.edt_email_cadastro);
        telefone = findViewById(R.id.edt_telefone_cadastro);
        dataNascimento = findViewById(R.id.edt_nascimento_cadastro);
        btnCadastrar = findViewById(R.id.btn_cadastro);
        progressBar = findViewById(R.id.loading);

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(password.getText().equals(confirmPassword.getText())) {
                    register();
                }
                else {
                    Toast.makeText(CadastroActivity.this, "Senhas não conferem", Toast.LENGTH_SHORT);
                }
            }
        });
    }

    public void register(){
        progressBar.setVisibility(View.VISIBLE);
        btnCadastrar.setVisibility(View.GONE);

        final String name = this.name.getText().toString().trim();
        final String password = this.password.getText().toString().trim();
        final String confPassword = this.confirmPassword.getText().toString().trim();
        final String email = this.email.getText().toString().trim();
        final String telefone = this.telefone.getText().toString().trim();
        final String dataNascimento = this.dataNascimento.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");

                    if(success.equals(1)){
                        Toast.makeText(CadastroActivity.this, "Cadastrado com sucesso!!", Toast.LENGTH_SHORT).show();
                    }
                }
                catch (JSONException e){
                    e.printStackTrace();
                    Toast.makeText(CadastroActivity.this, "Erro ao cadastrat! " + e.toString(), Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                    btnCadastrar.setVisibility(View.VISIBLE);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(CadastroActivity.this, "Erro ao cadastrat! " + error.toString(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                btnCadastrar.setVisibility(View.VISIBLE);
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("name", name);
                params.put("email", email);
                params.put("password", password);
                params.put("telefone", telefone);
                params.put("dataNascimento", dataNascimento);
                return super.getParams();
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
