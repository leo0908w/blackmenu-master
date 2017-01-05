package com.org.iii.blackmenu;


import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;

import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.BottomBarBadge;
import com.roughike.bottombar.BottomBarFragment;
import com.roughike.bottombar.OnMenuTabSelectedListener;
import com.roughike.bottombar.OnTabSelectedListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


public class MainActivity extends AppCompatActivity {
    private F1 f1;
    private FragmentManager fmr;
    private FragmentTransaction ftn;
    private FireBase1 fireBase1;
    private BottomBar bottomBar;
    private menupager mu;
    private int count;
    private DBHandler handler;
    private SQLiteDatabase db;

    public static final String POSITION = "position";
    public static final int FRAGMENT_ONE=0;
    public static final int FRAGMENT_TWO=1;

    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         EventBus.getDefault().register(this);

        handler = new DBHandler(this);
        db = handler.getWritableDatabase();

        fmr =getSupportFragmentManager();

//        showFragment(FRAGMENT_ONE);
        f1 = new F1();


        bottomBar = BottomBar.attach(this, savedInstanceState);
        bottomBar.setItemsFromMenu(R.menu.three_buttons_menu, new OnMenuTabSelectedListener() {
            @Override
            public void onMenuItemSelected(@IdRes int menuItemId) {
                if (menuItemId == R.id.recent_item) {
                    ftn = fmr.beginTransaction().setCustomAnimations(R.anim.zoomin, R.anim.zoomout);
                    ftn.detach(mu);
                    ftn.replace(R.id.container, f1);
//                    ftn.addToBackStack(null);
                    ftn.commit();
                } else if (menuItemId == R.id.location_item) {
//                    showFragment(FRAGMENT_TWO);
                    mu = new menupager();
                    ftn = fmr.beginTransaction().setCustomAnimations(R.anim.zoomin, R.anim.zoomout);
                    ftn.replace(R.id.container, mu);
//                    ftn.addToBackStack(null);
                    ftn.commit();
                }
            }
        });

        ftn = fmr.beginTransaction();
        ftn.add(R.id.container, f1);
        ftn.commit();

        // Make a Badge for the first tab, with red background color and a value of "4".
        BottomBarBadge unreadMessages = bottomBar.makeBadgeForTabAt(1, "#E91E63", 0);

        // Control the badge's visibility
        unreadMessages.show();
        //unreadMessages.hide();

        // Change the displayed count for this badge.
//        Cursor cursor = db.query("cart",new String[]{"number"},null,null,null,null,null);
//        while (cursor.moveToNext()) {
//            int cnumber = cursor.getInt(cursor.getColumnIndex("number"));
//            count += cnumber;
//        }

        unreadMessages.setCount(count);

        // Change the show / hide animation duration.
//        unreadMessages.setAnimationDuration(9999999);

        // If you want the badge be shown always after unselecting the tab that contains it.
        unreadMessages.setAutoShowAfterUnSelection(true);



    }

    private String cname = "";
    private int cnumber;
    // EventBus
    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onMessageEvent(Order event) {
        Cursor cursor = db.query("cart", new String[]{"name, number"}, "name = ?", new String[]{event.getProduct()}, null, null, null);
        while (cursor.moveToNext()) {
            cname = cursor.getString(cursor.getColumnIndex("name"));
            cnumber = cursor.getInt(cursor.getColumnIndex("number"));
        }
        if (cname.equals(event.getProduct())) {
            ContentValues data = new ContentValues();
            data.put("name", event.getProduct());
            data.put("price", event.getPrice());
            data.put("path", event.getPathimg());
            data.put("number", ++cnumber);
            db.update("cart", data, "name = ?", new String[]{event.getProduct()});
//                    Log.v("if", "if yes");
        } else {
            ContentValues data = new ContentValues();
            data.put("name", event.getProduct());
            data.put("price", event.getPrice());
            data.put("path", event.getPathimg());
            data.put("number", event.getNumber());
            db.insert("cart", null, data);
//                    Log.v("else", "else no");
        }

//        Log.v("123", "db: " + event.getProduct() + " cum: " + cnumber + " eventnuber: " + event.getNumber());
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        bottomBar.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);

        Intent intent = new Intent(this, MyService.class);
        stopService(intent);

    }


}
