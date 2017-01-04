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
        db = handler.getReadableDatabase();

        fmr =getSupportFragmentManager();

        showFragment(FRAGMENT_ONE);

        bottomBar = BottomBar.attach(this, savedInstanceState);
        bottomBar.setItemsFromMenu(R.menu.three_buttons_menu, new OnMenuTabSelectedListener() {
            @Override
            public void onMenuItemSelected(@IdRes int menuItemId) {
                if (menuItemId == R.id.recent_item) {
                    showFragment(FRAGMENT_ONE);
                } else if (menuItemId == R.id.location_item) {
                    showFragment(FRAGMENT_TWO);
                }
            }
        });

        // Make a Badge for the first tab, with red background color and a value of "4".
        BottomBarBadge unreadMessages = bottomBar.makeBadgeForTabAt(1, "#E91E63", 5);

        // Control the badge's visibility
        unreadMessages.show();
        //unreadMessages.hide();

        // Change the displayed count for this badge.
        unreadMessages.setCount(2);

        // Change the show / hide animation duration.
//        unreadMessages.setAnimationDuration(9999999);

        // If you want the badge be shown always after unselecting the tab that contains it.
        unreadMessages.setAutoShowAfterUnSelection(true);

//        fireBase1 = new FireBase1();
//        fireBase1.ReadFoodBase("foodinfo");
//

    }

    public void showFragment(int index){

        ftn = fmr.beginTransaction().setCustomAnimations(
                R.anim.zoomin, R.anim.zoomout);
        hideFragment(ftn);

        //注意这里设置位置
        position = index;

        switch (index) {

            case FRAGMENT_ONE:
                // 如果Fragment为空，就新建一个实例
                // 如果不为空，就将它从栈中显示出来

                if (f1 == null) {
                    f1 = new F1();
                    ftn.add(R.id.container, f1);
                } else {
                    ftn.show(f1);
                }

                break;
            case FRAGMENT_TWO:

                if (mu == null) {
                    mu = new menupager();
                    ftn.add(R.id.container, mu);
                } else {
                    ftn.show(mu);
                }

                break;
        }

        ftn.commit();
    }

    public void hideFragment(FragmentTransaction ft){
        //如果不为空，就先隐藏起来
        if (f1 != null){
            ft.hide(f1);
        }
        if(mu != null) {
            ft.hide(mu);
        }
    }

    // EventBus
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(Order event) {
        Cursor cursor = db.query("cart",  new String[]{"name"}, "name = ?", new String[]{event.getProduct()}, null, null, null);
        if (cursor.equals(event.getProduct())) {
            ContentValues data = new ContentValues();
            data.put("cname", "Blue");
            data.put("birthday", "1992-01-03");
            data.put("tel", "0912");
            db.update("cust", data, "id = ?", new String[] {"5"});
//            ContentValues data = new ContentValues();
//            data.put("name", event.getProduct());
//            data.put("price", event.getPrice());
//            data.put("path", event.getPathimg());
//            data.put("number", event.getNumber());
//            db.insert("cart", null, data);
        } else {
            ContentValues data = new ContentValues();
            data.put("name", event.getProduct());
            data.put("price", event.getPrice());
            data.put("path", event.getPathimg());
            data.put("number", event.getNumber());
            db.insert("cart", null, data);
        }
        Log.v("123", "db: " + event.getProduct());
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

//        Intent it = new Intent(this , MyService.class);
//        stopService(it);
    }


}
