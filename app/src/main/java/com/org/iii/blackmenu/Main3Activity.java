package com.org.iii.blackmenu;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.BottomBarBadge;
import com.roughike.bottombar.BottomBarFragment;
import com.roughike.bottombar.OnTabSelectedListener;

public class Main3Activity extends AppCompatActivity {
    private Button button;
    private DBHandler handler;
    private SQLiteDatabase db;
    public  TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        handler = new DBHandler(this);
        db = handler.getWritableDatabase();

        textView = (TextView) findViewById(R.id.textView);

    }
    public void clear(View v) {
        db.execSQL("DROP TABLE IF EXISTS cart");
        db.execSQL("CREATE TABLE cart(id INTEGER PRIMARY KEY AUTOINCREMENT,name STRING,price INTEGER,path STRING,number INTEGER)");
    }

    public void query(View v) {
        textView.setText("");
        Cursor cursor = db.query("cart",null,null,null,null,null,null);

        while (cursor.moveToNext()){
            String cprice = cursor.getString(cursor.getColumnIndex("price"));
            String cpath = cursor.getString(cursor.getColumnIndex("path"));
            String cname = cursor.getString(cursor.getColumnIndex("name"));
            int cnumber = cursor.getInt(cursor.getColumnIndex("number"));
            textView.append(cname + " : "+cnumber +" : "+ cprice +" : "+ cpath+"\n");
        }
    }
}
