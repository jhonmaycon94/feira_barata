package com.jhonmaycon.feirabarata;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar appToolbar = findViewById(R.id.app_toolbar);
        setSupportActionBar(appToolbar);
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
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
