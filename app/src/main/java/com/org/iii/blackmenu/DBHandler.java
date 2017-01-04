package com.org.iii.blackmenu;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by user on 2017/1/4.
 */

public class DBHandler extends SQLiteOpenHelper {
    private static final String DB_NAME = "mydata.db";
    private static final int DB_VERSION = 1;

    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE cart " +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT,name STRING,price INTEGER,path STRING)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }
}
