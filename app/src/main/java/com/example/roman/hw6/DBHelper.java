package com.example.roman.hw6;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by roman on 25.02.2016.
 */
public class DBHelper extends SQLiteOpenHelper{


    private static final String DATABASE_NAME = "weather";
    private static final String TABLE_NAME = "info_weather";
    private static final String TABLE_CREATE =  "CREATE TABLE " + TABLE_NAME + " (" +
            "_id integer primary key autoincrement,"+
            "city text,"+
            "weather text" + " );";


    public DBHelper(Context context){
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
