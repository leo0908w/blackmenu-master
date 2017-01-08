package com.org.iii.blackmenu;


import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
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

import cn.pedant.SweetAlert.SweetAlertDialog;


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
    private String re;
    private BottomBarBadge unreadMessages;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final SweetAlertDialog pDialog = new SweetAlertDialog(MainActivity.this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        pDialog.show();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                pDialog.dismiss();
            }
        }).start();

         EventBus.getDefault().register(this);

        handler = new DBHandler(this);
        db = handler.getWritableDatabase();

        fmr =getSupportFragmentManager();

//        showFragment(FRAGMENT_ONE);
        f1 = new F1();

        Intent intent = getIntent();
        re = intent.getStringExtra("ppking");
//        Log.v("will", "re : "+re);

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

                    Bundle bundle = new Bundle();
                    bundle.putString("edttext", re);
// set Fragmentclass Arguments
                    mu = new menupager();
                    mu.setArguments(bundle);
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
        unreadMessages = bottomBar.makeBadgeForTabAt(1, "#E91E63", 0);

        // Control the badge's visibility
        unreadMessages.show();
//        unreadMessages.hide();

        // Change the displayed count for this badge.
//        Cursor cursor = db.query("cart",new String[]{"number"},null,null,null,null,null);
//        while (cursor.moveToNext()) {
//            int cnumber = cursor.getInt(cursor.getColumnIndex("number"));
//            count += cnumber;
//        }
////        count = 0;
//        Log.v("will", "Count: " + count);
//        unreadMessages.setCount(200);

        // Change the show / hide animation duration.
//        unreadMessages.setAnimationDuration(9999999);

        // If you want the badge be shown always after unselecting the tab that contains it.
        unreadMessages.setAutoShowAfterUnSelection(true);
        count = 0;
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 0:
                            count++;
                            unreadMessages.setCount(count);
                        break;
                }
            }
        };

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
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message msg = new Message();
                msg.what = 0;
                mHandler.sendMessage(msg);
            }
        }).start();

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

    }



}
