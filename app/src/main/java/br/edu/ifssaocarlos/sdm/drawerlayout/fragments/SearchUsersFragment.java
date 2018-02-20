package br.edu.ifssaocarlos.sdm.drawerlayout.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.edu.ifssaocarlos.sdm.drawerlayout.R;
import br.edu.ifssaocarlos.sdm.drawerlayout.SaveUpdateUserActivity;
import br.edu.ifssaocarlos.sdm.drawerlayout.data.DBSetupSearch;
import br.edu.ifssaocarlos.sdm.drawerlayout.model.User;

/**
 * Created by zigui on 18/02/2018.
 */

public class SearchUsersFragment extends Fragment {

    private EditText search;
    private View viewListUsers,viewSaveUpdateUser;
    private String searchSTR = "";

    Button btn_save_user, btn_update_user, btn_cancel_user;
    EditText full_name_user, login_user, password1_user, password2_user;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewListUsers = inflater.inflate(R.layout.fragment_list_users, container, false);

        search = viewListUsers.findViewById(R.id.search_data);
        search.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                if(s.length()>=3){
                    searchSTR = s.toString();
                    filteredSearch(searchSTR);
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            /*    Log.i("onTextChanged", String.valueOf(count));
                if(s.length()==0){
                }*/
            }
        });

        return viewListUsers;
    }

    public void filteredSearch(String searchSTR){
        DBSetupSearch dbSearch = new DBSetupSearch(viewListUsers.getContext());
        List<User> list;
        list = dbSearch.setupSearchToT(searchSTR);

        ArrayAdapter<User> aau = new ArrayAdapter<User>(getContext(),
                android.R.layout.simple_list_item_1, list);

        ListView listView = viewListUsers.findViewById(R.id.list_data);
        listView.setAdapter(aau);

        SaveUpdateUserActivity sau = (SaveUpdateUserActivity) getActivity();
        btn_save_user = sau.findViewById(R.id.btn_save_user);
        btn_update_user = sau.findViewById(R.id.btn_update_user);
        btn_cancel_user = sau.findViewById(R.id.btn_cancel_user);

        full_name_user = sau.findViewById(R.id.full_name_user);
        login_user = sau.findViewById(R.id.login_user);
        password1_user = sau.findViewById(R.id.password1_user);
        password2_user = sau.findViewById(R.id.password2_user);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                User user = (User) parent.getItemAtPosition(position);

                btn_update_user.setVisibility(viewSaveUpdateUser.VISIBLE);
                btn_save_user.setVisibility(viewSaveUpdateUser.INVISIBLE);
                btn_cancel_user.setVisibility(viewSaveUpdateUser.VISIBLE);

                full_name_user.setEnabled(false);
                full_name_user.setText(user.getFullName());
                login_user.setText(user.getLogin());
                password1_user.setText(user.getPassword());
                password2_user.setText(user.getPassword());
                //Toast.makeText(getContext(), "Clicou no "+user, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
