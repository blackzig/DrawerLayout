package br.edu.ifssaocarlos.sdm.drawerlayout;
//https://www.youtube.com/watch?v=rBZMabLuh5E

import android.content.Intent;
import android.content.res.Configuration;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import okhttp3.OkHttpClient;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar1;
    private ActionBarDrawerToggle toggle;
    private DrawerLayout drawerLayout;

    private BottomNavigationView bnv;
    private Button btnUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Para ver os dados do BD no navegador
        //chrome://inspect/#devices
        Stetho.initializeWithDefaults(this);
        new OkHttpClient.Builder()
                .addNetworkInterceptor(new StethoInterceptor())
                .build();

        //Menu lateral
        toolbar1 = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar1);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        drawerLayout = findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawerLayout,
                R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(toggle);

        btnUser = findViewById(R.id.go_to_screen_user);
        btnUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, SaveUpdateUserActivity.class);
                startActivity(i);
            }
        });


//só um teste de menu na parte de baixo da tela
       bnv = findViewById(R.id.bottom_nav);
        bnv.setVisibility(View.INVISIBLE);

        bnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.btnSave:
                        Log.i("clicou ", String.valueOf(item.getTitle()));
                        break;
                    case R.id.btnUpdate:
                        Log.i("clicou ", String.valueOf(item.getTitle()));
                        break;
                    case R.id.btnList:
                        break;
                }
                return false;
            }
        });

    }

    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
        toggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        toggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(toggle.onOptionsItemSelected(item))
            return true;
        switch (item.getItemId()){
            case R.id.teste1_1:
                break;
            case R.id.teste1_2:
                break;
            case R.id.reset_default:
                Toast.makeText(this, "Reset!", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_options, menu);
        return true;
    }

}
