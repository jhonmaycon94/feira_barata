package com.jhonmaycon.feirabarata;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private EditText email, password;
    private Button btnLogin;
    private ProgressBar loading;
    private static String URL_LOGIN = "http://feirabarata.jhonmaycon.com/api/users/login.php";
    private static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loading = findViewById(R.id.loading);
        btnLogin = findViewById(R.id.btn_login);
        email = findViewById(R.id.edtEmail);
        password = findViewById(R.id.edt_senha);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mEmail = email.getText().toString().trim();
                String mPassword = password.getText().toString().trim();

                if(!mEmail.isEmpty() || !mPassword.isEmpty()){
                    login(mEmail, mPassword);
                }
                else{
                    email.setError("Por favor, insira o e-mail!");
                    password.setError("Por favor, insira sua senha");
                }
            }
        });
    }

public void login(final String email,final String senha){
        loading.setVisibility(View.VISIBLE);

    StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_LOGIN, new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            if (response.contains("success")) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Log.d(TAG, jsonObject.toString());
                    Character success = jsonObject.getString("success").charAt(0);
                    JSONArray jsonArray = jsonObject.getJSONArray("login");

                    if (success == '1') {
                        for (int i=0; i<jsonArray.length(); i++){
                            JSONObject object = jsonArray.getJSONObject(i);

                            String email = object.getString("email").trim();
                            String nome = object.getString("nome").trim();

                            loading.setVisibility(View.GONE);
                            Toast.makeText(LoginActivity.this, "Login efetuado com sucesso!!", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.putExtra("nome", nome);
                            intent.putExtra("email", email);
                            startActivity(intent);
                        }
                    }
                    else{
                        loading.setVisibility(View.GONE);
                        Toast.makeText(LoginActivity.this, "Erro ao efetuar login: " + jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "onResponse: " + response);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    loading.setVisibility(View.GONE);
                    Toast.makeText(LoginActivity.this, "Erro ao efetuar login! " + e.toString(), Toast.LENGTH_SHORT).show();
                    loading.setVisibility(View.GONE);
                }
            }
            else{
                loading.setVisibility(View.GONE);
                Toast.makeText(LoginActivity.this, "Erro ao efetuar login", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onResponse: " + response);
            }
        }
    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            loading.setVisibility(View.GONE);
            Toast.makeText(LoginActivity.this, "Erro ao efetuar login", Toast.LENGTH_SHORT).show();
        }
    })
    {
        @Override
        protected Map<String, String> getParams() throws AuthFailureError {
            Map<String, String> params = new HashMap<>();
            params.put("email", email);
            params.put("senha", senha);
            return params;
        }
    };

    RequestQueue requestQueue = Volley.newRequestQueue(this);
    requestQueue.add(stringRequest);
}

}
