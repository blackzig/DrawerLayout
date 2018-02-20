package br.edu.ifssaocarlos.sdm.drawerlayout.data;

import android.content.Context;

import br.edu.ifssaocarlos.sdm.drawerlayout.model.User;

/**
 * Created by zigui on 19/02/2018.
 */

public class DBSetupUpdate {

    private final Context context;

    public DBSetupUpdate(Context ctx){
        this.context = ctx;
    }

    public void setupUpdateToUser(User user){
        SQLiteHelper sqLiteHelper = new SQLiteHelper(context);
        sqLiteHelper.update("user",
                "login = '" + user.getLogin()+"' " +
                        ", password = '" + user.getPassword()+"' ",
                " full_name = '" + user.getFullName()+"'");
    }
}
