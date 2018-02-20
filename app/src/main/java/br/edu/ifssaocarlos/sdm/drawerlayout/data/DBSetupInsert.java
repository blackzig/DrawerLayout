package br.edu.ifssaocarlos.sdm.drawerlayout.data;

import android.content.Context;

/**
 * Created by zigui on 19/02/2018.
 */

public class DBSetupInsert {

    private final Context context;

    public DBSetupInsert(Context ctx){
        this.context = ctx;
    }

    public void setupInsertToUser(String values){
        SQLiteHelper sqLiteHelper = new SQLiteHelper(context);
        sqLiteHelper.insert("user",
                        "full_name, " +
                        "login, " +
                        "password",values);
    }
}
