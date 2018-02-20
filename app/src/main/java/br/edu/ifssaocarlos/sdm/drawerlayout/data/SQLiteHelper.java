package br.edu.ifssaocarlos.sdm.drawerlayout.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifssaocarlos.sdm.drawerlayout.model.User;

/**
 * Created by zigui on 19/02/2018.
 */

public class SQLiteHelper<T> extends SQLiteOpenHelper {

    private static final String databaseName = "ifspT1";
    private static final int databaseVersion =2;
    private Context appContext;

    public SQLiteHelper(Context context){
        super(context, databaseName, null, databaseVersion);
        this.appContext = context;
        SQLiteDatabase db = getWritableDatabase();

        db.execSQL("CREATE TABLE IF NOT EXISTS user(" +
                "full_name VARCHAR unique PRIMARY KEY, " +
                "login VARCHAR," +
                "password VARCHAR);");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS user");
    }

    public void insert(String table, String fields, String values){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO "+table+"("+fields+") VALUES ("+values+")");
    }

    public void update(String table, String fields, String values){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE "+table+" SET "+fields+" WHERE "+values);
    }

    public List<T> searchFiltered(String table, String fields, String values){
        List<T> list = new ArrayList<>();

        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor;
        String[] cols = new String[0];

        if(fields.contains("full_name")){
            cols=new String[] {"full_name","login","password"};            
        }
        
        String where= fields + " like ?";
        String[] argWhere=new String[]{"%"+values + "%"};


        cursor = db.query(table, cols, where , argWhere,
                null, null, fields);

        while (cursor.moveToNext())
        {
            if(fields.contains("full_name")){
                User u = new User(cursor.getString(0), cursor.getString(1),
                        cursor.getString(2));
                list.add((T) u);
            }

        }
        cursor.close();

        db.close();
        return list;
    }
}
