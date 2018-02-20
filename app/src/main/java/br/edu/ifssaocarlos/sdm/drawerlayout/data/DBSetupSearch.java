package br.edu.ifssaocarlos.sdm.drawerlayout.data;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifssaocarlos.sdm.drawerlayout.model.User;

/**
 * Created by zigui on 19/02/2018.
 */

public class DBSetupSearch<T> {

    private final Context context;

    public DBSetupSearch(Context ctx){
        this.context = ctx;
    }

    public List<T> setupSearchToT(String values){
        List<T> list;
        SQLiteHelper sqLiteHelper = new SQLiteHelper(context);
        list = sqLiteHelper.searchFiltered("user","full_name", values);
        return list;
    }
}
