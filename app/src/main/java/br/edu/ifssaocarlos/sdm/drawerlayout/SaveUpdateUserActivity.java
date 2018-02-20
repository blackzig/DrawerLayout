package br.edu.ifssaocarlos.sdm.drawerlayout;

import android.app.Fragment;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

import br.edu.ifssaocarlos.sdm.drawerlayout.data.DBSetupInsert;
import br.edu.ifssaocarlos.sdm.drawerlayout.data.DBSetupSearch;
import br.edu.ifssaocarlos.sdm.drawerlayout.data.DBSetupUpdate;
import br.edu.ifssaocarlos.sdm.drawerlayout.data.SQLiteHelper;
import br.edu.ifssaocarlos.sdm.drawerlayout.fragments.SearchUsersFragment;
import br.edu.ifssaocarlos.sdm.drawerlayout.model.User;

public class SaveUpdateUserActivity extends AppCompatActivity implements View.OnClickListener{

    EditText full_name_user, login_user, password1_user, password2_user;
    Button btn_save_user, btn_update_user, btn_cancel_user;

    Integer saveNow = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_update_user);

        FragmentManager f = getSupportFragmentManager();
        FragmentTransaction ft = f.beginTransaction();
        ft.replace(R.id.frame_list_users, new SearchUsersFragment());
        ft.commit();

        btn_save_user = findViewById(R.id.btn_save_user);
        btn_save_user.setOnClickListener(this);

        btn_update_user = findViewById(R.id.btn_update_user);
        btn_update_user.setOnClickListener(this);
        btn_update_user.setVisibility(View.INVISIBLE);

        btn_cancel_user = findViewById(R.id.btn_cancel_user);
        btn_cancel_user.setOnClickListener(this);

        full_name_user = findViewById(R.id.full_name_user);
        login_user = findViewById(R.id.login_user);
        password1_user = findViewById(R.id.password1_user);
        password2_user = findViewById(R.id.password2_user);
    }

    private void captureData(Integer saveNow){
        String pass1 = password1_user.getText().toString();
        String pass2 = password2_user.getText().toString();

        if(full_name_user.getText().toString().equals("")){
            Toast.makeText(this, "Digite o nome do usuário.", Toast.LENGTH_LONG).show();
        }else if(login_user.getText().toString().equals("")){
            Toast.makeText(this, "Digite um login para o usuário.", Toast.LENGTH_LONG).show();
        }else if(pass1.equals("") || pass2.equals("")) {
            Toast.makeText(this, "Os campos de senha não devem ser vazios.", Toast.LENGTH_LONG).show();
        }else{
            if(pass1.equals(pass2)){

                User u = new User(full_name_user.getText().toString(),
                        login_user.getText().toString(),
                        password1_user.getText().toString());

                if(saveNow==1){
                    DBSetupInsert db = new DBSetupInsert(this);

                    try{
                        db.setupInsertToUser("'"+u.getFullName()+"','"+u.getLogin()+"','"+u.getPassword()+"'");

                        clearFields();

                        Toast.makeText(this, "Usuário "+u.getFullName()+" adicionado.", Toast.LENGTH_LONG).show();
                    }catch(Exception e){
                        Log.d("ERROR>>>>>>" ,e.getMessage());
                        Toast.makeText(this, "Este usuário já deve está cadastrado.", Toast.LENGTH_LONG).show();
                    }
                }else {
                    DBSetupUpdate db = new DBSetupUpdate(this);

                    try{

                        db.setupUpdateToUser(u);

                        clearFields();

                        Toast.makeText(this, "Usuário "+u.getFullName()+" atualizado.", Toast.LENGTH_LONG).show();
                    }catch(Exception e){
                        Log.i("ERROR>>>>>>" ,e.getMessage());
                        Toast.makeText(this, "Erro ao tentar atualizar os dados.", Toast.LENGTH_LONG).show();
                    }
                }

                ((SearchUsersFragment) getSupportFragmentManager().findFragmentById(R.id.frame_list_users)).filteredSearch(u.getFullName().trim());

            }else{
                Toast.makeText(this, "As senhas não são iguais.", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_save_user:
                captureData(1);
                break;
            case R.id.btn_cancel_user:
                clearFields();
                break;
            case R.id.btn_update_user:
                captureData(0);
                break;
        }
    }

    public void clearFields(){
        full_name_user.setText("");
        login_user.setText("");
        password1_user.setText("");
        password2_user.setText("");
        btn_cancel_user.setVisibility(View.INVISIBLE);
        btn_update_user.setVisibility(View.INVISIBLE);
        btn_save_user.setVisibility(View.VISIBLE);
        full_name_user.setEnabled(true);
        saveNow = 1;
    }
}
